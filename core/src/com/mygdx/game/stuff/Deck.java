package com.mygdx.game.stuff;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	private ArrayList<Card> cards;
	
	public Deck(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public Deck() {
		this.cards = new ArrayList<Card>();
		
		for (int i = 0; i < 10; i++) {
			cards.add(new Card("FUG", 0));
			cards.add(new Card("FRIG", 1));
			cards.add(new Card("FARQUAD", 2));
		}
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

	public void addCard(Card card) {
		cards.add(card);
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
}
