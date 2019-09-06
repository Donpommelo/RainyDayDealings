package com.mygdx.game.managers;

import java.util.ArrayList;

import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Action;
import com.mygdx.game.numbers.Number;

public class NumberManager {

	private PlayState ps;
	
	private ArrayList<Number> numbersIn, numbersOut;
	
	public NumberManager(PlayState ps) {
		this.ps = ps;
		
		numbersIn = new ArrayList<Number>();
		numbersOut = new ArrayList<Number>();
		
		
		for (int i = 1; i < 7; i++) {
			numbersIn.add(new Number(i));
		}
	}
	
	public Number rollNumber() {
		
		final Number rolled = numbersIn.get(GameStateManager.generator.nextInt(numbersIn.size()));
		numbersIn.remove(rolled);
		numbersOut.add(rolled);
		
		ps.addAction(new Action("Rolled a " + rolled.getNum(), 1.0f, true) {});
				
		rolled.onRoll();
		
		if (numbersIn.isEmpty()) {
			ps.addAction(new Action("Numbers Refreshed!", 1.0f, true) {});
			for (Number num : numbersOut) {
				addNumber(num);
			}
			numbersOut.clear();
		}
		
		//activate on roll effects
		
		return rolled;
	}
	
	public void addNumber(Number num) {
		numbersIn.add(num);
	}
}
