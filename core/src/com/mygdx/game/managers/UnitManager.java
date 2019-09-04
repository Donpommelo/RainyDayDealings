package com.mygdx.game.managers;

import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.DamageTag;

public class UnitManager {

	private PlayState ps;
	
	public UnitManager(PlayState ps) {
		this.ps = ps;
	}
	
	public void hpChange(UnitCard perp, UnitCard vic, int amount, DamageTag... tags) {
		
	}
}
