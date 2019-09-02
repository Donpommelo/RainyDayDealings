package com.mygdx.game.stuff;

import java.util.ArrayList;

import com.mygdx.game.actors.CardActor;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.cardtags.CardTag;

public class Card {

	private String name;
	private int rainCost;
	private ArrayList<CardTag> startTags;
	private ArrayList<CardTag> currentTags;
	
	private CardActor cardActor;
	
	public Card(String name, int rainCost, CardTag... tags) {
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
