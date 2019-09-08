package com.mygdx.game.cards;

import com.mygdx.game.cardtags.CardTag;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.Team;

public class MiscCard extends Card {	
	
	public MiscCard(PlayState ps, String name, int rainCost, CardTag... tags) {
		super(ps, name, rainCost, tags);
	}
	
	@Override
	public void onPlay(UnitCard unit) {
		ps.getDm().discardFromHand(unit.getTeam(), unit, this);
	}
}
