package com.mygdx.game.managers;

import java.util.ArrayList;

import com.mygdx.game.actors.UnitActor;
import com.mygdx.game.board.Square;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.SelectionStage;
import com.mygdx.game.utils.CardTagProcTime;
import com.mygdx.game.utils.EffectTag;
import com.mygdx.game.utils.Stats;

/**
 * UnitManager manages things related to units
 * tbh, these managers are kinda arbitrary and are really just for organizational purposes 
 * @author Zachary Tu
 *
 */
public class UnitManager {

	private PlayState ps;
	
	public UnitManager(PlayState ps) {
		this.ps = ps;
	}
	
	/**
	 * This is run when a schmuck's hp is changed from a heal or damaging effect
	 * @param perp: The unit who dunnit
	 * @param vic: The unit whose hp is changed
	 * @param amount: amount of change (negative values for damage)
	 * @param tags: special damage tags?
	 */
	public void hpChange(UnitCard perp, UnitCard vic, int amount, EffectTag... tags) {
		
		int finalAmount = amount;
		
		//modify hp change with all on hp change effects
		finalAmount = ps.getEm().cardTagProcTime(CardTagProcTime.HP_CHANGE, finalAmount, perp, vic, null, null, null);
		
		//modify hp.
		vic.setCurrentHp(vic.getCurrentHp() + finalAmount);
		
		int maxHp = vic.getBuffedStat(Stats.HP);
		
		//Account for overheal and death.
		if (vic.getCurrentHp() > maxHp) {
			
			//TODO: if overheal effects are a thing, activate here?
			vic.setCurrentHp(maxHp);
		}
		
		if (vic.getCurrentHp() <= 0) {
			vic.die();
		}
	}
	
	/**
	 * This is run when a unit's RAIN changes
	 * @param perp: The unit who dunnit
	 * @param vic: The unit whose RAIN is changed
	 * @param amount: amount of change
	 */
	public void rainChange(UnitCard perp, UnitCard vic, int amount) {
		
		int finalAmount = amount;
		
		finalAmount = ps.getEm().cardTagProcTime(CardTagProcTime.RAIN_CHANGE, finalAmount, perp, vic, null, null, null);
		
		vic.setCurrentRain(vic.getCurrentRain() + finalAmount);
		
		//TODO: check for saturation point level
	}
	
	/**
	 * Acquire all allied units within a range from a selected square
	 * @param start: square we are checking distance from
	 * @param unit: unit that is checking
	 * @param range: distance to check from
	 * @return: list of nearby allies
	 */
	public ArrayList<UnitCard> getAlliesWithinDistance(Square start, UnitCard unit, int range) {
		ArrayList<UnitCard> targets = new ArrayList<UnitCard>();
		
		for (UnitCard target : getUnitsWithinDistance(start, range)) {
			if (target.getTeam().equals(unit.getTeam())) {
				targets.add(target);
			}
		}
		
		return targets;
	}
	
	/**
	 * Literally same as getAlliesWithinDistance except with enemies
	 */
	public ArrayList<UnitCard> getEnemiesWithinDistance(Square start, UnitCard unit, int range) {
		ArrayList<UnitCard> targets = new ArrayList<UnitCard>();
		
		for (UnitCard target : getUnitsWithinDistance(start, range)) {
			if (!target.getTeam().equals(unit.getTeam())) {
				targets.add(target);
			}
		}
		
		return targets;
	}
	
	/**
	 * This processes the targeting of a unit with a card
	 * @param card: This is the card that is doing the targeted
	 * @param player: unit doing the targeting
	 * @param range: distance for valid targets
	 */
	public void targetUnitWithinRange(final Card card, final UnitCard player, int range) {
		
		//Pop up a selection stage. When selecting a valid unit, run the card's on target method
		SelectionStage newStage = new SelectionStage(ps) {
			
			@Override
			public void onSelectUnit(UnitActor target) {
				if (validUnits.contains(target)) {
					card.onTargetUnit(player, target.getUnit());
					super.onSelectUnit(target);
				}
			}
		};
		
		//Add all units within range to valid targets list
		for (UnitCard target : ps.getUm().getUnitsWithinDistance(player.getOccupied(), range)) {
			newStage.addValidUnit(target.getUnitActor());
		}
		
		ps.setCurrentSelection(newStage);
	}
	
	/**
	 * Acquire all units within a range from a selected square
	 * @param start square to start searching from
	 * @param range: look for units up to this number away from start
	 * @return list of nearby units
	 */
	public ArrayList<UnitCard> getUnitsWithinDistance(Square start, int range) {
		ArrayList<UnitCard> targets = new ArrayList<UnitCard>();
		ArrayList<Square> squares = new ArrayList<Square>();
		
		squares.add(start);
		
		//This method simply adds neighbors of all squares in the list to the list range times.
		//Not the most efficient probably, but it works fine
		for (int i = 1; i <= range; i++) {
			
			ArrayList<Square> temp = new ArrayList<Square>();
			
			for (Square s : squares) {
				for (Square n : s.getNeighbors()) {
					if (!squares.contains(n) && !temp.contains(n)) {
						temp.add(n);
					}
				}
			}
			
			squares.addAll(temp);
		}
		
		for (Square s: squares) {
			targets.addAll(s.getOccupants());
		}
		
		return targets;
	}
	
	/**
	 * This is very similar to getUnitsWithinDistance except we return squares within range instead of units.
	 */
	public ArrayList<Square> getSquaresWithinDistance(Square start, int range) {
		ArrayList<Square> squares = new ArrayList<Square>();
		
		squares.add(start);
		
		for (int i = 1; i <= range; i++) {
			
			ArrayList<Square> temp = new ArrayList<Square>();
			
			for (Square s : squares) {
				for (Square n : s.getNeighbors()) {
					if (!squares.contains(n) && !temp.contains(n)) {
						temp.add(n);
					}
				}
			}
			
			squares.addAll(temp);
		}
		
		return squares;
	}
}
