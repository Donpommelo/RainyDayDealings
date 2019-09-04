package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

public class UnitActionActor {

	private final static int portraitX = 0;
	private final static int portraitY = 600;
	private final static float scale = 0.5f;
	private final static int padY = 25;

	
	private UnitCard unit;
	private Table table;
	private Text title, move, skill, endTurn;
	
	
	public UnitActionActor(final PlayState ps) {
		
		table = new Table();
		
		title = new Text("", 0, 0);
		title.setScale(scale);
		
		
		move = new Text("MOVE", 0, 0);
		move.setScale(scale);
		
		move.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				//move
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
				//end turn
				ps.getPm().postTurn();
	        }
	    });

		table.add(title).row();
		table.add(move).align(Align.right).padTop(padY).row();
		table.add(skill).align(Align.right).row();
		table.add(endTurn).align(Align.right);
		
		table.setPosition(portraitX, portraitY);
		ps.getStage().addActor(table);
	}
	
	public void switchUnit(UnitCard unit) {
		this.unit = unit;
		title.setText(unit.getName());
		
//		if (unit.getTeam().isPlayer()) {
//			move.setVisible(true);
//			skill.setVisible(true);
//			endTurn.setVisible(true);
//		} else {
//			move.setVisible(false);
//			skill.setVisible(false);
//			endTurn.setVisible(false);
//		}
	}

	
	
	public UnitCard getUnit() {
		return unit;
	}
}
