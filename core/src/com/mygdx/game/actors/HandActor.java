package com.mygdx.game.actors;

import java.util.ArrayList;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;

public class HandActor {

	private PlayState ps;
	
	private ArrayList<Card> cards;
	
	private final static int handCenter = 550;
	private final static int handY = 0;

	public HandActor(PlayState ps) {
		this.ps = ps;
		cards = new ArrayList<Card>();
	}
	
	public void syncCardPositions() {
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).getCardActor().addAction(Actions.moveTo(handCenter - ((float)cards.size() - 1) / 2 * CardActor.cardWidth + i * CardActor.cardWidth, handY, .5f, Interpolation.pow5Out));
		}
	}

	public void createCard(Card card) {
		CardActor cardActor = new CardActor(ps, card);
		card.setCardActor(cardActor);
		cardActor.setPosition(0, 0);

		ps.getStage().addActor(cardActor);
		
		addCardToHand(cards.size(), card);
	}
	
	public void deleteCard(Card card) {
		
		if (card.getCardActor() != null) {
			card.getCardActor().remove();
			removeCardFromHand(card);
		}
	}
	
	public void addCardToHand(int index, Card card) {
		cards.add(index, card);
		syncCardPositions();
	}
	
	public int removeCardFromHand(Card card) {

		int index = cards.indexOf(card);
		cards.remove(card);
		syncCardPositions();
		
		return index;
	}
}
