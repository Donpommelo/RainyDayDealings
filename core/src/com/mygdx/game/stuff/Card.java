package com.mygdx.game.stuff;

import java.util.ArrayList;

import com.mygdx.game.actors.CardActor;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.cardtags.CardTag;
import com.mygdx.game.states.PlayState;

public class Card {

	protected PlayState ps;
	private String name;
	private int rainCost;
	private ArrayList<CardTag> startTags;
	private ArrayList<CardTag> currentTags;
	
	private CardActor cardActor;
	
	public Card(PlayState ps, String name, int rainCost, CardTag... tags) {
		this.ps = ps;
		this.name = name;
		this.rainCost = rainCost;
		
		for (CardTag tag: tags) {
			startTags.add(tag);
			currentTags.add(tag);
		}
	}
	
	public void addTags() {
		
	}
	
	public void onPlay(UnitCard unit) {
		
	}
	
	public String getName() {
		return name;
	}

	public String getCardText() {
		return name;
	}


	public CardActor getCardActor() {
		return cardActor;
	}


	public void setCardActor(CardActor cardActor) {
		this.cardActor = cardActor;
	}
}
