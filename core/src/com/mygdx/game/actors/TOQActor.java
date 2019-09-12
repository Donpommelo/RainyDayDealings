package com.mygdx.game.actors;

import java.util.HashMap;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

/**
 * This manages a group of actors that represents the turn order queue.
 * @author Zachary Tu
 *
 */
public class TOQActor {

	private final static int toqCenter = 400;
	private final static int toqY = 650;
	private final static int toqSpace = 125;
	
	//This is a mapping of units to their actors in the toq
	private HashMap<UnitCard, UnitActor> units;
	private PlayState ps;
	
	public TOQActor(PlayState ps) {
		this.ps = ps;
		units = new HashMap<UnitCard, UnitActor>();
	}

	/**
	 * This is run whenever something is added/removed from the toq.
	 * Moves all unit actors to their locations, evenly spaced and centered
	 */
	public void syncActorPositions() {
		for (int i = 0; i < ps.getPm().getToq().size(); i++) {
			UnitActor actor = units.get(ps.getPm().getToq().get(i));
			
			if (actor != null) {
				actor.addAction(Actions.moveTo(toqCenter - ((float)ps.getPm().getToq().size() - 1) / 2 * toqSpace + i * toqSpace, toqY, .5f, Interpolation.pow5Out));
			}
		}
	}
	
	/**
	 * Add a unit to the toq. 
	 * @param unit: unit to be added
	 */
	public void addUnit(UnitCard unit) {
		
		//if active unit, create new actor and add it to the end of the toq
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
	
	/**
	 * remove unit from the toq
	 * @param unit: unit to be removed
	 */
	public void removeUnit(UnitCard unit) {
		
		//find unit corresponding to input card
		UnitActor actor = units.get(unit);
		
		//if existent, remove from map and delete actor
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
