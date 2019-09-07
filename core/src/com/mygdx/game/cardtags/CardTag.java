package com.mygdx.game.cardtags;

import com.mygdx.game.board.Square;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.utils.CardTagProcTime;
import com.mygdx.game.utils.EffectTag;

public class CardTag {

	private PlayState ps;
	private Card card;
	
	public CardTag(PlayState ps, Card card) {
		this.ps = ps;
		this.card = card;
	}
	
	public void onCreate() {
		ps.getEm().createTag(this);
	}
	
	public void onDelete() {
		ps.getEm().deleteTag(this);
	}
	
	public int cardTagProcTime(CardTagProcTime procTime, int amount, UnitCard fella1, UnitCard fella2, Card card, CardTag tag, Square square, EffectTag... extra) {
		
		int finalAmount = amount;
		
		switch(procTime) {
		case STAT_REQ:
			finalAmount = statRequested(amount);
			break;
		case LEVEL_START:
			levelStart();
			break;
		case LEVEL_END:
			levelEnd();
			break;	
		case BEFORE_ROUND:
			beforeRound();
			break;
		case AFTER_ROUND:
			afterRound();
			break;	
		case BEFORE_TURN:
			beforeTurn(fella1);
			break;
		case AFTER_TURN:
			afterTurn(fella1);
			break;
		case BEFORE_CARD_PLAYED:
			beforeCardPlayed(fella1, card);
			break;
		case AFTER_CARD_PLAYED:
			afterCardPlayed(fella1, card);
			break;
		case MOVEMENT:
			onMovement(fella1, square);
			break;	
		case SQUARE_ENTERED:
			onEnterSquare(fella1, square);
			break;
		case BEFORE_BATTLE:
			beforeBattle(fella1, fella2);
			break;	
		case AFTER_BATTLE:
			afterBattle(fella1, fella2);
			break;
		case ATTACK:
			onAttack(fella1, fella2);
			break;	
		case SKILL:
			onSkill(fella1, card);
			break;	
		case WAIT:
			onWait(fella1);
			break;
		case CARD_TO_HAND:
			onAddtoHand(fella1, card, extra);
			break;
		case CARD_TO_DECK:
			onAddtoDeck(fella1, card, extra);
			break;
		case CARD_ATTACH:
			onAttach(fella1, card);
			break;
		case CARD_MODIFY:
			onModify(card, tag);
			break;
		case HP_CHANGE:
			finalAmount = hpChange(amount, fella1, fella2);
			break;
		case RAIN_CHANGE:
			finalAmount = rainChange(amount, fella1, fella2);
			break;
		case SATURATION_CHANGE:
			saturationChange(amount, fella1);
			break;
		case NUMBER_ROLL:
			numberRoll(amount, fella1);
			break;
		case NUMBER_REFRESH:
			numberRefresh();
			break;
		case TARGET_ACQUISITION:
			targetAcquisition(fella1);
			break;
		case ACTION_ACQUISITION:
			actionAcquisition(fella1);
			break;
		default:
			break;
		}
		
		return finalAmount;
	}
	
	public int statRequested(int amount) { return amount; }
	
	public void levelStart() {}
	
	public void levelEnd() {}
	
	public void beforeRound() {}
	
	public void afterRound() {}
	
	public void beforeTurn(UnitCard unit) {}
	
	public void afterTurn(UnitCard unit) {}
	
	public void beforeCardPlayed(UnitCard unit, Card card) {}
	
	public void afterCardPlayed(UnitCard unit, Card card) {}
	
	public void onMovement(UnitCard unit, Square square) {}
	
	public void onEnterSquare(UnitCard unit, Square square) {}
	
	public void beforeBattle(UnitCard unit1, UnitCard unit2) {}
	
	public void afterBattle(UnitCard unit1, UnitCard unit2) {}
	
	public void onAttack(UnitCard unit1, UnitCard unit2) {}
	
	public void onSkill(UnitCard unit, Card skill) {}

	public void onWait(UnitCard unit) {}
	
	public void onAddtoHand(UnitCard unit, Card card, EffectTag... tag) {}
	
	public void onAddtoDeck(UnitCard unit, Card card, EffectTag... tag) {}
	
	public void onRemoveFromPlay(UnitCard unit, Card card) {}
	
	public void onAttach(UnitCard unit, Card card) {}
	
	public void onModify(Card card, CardTag tag) {}
	
	public int hpChange(int amount, UnitCard unit1, UnitCard unit2) { return amount; }
	
	public int rainChange(int amount, UnitCard unit1, UnitCard unit2) { return amount; }
	
	public void saturationChange(int amount, UnitCard unit) {}
	
	public void numberRoll(int amount, UnitCard unit) {}
	
	public void numberRefresh() {}
	
	public void targetAcquisition(UnitCard unit) {}
	
	public void actionAcquisition(UnitCard unit) {}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
}
