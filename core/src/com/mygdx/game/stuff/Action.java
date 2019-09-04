package com.mygdx.game.stuff;

public abstract class Action {

	private String text;
	private float duration;
	private boolean visible;
	
	public Action(String text, float duration, boolean visible) {
		this.text = text;
		this.duration = duration;
		this.visible = visible;
	}
	
	public String getText() {
		return text;
	}

	public float getDuration() {
		return duration;
	}
	
	public boolean isVisible() { return visible; }
	
	public void preAction() {};
	
	public void postAction() {};
}
