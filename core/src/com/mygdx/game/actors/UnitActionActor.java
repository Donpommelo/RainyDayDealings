package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.numbers.Number;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.Stats;

/**
 * This is not technically an actor, but rather a class that manages a group of actors.
 * These are a group of menu options that let the player make actions for the current turn unit
 * @author Zachary Tu
 *
 */
public class UnitActionActor {

	private final static int portraitX = 0;
	private final static int portraitY = 400;
	private final static int portraitWidth = 400;
	private final static int portraitHeight = 300;
	private final static float scale = 0.5f;
	private final static int padY = 40;

	private PlayState ps;
	private UnitCard unit;
	private Table table;
	private Text move, skill, endTurn;
	
	private int movesUsed, skillsUsed, cardsUsed;
	
	public UnitActionActor(final PlayState ps) {
		this.ps = ps;
		
		table = new Table();
		
		table.setPosition(portraitX, portraitY);
		ps.getStage().addActor(table);
	}
	
	/**
	 * This is run when at the start of the turn when a new unit is at the front of the toq.
	 * @param unit: current turn unit
	 */
	public void switchUnit(final UnitCard unit) {
		this.unit = unit;
		
		//reset action limits
		movesUsed = 0;
		skillsUsed = 0;
		cardsUsed = 0;
		
		move = new Text("MOVE", 0, 0);
		move.setScale(scale);
		
		move.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				
				//If the player can still move, roll a number and move.
				if (movesUsed < 1 + unit.getBuffedStat(Stats.MOVE_LIMIT)) {
					movesUsed++;

					Number number = ps.getNm().rollNumber();
					ps.getAm().moveSequence(number.getNum(), unit);
				}
	        }
	    });
		
		skill = new Text("SKILL", 0, 0);
		skill.setScale(scale);
		
		skill.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				//TODO: skill usage
	        }
	    });

		endTurn = new Text("END TURN", 0 ,0);
		endTurn.setScale(scale);
		
		endTurn.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				
				//turn only ended when no actions are being processed.
				if (ps.getActionQueue().isEmpty()) {
					//end turn
					table.clear();
					ps.getPm().postTurn();
				}
	        }
	    });
		table.left().top();
		
		table.setWidth(portraitWidth);
		table.setHeight(portraitHeight);
		table.add(new PortraitActor(unit, 0, 0)).row();
		table.add(move).left().padTop(padY).row();
		table.add(skill).left().row();
		table.add(endTurn).left();
	}

	public UnitCard getUnit() {
		return unit;
	}
}
