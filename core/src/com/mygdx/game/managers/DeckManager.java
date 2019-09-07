package com.mygdx.game.managers;

import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.Deck;
import com.mygdx.game.stuff.Team;
import com.mygdx.game.utils.CardTagProcTime;
import com.mygdx.game.utils.EffectTag;

public class DeckManager {
	
	private PlayState ps;
	
	public DeckManager(PlayState ps) {
		this.ps = ps;
	}
	
	public void drawTopCard(Team team, UnitCard unit, Deck deck) {
		Card topCard = deck.getTopCard();
		
		if (topCard != null) {
			
			drawCard(team, unit, deck, topCard);
		} else {
			//on empty deck draw.
		}
	}
	
	public void drawCard(Team team, UnitCard unit, Deck deck, Card card) {
		
		if (deck.getCards().contains(card)) {
			removeCardfromDeck(deck, card);
			addCardtoHand(team, unit, card, deck.getType());
		} else {
			//TODO: add a nothing to your hand
		}
	}
	
	public void discardFromHand(Team team, UnitCard unit, Card card) {
		removeCardfromHand(team, card);
		addCardtoDeck(team.getTrashDeck(), unit, card, EffectTag.DISCARD);
	}
	
	public void shuffleCardIntoDeck(Team team, UnitCard unit, Deck deck, Card card) {
		addCardtoDeck(team.getTrashDeck(), unit, card, EffectTag.SHUFFLE);
	}
	
	public void addCardtoHand(Team team, UnitCard unit, Card card, EffectTag tag) {
		if (team.getHand().getCards().size() < team.getHandSize()) {
			team.getHand().addCard(card);
			//after draw effects
		} else {
			
			//overdraw effects?
		}
		ps.getEm().cardTagProcTime(CardTagProcTime.CARD_TO_HAND, 0, unit, null, card, null, null, tag);
	}
	
	public void addCardtoDeck(Deck deck, UnitCard unit, Card card, EffectTag tag) {
		deck.addCard(card);
		ps.getEm().cardTagProcTime(CardTagProcTime.CARD_TO_DECK, 0, unit, null, card, null, null, tag);
	}
	
	public void removeCardfromHand(Team team, Card card) {
		team.getHand().removeCard(card);
	}
	
	public void removeCardfromDeck(Deck deck, Card card) {
		deck.getCards().remove(card);
	}
	
	public void removeUnitCardfromBoard(UnitCard card) {
		card.reset();
		card.getOccupied().getOccupants().remove(card);
		card.getUnitActor().remove();
	}
}
