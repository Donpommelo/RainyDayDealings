package com.mygdx.game.managers;

import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.Deck;
import com.mygdx.game.stuff.Team;
import com.mygdx.game.utils.CardTagProcTime;
import com.mygdx.game.utils.EffectTag;

/**
 * DeckManager manages deck related stuff
 * @author Zachary Tu
 *
 */
public class DeckManager {
	
	private PlayState ps;
	
	public DeckManager(PlayState ps) {
		this.ps = ps;
	}
	
	/**
	 * Team draws the top card of an input deck
	 * @param team: The drawer
	 * @param unit: The unit that draws the card (team dummy if team draw)
	 * @param deck: The deck we are drawing from
	 */
	public void drawTopCard(Team team, UnitCard unit, Deck deck) {
		Card topCard = deck.getTopCard();
		
		if (topCard != null) {
			
			drawCard(team, unit, deck, topCard);
		} else {
			//on empty deck draw.
		}
	}
	
	/**
	 * Like draw top card, except we draw a specific card from the deck (if present)
	 * @param team: The drawer
	 * @param unit: The unit that draws the card (team dummy if team draw)
	 * @param deck: The deck we are drawing from
	 * @param card: The card we draw
	 */
	public void drawCard(Team team, UnitCard unit, Deck deck, Card card) {
		
		if (deck.getCards().contains(card)) {
			removeCardfromDeck(deck, card);
			addCardtoHand(team, unit, card, deck.getType());
		} else {
			//TODO: add a nothing to your hand
		}
	}
	
	/**
	 * Card going from hand -> deck
	 * @param team: The team whose hand the card is removed from
	 * @param unit: The unit responsible for discarding 
	 * @param card: Card to discard
	 */
	public void discardFromHand(Team team, UnitCard unit, Card card) {
		removeCardfromHand(team, card);
		addCardtoDeck(team.getTrashDeck(), unit, card, EffectTag.DISCARD);
	}
	
	/**
	 * Card going into deck
	 * @param team: The team whose hand the card is removed from
	 * @param unit: The unit responsible for shuffling 
	 * @param card: Card to discard
	 */
	public void shuffleCardIntoDeck(Team team, UnitCard unit, Deck deck, Card card) {
		addCardtoDeck(team.getTrashDeck(), unit, card, EffectTag.SHUFFLE);
	}
	
	/**
	 * Card adding to hand
	 * @param team: team whose hand gets the card
	 * @param unit: unit responsible for adding
	 * @param card: card added
	 * @param tag: how is this added to hand? (draw, generate bounce etc)
	 */
	public void addCardtoHand(Team team, UnitCard unit, Card card, EffectTag tag) {
		if (team.getHand().getCards().size() < team.getHandSize()) {
			team.getHand().addCard(card);
		} 
		
		ps.getEm().cardTagProcTime(CardTagProcTime.CARD_TO_HAND, 0, unit, null, card, null, null, tag);
	}
	
	/**
	 * Card adding to deck
	 * @param team: team whose hand gets the card
	 * @param unit: unit responsible for adding
	 * @param card: card added
	 * @param tag: how is this added to hand? (discard, shuffle,  etc)
	 */
	public void addCardtoDeck(Deck deck, UnitCard unit, Card card, EffectTag tag) {
		deck.addCard(card);
		ps.getEm().cardTagProcTime(CardTagProcTime.CARD_TO_DECK, 0, unit, null, card, null, null, tag);
	}
	
	/**
	 * card removed from hand
	 * @param team: hand to remove from
	 * @param card: card removed
	 */
	public void removeCardfromHand(Team team, Card card) {
		team.getHand().removeCard(card);
	}
	
	/**
	 * card removed from deck
	 * @param deck: deck removed from
	 * @param card: card removed
	 */
	public void removeCardfromDeck(Deck deck, Card card) {
		deck.getCards().remove(card);
	}
	
	/**
	 * unit card removed from board
	 * @param card: unit removed
	 */
	public void removeUnitCardfromBoard(UnitCard card) {
		card.reset();
		card.getOccupied().getOccupants().remove(card);
		card.getUnitActor().remove();
	}
}
