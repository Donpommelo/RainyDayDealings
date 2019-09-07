package com.mygdx.game.stuff;

import java.util.ArrayList;
import java.util.Collections;

import com.mygdx.game.cards.Longing;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.EffectTag;

public class Deck {

	private PlayState ps;
	private ArrayList<Card> cards;
	private EffectTag type;
	
	public Deck(PlayState ps, ArrayList<Card> cards, EffectTag type) {
		this.ps = ps;
		this.cards = cards;
		this.type = type;
	}
	
	public Deck(PlayState ps) {
		this.ps = ps;
		this.cards = new ArrayList<Card>();
		
		for (int i = 0; i < 15; i++) {
			cards.add(new Longing(ps));
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

	public EffectTag getType() {
		return type;
	}
}
