package com.mygdx.game.stuff;

public abstract class Action {

	private String text;
	private float duration;
	
	public Action(String text, float duration) {
		this.text = text;
		this.duration = duration;
	}
	
	public String getText() {
		return text;
	}

	public float getDuration() {
		return duration;
	}

	public void preAction() {};
	
	public void postAction() {};
}
