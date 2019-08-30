package com.mygdx.game.managers;

import java.util.ArrayList;

import com.mygdx.game.board.Square;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.cardtags.CardTag;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.utils.CardTagProcTime;

public class EffectManager {

	private PlayState ps;
	private ArrayList<CardTag> tags;
	private ArrayList<CardTag> tagsChecked;
	
	public EffectManager(PlayState ps) {
		this.ps = ps;
		this.tags = new ArrayList<CardTag>();
		this.tagsChecked = new ArrayList<CardTag>();
	}

	public int cardTagProcTime(CardTagProcTime procTime, int amount, UnitCard fella1, UnitCard fella2, Card card, CardTag tag, Square square) {
		
		int finalAmount = amount;
		
		ArrayList<CardTag> oldChecked = new ArrayList<CardTag>();
		
		for(CardTag t : tagsChecked){
			tags.add(0, t);
			oldChecked.add(t);
		}
		tagsChecked.clear();
		
		while(!tags.isEmpty()) {
			CardTag tempTag = tags.get(0);
			
			finalAmount = tempTag.cardTagProcTime(procTime, finalAmount, fella1, fella2, card, tag, square);
			
			if(tags.contains(tempTag)){
				tags.remove(tempTag);
				tagsChecked.add(tempTag);
			}
		}
		
		for(CardTag s : tagsChecked){
			if(!oldChecked.contains(s)){
				tags.add(s);
			}
		}
		tagsChecked.clear();
		for(CardTag t : oldChecked){
			tagsChecked.add(t);
		}
		return finalAmount;
	}
	
	public void createTag(CardTag tag) {
		tags.add(tag);
	}
	
	public void deleteTag(CardTag tag) {
		tags.remove(tag);
	}
}
