package com.mygdx.game.stuff;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	private ArrayList<Card> cards;
	
	public Deck(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public void shuffleDeck() {
		//are on shuffle effects a thing?
		Collections.shuffle(cards);
	}
	
	public Card getTopCard() {
		
		if (cards.isEmpty()) {
			return null;
		}
		
		return cards.get(0);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
	
}
