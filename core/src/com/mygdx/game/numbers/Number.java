package com.mygdx.game.numbers;

/**
 * This represents a rollable number in the pool of numbers.
 * This is pretty much usually just an integer.
 * Sometimes, there will be an onroll effect
 * @author Zachary Tu
 *
 */
public class Number {

	private int number;
	
	public Number(int num) {
		this.number = num;
	}
	
	public void onRoll() {};
	
	public int getNum() { return number; }
}
