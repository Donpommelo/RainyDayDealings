package com.mygdx.game.managers;

import java.util.ArrayList;

import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Action;
import com.mygdx.game.stuff.Team;
import com.mygdx.game.utils.CardTagProcTime;
import com.mygdx.game.utils.Stats;

/**
 * This managers the phases during RDDs
 * @author Zachary Tu
 *
 */
public class PhaseManager {

	private PlayState ps;
	
	//ordered of list of units in the turn order queue
	private ArrayList<UnitCard> toq;
	
	//This is the unit who is currently making an action
	private UnitCard currentUnit;
	
	//round number
	private int roundNum;
	
	private final static float actionDurationTemp = 1.0f;
	
	public PhaseManager(PlayState ps) {
		this.ps = ps;
		this.toq = new ArrayList<UnitCard>();
		
	}
	
	/**
	 * This is run when the level begins 
	 */
	public void startofLevel() {
		
		roundNum = 0;
		
		//Start level by activating start of level effects and drawing starting cards
		ps.addAction(new Action("level start!", actionDurationTemp, true) {
			
			@Override
			public void preAction() {
				
				ps.getEm().cardTagProcTime(CardTagProcTime.LEVEL_START, 0, null, null, null, null, null);
				
				//draw cards
				for (Team team: ps.getTeams()) {
					team.firstRoundDraw();
				}
			}
		});
		
		preRound();
	}
	
	/**
	 * This is run at the start of rounds
	 */
	public void preRound() {
		
		roundNum++;
		
		//add active units to the toq, sort it and create unit actors for the toq.
		ps.addAction(new Action("Round: " + roundNum, actionDurationTemp, true) {
			
			@Override
			public void preAction() {
				
				//sort toq according to speed of each active unit
				toq.clear();
				ps.getToqActor().clearUnits();
				
				for (UnitCard unit: ps.getActiveUnits()) {
					addToTOQ(unit);
				}

				sortTOQ();
				
				for (UnitCard unit: toq) {
					ps.getToqActor().addUnit(unit);
				}
			}
		});
		
		//draw cards at the start of round and process start of round effects
		ps.addAction(new Action("", actionDurationTemp, false) {
			
			@Override
			public void preAction() {
				
				//draw cards
				for (Team team: ps.getTeams()) {
					team.preRoundDraw();
				}
				
				ps.getEm().cardTagProcTime(CardTagProcTime.BEFORE_ROUND, 0, null, null, null, null, null);
				preTurn();
			}
		});
	}
	
	/**
	 * This is run at the start of each unit's turn in the round
	 */
	public void preTurn() {
		
		//pop off fastest fella. If no fellas left, round is over
		if (toq.isEmpty()) {
			postRound();
		} else {
			
			//fella is removed from toq and gets to perform an action. Before turn effects activate
			ps.addAction(new Action(toq.get(0).getName() + "'s turn", actionDurationTemp, true) {
				
				@Override
				public void preAction() {
					currentUnit = toq.remove(0);
					ps.getToqActor().removeUnit(currentUnit);
					ps.getActionActor().switchUnit(currentUnit);
					
					ps.getEm().cardTagProcTime(CardTagProcTime.BEFORE_TURN, 0, null, null, null, null, null);
				}
			});
		}
	}
	
	/**
	 * This is run when a unit ends their turn
	 */
	public void postTurn() {
		
		//post turn effects. Next unit makes their turn.
		ps.addAction(new Action("", actionDurationTemp, false) {
			
			@Override
			public void preAction() {
				
				ps.getEm().cardTagProcTime(CardTagProcTime.AFTER_TURN, 0, null, null, null, null, null);
				
				if (toq.isEmpty()) {
					postRound();
				} else {
					preTurn();
				}
			}
		});
	}
	
	/**
	 * Run at end of rounds. atm, just does post round effects and restarts next round
	 */
	public void postRound() {
		ps.getEm().cardTagProcTime(CardTagProcTime.AFTER_ROUND, 0, null, null, null, null, null);
		preRound();
	}
	
	public void addToTOQ(UnitCard unit) {
		toq.add(unit);
	}
	
	public void removeFromTOQ(UnitCard unit) {
		toq.remove(unit);
	}
	
	/**
	 * Sort toq according to unit's buffed speed.
	 * speed ties are broken by chance
	 */
	public void sortTOQ() {
		int j;
		boolean flag = true;
		UnitCard temp;
		
		while (flag) {
			flag = false;
			for(j = 0; j < toq.size() - 1; j++){
				if(toq.get(j) != null && toq.get(j+1) != null){
					
					if((toq.get(j).getBuffedStat(Stats.SPD) < toq.get(j + 1).getBuffedStat(Stats.SPD)) ||
							(toq.get(j).getBuffedStat(Stats.SPD) == toq.get(j + 1).getBuffedStat(Stats.SPD) && GameStateManager.generator.nextBoolean())){
						temp = toq.get(j);
						toq.set(j,toq.get(j+1));
						toq.set(j+1,temp);
						flag = true;
					}
				}
			}
		}
	}
	
	public ArrayList<UnitCard> getToq() {
		return toq;
	}

	public int getRoundNum() {
		return roundNum;
	}

	public UnitCard getCurrentUnit() {
		return currentUnit;
	}
}
