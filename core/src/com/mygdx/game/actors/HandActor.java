package com.mygdx.game.actors;

import java.util.ArrayList;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;

/**
 * The hand actor represents the player's hand.
 * Note that this is not technically an actor, but rather a class that manages a group of actors.
 * @author Zachary Tu
 *
 */
public class HandActor {

	private final static int handCenter = 550;
	private final static int handY = 0;
	
	private PlayState ps;
	
	//These are all of the cards in the player's hand.
	private ArrayList<Card> cards;

	public HandActor(PlayState ps) {
		this.ps = ps;
		cards = new ArrayList<Card>();
	}
	
	/**
	 * This should be run whenever cards enter or exit the hand.
	 * This makes all of the cards slide to their desired location, spaced evenly and centered.
	 */
	public void syncCardPositions() {
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).getCardActor().addAction(Actions.moveTo(handCenter - ((float)cards.size() - 1) / 2 * CardActor.cardWidth + i * CardActor.cardWidth, handY, .5f, Interpolation.pow5Out));
		}
	}

	/**
	 * This creates a card actor for a desired card and adds it to the player's hand
	 * @param card: card that we should be creating.
	 */
	public void createCard(Card card) {
		CardActor cardActor = new CardActor(ps, card);
		card.setCardActor(cardActor);
		cardActor.setPosition(0, 0);

		ps.getStage().addActor(cardActor);
		
		addCardToHand(cards.size(), card);
	}
	
	/**
	 * This deletes a card from the player's hand, removing its actor as well.
	 * @param card: card to remove
	 */
	public void deleteCard(Card card) {
		
		if (card.getCardActor() != null) {
			card.getCardActor().remove();
			removeCardFromHand(card);
		}
	}
	
	/**
	 * This adds a card to the player's hand but does not create a new actor for it. Used when returning an already created card to the hand
	 * @param index
	 * @param card
	 */
	public void addCardToHand(int index, Card card) {
		cards.add(index, card);
		syncCardPositions();
	}
	
	/**
	 * This removes a card from the player's hand but does not delete its actor. Used when dragging a card out of the hand
	 * @param card: card to remove
	 * @return: index of removed card (-1 if not in hand).
	 */
	public int removeCardFromHand(Card card) {

		int index = cards.indexOf(card);
		cards.remove(card);
		syncCardPositions();
		
		//The reason why we return index is so that, when dragging a card out of the hand and returning it, we know what index it should be returned to.
		return index;
	}
}
