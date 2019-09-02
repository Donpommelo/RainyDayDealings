package com.mygdx.game.actors;

import java.util.ArrayList;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;

public class HandActor {

	private PlayState ps;
	
	private HorizontalGroup group;
	private ArrayList<Card> cards;
	
	private final static int handX =100;
	private final static int handY = 0;
	private final static int handWidth = 800;
	private final static int handHeight = 100;
	private final static float handSpace = 0.0f;

	public HandActor(PlayState ps) {
		this.ps = ps;
		group = new HorizontalGroup();
		cards = new ArrayList<Card>();
		
		group.setPosition(handX, handY);
		group.setSize(handWidth, handHeight);
		group.space(handSpace);
		
		this.ps.getStage().addActor(group);
	}
	
	public void syncCardPositions() {
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).getCardActor().addAction(Actions.moveTo(0, 0, .5f, Interpolation.pow5Out));
		}
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
