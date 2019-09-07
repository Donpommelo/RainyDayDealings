package com.mygdx.game.stuff;

import java.util.ArrayList;
import java.util.Arrays;

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
		this.trashDeck = new Deck(ps);
		this.hand = new Hand(ps, player);
		
		this.startingUnits = startingUnits;
		
		deck.shuffleDeck();
		
		this.teamDummy = new UnitCard(ps, this, "dummy", 0, 0, 0, 0, 0);
	}
	
	public Team(PlayState ps, Deck startingDeck, boolean player) {
		this.ps = ps;
		this.deck = startingDeck;
		this.trashDeck = new Deck(ps);
		this.player = player;
		this.hand = new Hand(ps, player);
		
		this.startingUnits = new ArrayList<UnitCard>(Arrays.asList(new UnitCard(ps, this), new UnitCard(ps, this)));
		
		deck.shuffleDeck();
		
		this.teamDummy = new UnitCard(ps, this, "dummy", 0, 0, 0, 0, 0);
	}
	
	public void firstRoundDraw() {
		for (int i = 0; i < firstRoundDraw; i++) {
			ps.getDm().drawTopCard(this, teamDummy, deck);
		}
	}
	
	public void preRoundDraw() {
		for (int i = 0; i < preRoundDraw; i++) {
			ps.getDm().drawTopCard(this, teamDummy, deck);
		}
	}

	public boolean isPlayer() {
		return player;
	}

	public ArrayList<UnitCard> getStartingUnits() {
		return startingUnits;
	}

	public Hand getHand() {
		return hand;
	}

	public Deck getDeck() {
		return deck;
	}

	public Deck getTrashDeck() {
		return trashDeck;
	}
	
	public Deck getEventDeck() {
		return eventDeck;
	}

	public int getHandSize() {
		return handSize;
	}
}
