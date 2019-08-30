package com.mygdx.game.stuff;

import java.util.ArrayList;

import com.mygdx.game.actors.CardActor;
import com.mygdx.game.cardtags.CardTag;

public class Card {

	private String name, descr;
	private int rainCost;
	private ArrayList<CardTag> startTags;
	private ArrayList<CardTag> currentTags;
	
	private CardActor cardActor;
	
	public Card(String name, String descr, int rainCost, CardTag... tags) {
		this.name = name;
		this.descr = descr;
		this.rainCost = rainCost;
		
		for (CardTag tag: tags) {
			startTags.add(tag);
			currentTags.add(tag);
		}
	}
	
	public void onPlay() {
		
	}
	
	public String getCardText() {
		return "";
	}


	public CardActor getCardActor() {
		return cardActor;
	}


	public void setCardActor(CardActor cardActor) {
		this.cardActor = cardActor;
	}
}
