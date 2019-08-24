package com.mygdx.game.managers;

import java.util.ArrayList;

import com.mygdx.game.states.PlayState;
import com.mygdx.game.numbers.Number;

public class NumberManager {

	private PlayState ps;
	
	private ArrayList<Number> numbersIn, numbersOut;
	
	public NumberManager(PlayState ps) {
		this.ps = ps;
		
		numbersIn = new ArrayList<Number>();
		numbersOut = new ArrayList<Number>();
	}
	
	public Number rollNumber() {
		
		if (numbersIn.isEmpty()) {
			for (Number num : numbersOut) {
				addNumber(num);
			}
			numbersOut.clear();
		}
		
		if (numbersIn.isEmpty()) {
			new Number(0);
		}
		
		ps.getGsm();
		Number rolled = numbersIn.get(GameStateManager.generator.nextInt(numbersIn.size()));
		
		numbersIn.remove(rolled);
		numbersOut.remove(rolled);
		
		rolled.onRoll();
		
		//activate on roll effects
		
		return rolled;
	}
	
	public void addNumber(Number num) {
		numbersIn.add(num);
	}
}
