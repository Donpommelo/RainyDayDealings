package com.mygdx.game.cards;

import java.util.ArrayList;

public class Team {

	private Hand hand;
	private Deck deck, trashDeck, eventDeck;
	private ArrayList<UnitCard> startingUnits;
	
	public Team(Deck startingDeck, Deck eventDeck, ArrayList<UnitCard> startingUnits) {
		this.deck = startingDeck;
		this.eventDeck = eventDeck;
		this.eventDeck = new Deck();
		
		this.startingUnits = startingUnits;
		
	}
}
