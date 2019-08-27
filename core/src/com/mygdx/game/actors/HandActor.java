package com.mygdx.game.actors;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.mygdx.game.stuff.Card;

public class HandActor extends ARDDActor {

	private HorizontalGroup group;
	private ArrayList<Card> cards;
	
	private final static int handX = 25;
	private final static int handY = 0;
	private final static int handWidth = 500;
	private final static int handHeight = 100;
	private final static float handSpace = -25.0f;

	public HandActor() {
		super(handX, handY, handWidth, handHeight);
		
		group = new HorizontalGroup();
		cards = new ArrayList<Card>();
		
		group.space(handSpace);
	}	

	public void addCard(Card card) {
		
		CardActor cardActor = new CardActor(card);
		
		group.addActor(cardActor);
		cards.add(card);
		group.center();
	}
	
	public void removeCard(Card card) {
		
		if (card.getCardActor() != null) {
			group.removeActor(card.getCardActor());
		}
		cards.remove(card);		
		group.center();
	}
}
