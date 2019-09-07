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
	
	private ArrayList<UnitCard> toq;
	private UnitCard currentUnit;
	
	private int roundNum;
	
	private final static float actionDurationTemp = 1.0f;
	
	public PhaseManager(PlayState ps) {
		this.ps = ps;
		this.toq = new ArrayList<UnitCard>();
		
	}
	
	public void startofLevel() {
		
		roundNum = 0;
		
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
	
	public void preRound() {
		
		roundNum++;
		
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
	
	public void preTurn() {
		
		//pop off fastest fella
		if (toq.isEmpty()) {
			postRound();
		} else {
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
	
	public void postTurn() {
		//post turn effects.
		
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
}
