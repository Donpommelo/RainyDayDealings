package com.mygdx.game.cards;

import java.util.ArrayList;

import com.mygdx.game.cardtags.CardTag;

public class Card {

	private String name, descr;
	private int rainCost;
	private ArrayList<CardTag> startTags;
	private ArrayList<CardTag> currentTags;
	
	
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
}
