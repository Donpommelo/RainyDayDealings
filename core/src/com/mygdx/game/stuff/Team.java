package com.mygdx.game.stuff;

import java.util.ArrayList;

import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

public class Team {

	private PlayState ps;
	
	private int handSize = 10;
	
	private int firstRoundDraw = 5;
	private int preRoundDraw = 1;
	private Hand hand;
	private Deck deck, trashDeck, eventDeck;
	private ArrayList<UnitCard> startingUnits;
	private boolean player;
	
	private UnitCard teamDummy;
	
	public Team(PlayState ps, Deck startingDeck, Deck eventDeck, ArrayList<UnitCard> startingUnits, boolean player) {
		this.ps = ps;
		this.deck = startingDeck;
		this.eventDeck = eventDeck;
		this.trashDeck = new Deck();
		this.hand = new Hand(ps, player);
		
		this.startingUnits = startingUnits;
		
		deck.shuffleDeck();
		
		this.teamDummy = new UnitCard(this, "dummy", 0, 0, 0, 0, 0);
	}
	
	public Team(PlayState ps, Deck startingDeck, boolean player) {
		this.ps = ps;
		this.deck = startingDeck;
		this.trashDeck = new Deck();
		this.player = player;
		this.hand = new Hand(ps, player);
		
		deck.shuffleDeck();
		
		this.teamDummy = new UnitCard(this, "dummy", 0, 0, 0, 0, 0);
	}
	
	public void drawTopCard(UnitCard drawer) {
		Card topCard = deck.getTopCard();
		
		if (topCard != null) {
			
			//before draw effects
			
			deck.getCards().remove(topCard);
			
			if (hand.getCards().size() < handSize) {
				hand.addCard(topCard);
				//after draw effects
			} else {
				
				//overdraw effects?
			}
		}
	}
	
	public void drawCard(UnitCard drawer, Card card) {
		deck.getCards().remove(card);
		
		if (hand.getCards().size() < handSize) {
			
			hand.addCard(card);
			//after draw effects
		} else {
			
			//overdraw effects?
		}
	}
	
	public void discardCard(UnitCard drawer, Card card) {
		hand.removeCard(card);
		trashDeck.addCard(card);
	}
	
	public void firstRoundDraw() {
		for (int i = 0; i < firstRoundDraw; i++) {
			drawTopCard(teamDummy);
		}
	}
	
	public void preRoundDraw() {
		for (int i = 0; i < preRoundDraw; i++) {
			drawTopCard(teamDummy);
		}
	}

	public boolean isPlayer() {
		return player;
	}	
}
