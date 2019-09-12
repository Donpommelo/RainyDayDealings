package com.mygdx.game.managers;

import java.util.ArrayList;

import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Action;
import com.mygdx.game.numbers.Number;

/**
 * This manager keeps track of number rolling.
 * @author Zachary Tu
 *
 */
public class NumberManager {

	private PlayState ps;
	
	//Lists of all bingo numbers in/out of the bingo number rolling thingy
	private ArrayList<Number> numbersIn, numbersOut;
	
	public NumberManager(PlayState ps) {
		this.ps = ps;
		
		numbersIn = new ArrayList<Number>();
		numbersOut = new ArrayList<Number>();
		
		//As default, start with numbers for 1-6
		for (int i = 1; i < 7; i++) {
			numbersIn.add(new Number(i));
		}
	}
	
	/**
	 * This is run whenever any effect "rolls a number" like for battle damage or movement
	 * @return
	 */
	public Number rollNumber() {
		
		//Get a random number and remove it from numbers in the pool
		final Number rolled = numbersIn.get(GameStateManager.generator.nextInt(numbersIn.size()));
		numbersIn.remove(rolled);
		numbersOut.add(rolled);
		
		//Add number roll to the action log
		ps.addAction(new Action("Rolled a " + rolled.getNum(), 1.0f, true) {});
				
		//Activate on roll effects. (usually nothing)
		rolled.onRoll();
		
		//If last number rolled, re add all numbers to the pool
		if (numbersIn.isEmpty()) {
			ps.addAction(new Action("Numbers Refreshed!", 1.0f, true) {});
			for (Number num : numbersOut) {
				addNumber(num);
			}
			numbersOut.clear();
		}
		
		return rolled;
	}
	
	public void addNumber(Number num) {
		numbersIn.add(num);
	}
}
