package com.mygdx.game.actors;

import com.mygdx.game.cards.UnitCard;

public class UnitActionActor extends ARDDActor {

	private final static int portraitX = 0;
	private final static int portraitY = 400;

	private UnitCard unit;
	
	public UnitActionActor(UnitCard unit) {
		super(portraitX, portraitY);
		this.unit = unit;
	}

	public UnitCard getUnit() {
		return unit;
	}

	public void setUnit(UnitCard unit) {
		this.unit = unit;
	}	
}
