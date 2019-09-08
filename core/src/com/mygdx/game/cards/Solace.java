package com.mygdx.game.cards;

import com.mygdx.game.states.PlayState;

public class Solace extends MiscCard {	
	
	private final static String name = "Solace";
	private final static int rainCost = 0;
	
	private final static int heal = 4;
	private final static int range = 3;

	
	public Solace(PlayState ps) {
		super(ps, name, rainCost);
	}
	
	@Override
	public void onPlay(final UnitCard player) {
		super.onPlay(player);
		ps.getUm().targetUnitWithinRange(this, player, range);
	}
	
	@Override
	public void onTargetUnit(UnitCard player, UnitCard target) {
		ps.getUm().hpChange(player, target, heal);
	}
}
