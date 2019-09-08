package com.mygdx.game.stuff;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.mygdx.game.actors.SquareActor;
import com.mygdx.game.actors.UnitActor;
import com.mygdx.game.states.PlayState;

public class SelectionStage {

	protected ArrayList<SquareActor> validSquares;
	protected ArrayList<UnitActor> validUnits;
	private RepeatAction validSelectionIndicator;
	private PlayState ps;

	public SelectionStage(PlayState ps) {
		this.ps = ps;
		this.validSquares = new ArrayList<SquareActor>();
		this.validUnits = new ArrayList<UnitActor>();
		
		validSelectionIndicator = Actions.forever(Actions.sequence(Actions.fadeOut(0.25f), Actions.fadeIn(0.25f)));
	}
	
	public void onSelectSquare(SquareActor square) {
		for (SquareActor actor : validSquares) {
			actor.removeAction(validSelectionIndicator);
		}
		validSquares.clear();
		ps.setCurrentSelection(null);
	}
	
	public void onSelectUnit(UnitActor unit) {
		for (UnitActor actor : validUnits) {
			actor.removeAction(validSelectionIndicator);
		}
		validUnits.clear();
		ps.setCurrentSelection(null);
	}
	
	public void addValidSquare(SquareActor square) {
		validSquares.add(square);
		square.addAction(validSelectionIndicator);
	}
	
	public void addValidUnit(UnitActor unit) {
		validUnits.add(unit);
		unit.addAction(validSelectionIndicator);
	}
}
