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

public class UnitManager {

	private PlayState ps;
	
	public UnitManager(PlayState ps) {
		this.ps = ps;
	}
	
	public void hpChange(UnitCard perp, UnitCard vic, int amount, EffectTag... tags) {
		
		int finalAmount = amount;
		
		finalAmount = ps.getEm().cardTagProcTime(CardTagProcTime.HP_CHANGE, finalAmount, perp, vic, null, null, null);
		
		vic.setCurrentHp(vic.getCurrentHp() + finalAmount);
		
		int maxHp = vic.getBuffedStat(Stats.HP);
		
		if (vic.getCurrentHp() > maxHp) {
			
			//TODO: if overheal effects are a thing, activate here?
			vic.setCurrentHp(maxHp);
		}
		
		if (vic.getCurrentHp() <= 0) {
			vic.die();
		}
	}
	
	public void rainChange(UnitCard perp, UnitCard vic, int amount) {
		
		int finalAmount = amount;
		
		finalAmount = ps.getEm().cardTagProcTime(CardTagProcTime.RAIN_CHANGE, finalAmount, perp, vic, null, null, null);
		
		vic.setCurrentRain(vic.getCurrentRain() + finalAmount);
		
		//TODO: check for saturation point level
	}
	
	public ArrayList<UnitCard> getAlliesWithinDistance(Square start, UnitCard unit, int range) {
		ArrayList<UnitCard> targets = new ArrayList<UnitCard>();
		
		for (UnitCard target : getUnitsWithinDistance(start, range)) {
			if (target.getTeam().equals(unit.getTeam())) {
				targets.add(target);
			}
		}
		
		return targets;
	}
	
	public ArrayList<UnitCard> getEnemiesWithinDistance(Square start, UnitCard unit, int range) {
		ArrayList<UnitCard> targets = new ArrayList<UnitCard>();
		
		for (UnitCard target : getUnitsWithinDistance(start, range)) {
			if (!target.getTeam().equals(unit.getTeam())) {
				targets.add(target);
			}
		}
		
		return targets;
	}
	
	public void targetUnitWithinRange(final Card card, final UnitCard player, int range) {
		SelectionStage newStage = new SelectionStage(ps) {
			
			@Override
			public void onSelectUnit(UnitActor target) {
				if (validUnits.contains(target)) {
					card.onTargetUnit(player, target.getUnit());
					super.onSelectUnit(target);
				}
			}
		};
		
		for (UnitCard target : ps.getUm().getUnitsWithinDistance(player.getOccupied(), range)) {
			newStage.addValidUnit(target.getUnitActor());
		}
		
		ps.setCurrentSelection(newStage);
	}
	
	public ArrayList<UnitCard> getUnitsWithinDistance(Square start, int range) {
		ArrayList<UnitCard> targets = new ArrayList<UnitCard>();
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
		
		for (Square s: squares) {
			targets.addAll(s.getOccupants());
		}
		
		return targets;
	}
	
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
