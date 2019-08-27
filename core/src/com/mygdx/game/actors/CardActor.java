package com.mygdx.game.actors;

import com.mygdx.game.stuff.Card;

public class CardActor extends ARDDActor {

	private Card card;
	
	public static final int cardWidth = 50;
	public static final int cardHeight = 70;
	
	public CardActor(Card card) {
		super(0, 0, cardWidth, cardHeight);
		this.card = card;
	}

	public Card getCard() {
		return card;
	}
}
