package com.mygdx.game.actors;

import java.util.HashMap;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

public class TOQActor {

	private HashMap<UnitCard, UnitActor> units;
	private PlayState ps;
	
	private final static int toqCenter = 400;
	private final static int toqY = 650;
	private final static int toqSpace = 125;
	
	public TOQActor(PlayState ps) {
		this.ps = ps;
		units = new HashMap<UnitCard, UnitActor>();
	}

	public void syncActorPositions() {
		for (int i = 0; i < ps.getPm().getToq().size(); i++) {
			UnitActor actor = units.get(ps.getPm().getToq().get(i));
			
			if (actor != null) {
				actor.addAction(Actions.moveTo(toqCenter - ((float)ps.getPm().getToq().size() - 1) / 2 * toqSpace + i * toqSpace, toqY, .5f, Interpolation.pow5Out));
			}
		}
	}
	
	public void addUnit(UnitCard unit) {
		if (unit.getUnitActor() != null) {
			UnitActor cardActor = new UnitActor(ps, unit);
			cardActor.setPosition(unit.getUnitActor().getX(), unit.getUnitActor().getY());
			
			units.put(unit, cardActor);
			ps.getStage().addActor(cardActor);
			
		} else {
			//oops you just added an inactive unit to the toq
		}
		syncActorPositions();
	}
	
	public void removeUnit(UnitCard unit) {
		
		UnitActor actor = units.get(unit);
		
		if (actor != null) {
			actor.addAction(Actions.sequence(Actions.moveBy(0, 800), Actions.removeActor()));
			units.remove(unit);
		}
		syncActorPositions();
	}
	
	public void clearUnits() {
		units.clear();
	}
}
