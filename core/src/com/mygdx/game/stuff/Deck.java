package com.mygdx.game.stuff;

import java.util.ArrayList;
import java.util.Collections;

import com.mygdx.game.states.PlayState;

public class Deck {

	private PlayState ps;
	private ArrayList<Card> cards;
	
	public Deck(PlayState ps, ArrayList<Card> cards) {
		this.ps = ps;
		this.cards = cards;
	}
	
	public Deck(PlayState ps) {
		this.ps = ps;
		this.cards = new ArrayList<Card>();
		
		for (int i = 0; i < 10; i++) {
			cards.add(new Card(ps, "FUG", 0));
			cards.add(new Card(ps, "FRIG", 1));
			cards.add(new Card(ps, "FARQUAD", 2));
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
