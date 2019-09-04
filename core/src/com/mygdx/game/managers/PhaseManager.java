package com.mygdx.game.managers;

import java.util.ArrayList;

import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Action;
import com.mygdx.game.stuff.Team;
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
	
	public PhaseManager(PlayState ps) {
		this.ps = ps;
		this.toq = new ArrayList<UnitCard>();
		
	}
	
	public void startofLevel() {
		
		roundNum = 0;
		
		ps.addAction(new Action("Level start", 0.5f) {
			
			@Override
			public void preAction() {
				
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
		
		ps.addAction(new Action("Round: " + roundNum, 0.5f) {
			
			@Override
			public void preAction() {
				
				//sort toq according to speed of each active unit
				toq.clear();
				ps.getToqActor().clearUnits();
				
//				for (UnitCard unit: ps.getActiveUnits()) {
//					addToTOQ(unit);
//					
//				}

				sortTOQ();
			}
		});
		
		
		
		for (UnitCard unit: toq) {
			ps.getToqActor().addUnit(unit);
		}
		
		
		//activate pre-round effects
		
		ps.addAction(new Action("Start!", 0.5f) {
			
			@Override
			public void preAction() {
				
				//draw cards
				for (Team team: ps.getTeams()) {
					team.preRoundDraw();
				}
			}
		});
		
		preTurn();
	}
	
	public void preTurn() {
		
		//if toq is empty, postRound. Otherwise, preTurn again.
		if (toq.isEmpty()) {
			postRound();
		} else {
			//pop off fastest fella
			currentUnit = toq.remove(0);
			
			ps.getToqActor().removeUnit(currentUnit);
			ps.getActionActor().switchUnit(currentUnit);
		}
	}
	
	public void postTurn() {
		//post turn effects.
	}
	
	public void postRound() {
		
		//activate post-round effects
		
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
					
					//TODO: account for speed ties and do stat buff stuff
					if(toq.get(j).getBuffedStat(Stats.SPD) < toq.get(j + 1).getBuffedStat(Stats.SPD)){
						temp = toq.get(j);
						toq.set(j,toq.get(j+1));
						toq.set(j+1,temp);
						flag = true;
					}
				}
			}
		}
	}

	public int getRoundNum() {
		return roundNum;
	}	
}
