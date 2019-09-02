package com.mygdx.game.cards;

import com.mygdx.game.cardtags.CardTag;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.Team;

public class MiscCard extends Card{	
	
	public MiscCard(Team team, String name, int rainCost, CardTag... tags) {
		super(name, rainCost, tags);
	}
	
	@Override
	public void onPlay(UnitCard unit) {
		unit.getTeam().discardCard(unit, this);
	}
}
