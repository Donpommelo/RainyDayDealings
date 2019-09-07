package com.mygdx.game.cards;

import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Action;

public class Longing extends MiscCard {	
	
	private final static String name = "Longing";
	private final static int rainCost = 4;
	
	public Longing(PlayState ps) {
		super(ps, name, rainCost);
	}
	
	@Override
	public void onPlay(final UnitCard unit) {
		super.onPlay(unit);
//		ps.getDm().drawTopCard(unit.getTeam(), unit, unit.getTeam().getDeck());
		ps.addAction(new Action("Drew " + 2 +" Cards!", 1.0f, true) {
			
			@Override
			public void preAction() {
				for (int i = 0; i < 2; i++) {
					ps.getDm().drawTopCard(unit.getTeam(), unit, unit.getTeam().getDeck());
				}
			}
		});
	}
}
