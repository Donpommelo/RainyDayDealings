package com.mygdx.game.managers;

import java.util.ArrayList;

import com.mygdx.game.board.Square;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.cardtags.CardTag;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.utils.CardTagProcTime;
import com.mygdx.game.utils.EffectTag;

/**
 * This manages effects. mostly rpg-battle-ish stuff
 * 
 * This keeps track of all tags in the game and process their effects.
 * @author Zachary Tu
 *
 */
public class EffectManager {

	private PlayState ps;
	
	//this is statuses/statuses checked for tag processing
	private ArrayList<CardTag> tags;
	private ArrayList<CardTag> tagsChecked;
	
	public EffectManager(PlayState ps) {
		this.ps = ps;
		this.tags = new ArrayList<CardTag>();
		this.tagsChecked = new ArrayList<CardTag>();
	}

	/**
	 * This is run whenever anything that could proc an efect is run
	 * @param procTime: type of proc effect
	 * 
	 * all inputs are just different things certain proc effects need to process
	 * @return: integer in case of effect that modifies a value like on hp change
	 */
	public int cardTagProcTime(CardTagProcTime procTime, int amount, UnitCard fella1, UnitCard fella2, Card card, CardTag tag, Square square, EffectTag... extra) {
		
		int finalAmount = amount;
		
		//remember which tags have already been processed if running this method recursively
		ArrayList<CardTag> oldChecked = new ArrayList<CardTag>();

		for(CardTag t : tagsChecked){
			tags.add(0, t);
			oldChecked.add(t);
		}
		tagsChecked.clear();
		
		
		//iterate through all tags running their effects and marking them as checked
		while(!tags.isEmpty()) {
			CardTag tempTag = tags.get(0);
			
			finalAmount = tempTag.cardTagProcTime(procTime, finalAmount, fella1, fella2, card, tag, square);
			
			if(tags.contains(tempTag)){
				tags.remove(tempTag);
				tagsChecked.add(tempTag);
			}
		}
		
		//set tags checked to their state before this was run
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
