package com.mygdx.game.actors;

import com.mygdx.game.cards.UnitCard;

public class UnitActionActor  {

	private final static int portraitX = 0;
	private final static int portraitY = 400;

	private UnitCard unit;
	private Text move, skill, wait, endTurn;
	
	
	public UnitActionActor(UnitCard unit) {
		this.unit = unit;
		
		move = new Text("MOVE", 0, 0);
	}
	
	

	public UnitCard getUnit() {
		return unit;
	}

	public void setUnit(UnitCard unit) {
		this.unit = unit;
	}	
}
