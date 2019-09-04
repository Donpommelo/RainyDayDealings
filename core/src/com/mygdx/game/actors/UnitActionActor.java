package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

public class UnitActionActor {

	private final static int portraitX = 50;
	private final static int portraitY = 600;
	private final static float scale = 0.5f;
	
	private UnitCard unit;
	private Table table;
	private Text title, move, skill, endTurn;
	
	
	public UnitActionActor(PlayState ps) {
		
		table = new Table();
		
		title = new Text("", 0, 0);
		title.setScale(scale);
		move = new Text("MOVE", 0, 0);
		move.setScale(scale);
		skill = new Text("SKILL", 0, 0);
		skill.setScale(scale);
		endTurn = new Text("END TURN", 0 ,0);
		endTurn.setScale(scale);
		
		table.add(title).row();
		table.add(move).row();
		table.add(skill).row();
		table.add(endTurn);
		
		table.setPosition(portraitX, portraitY);
		ps.getStage().addActor(table);
	}
	
	public void switchUnit(UnitCard unit) {
		this.unit = unit;
		title.setText(unit.getName());
		
		if (unit.getTeam().isPlayer()) {
			move.setVisible(true);
			skill.setVisible(true);
			endTurn.setVisible(true);
		} else {
			move.setVisible(false);
			skill.setVisible(false);
			endTurn.setVisible(false);
		}
	}

	
	
	public UnitCard getUnit() {
		return unit;
	}
}
