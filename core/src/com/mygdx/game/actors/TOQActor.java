package com.mygdx.game.actors;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.mygdx.game.cards.UnitCard;

public class TOQActor extends ARDDActor {

	private HorizontalGroup group;
	private ArrayList<UnitCard> units;
	
	private final static int toqX = 50;
	private final static int toqY = 500;
	private final static int toqWidth = 600;
	private final static int toqHeight = 30;
	
	public TOQActor() {
		super(toqX, toqY, toqWidth, toqHeight);
		
		group = new HorizontalGroup();
		units = new ArrayList<UnitCard>();
	}

	public void addUnit(UnitCard unit) {
		CardActor cardActor = new CardActor(unit);
		group.addActor(cardActor);
		units.add(unit);
		group.center();
	}
	
	public void removeUnit(UnitCard unit) {
		
		if (unit.getCardActor() != null) {
			group.removeActor(unit.getCardActor());
		}
		
		units.remove(unit);	
		group.center();
	}
}
