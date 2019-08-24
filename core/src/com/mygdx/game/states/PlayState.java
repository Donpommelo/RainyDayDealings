package com.mygdx.game.states;

import java.util.ArrayList;

import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.managers.GameStateManager;

public class PlayState extends GameState {

	private int roundNum;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		roundNum = 1;
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void dispose() {
		
	}
	
	public ArrayList<UnitCard> getActiveUnits() {
		return null;
	}

}
