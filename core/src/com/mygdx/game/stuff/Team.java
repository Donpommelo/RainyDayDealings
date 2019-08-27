package com.mygdx.game.stuff;

import java.util.ArrayList;

import com.mygdx.game.cards.UnitCard;

public class Team {

	private int handSize = 10;
	private int preRoundDraw = 1;
	private Hand hand;
	private Deck deck, trashDeck, eventDeck;
	private ArrayList<UnitCard> startingUnits;
	private boolean player;
	
	public Team(Deck startingDeck, Deck eventDeck, ArrayList<UnitCard> startingUnits) {
		this.deck = startingDeck;
		this.eventDeck = eventDeck;
//		this.trashDeck = new Deck();
		
		this.startingUnits = startingUnits;
		
		deck.shuffleDeck();
	}
	
	public Team(Deck startingDeck, boolean player) {
		this.deck = startingDeck;
		this.player = player;
		
		deck.shuffleDeck();
		
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
	
	public void preRoundDraw() {
		for (int i = 0; i < preRoundDraw; i++) {
			drawTopCard(null);
		}
	}
}
