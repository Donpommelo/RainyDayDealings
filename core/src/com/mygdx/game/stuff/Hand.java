package com.mygdx.game.stuff;

import java.util.ArrayList;

import com.mygdx.game.states.PlayState;

public class Hand {

	private ArrayList<Card> cards;
	private PlayState ps;
	
	private boolean player;
	
	public Hand(PlayState ps, boolean player) {
		this.ps = ps;
		this.player = player;
		this.cards = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		cards.add(card);
		
		//add cardActor to ui
		if (player) {
			ps.getHandActor().createCard(card);
		}		
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
		
		//remove cardActor from ui
		if (player) {
			ps.getHandActor().deleteCard(card);
		}
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
}
