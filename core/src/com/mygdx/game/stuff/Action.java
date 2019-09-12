package com.mygdx.game.stuff;

/**
 * An Action is anything that happens.
 * Whenever something happens, make a new actoin and add it to playstate's action queue. This action will perform the "something" in its preaction method.
 * This ensures that effects are processed in an orderly manner accounting for things like animation times and whatever
 * @author Zachary Tu
 *
 */
public abstract class Action {

	//This is the text that will be added to the action log when this action is processed
	private String text;
	
	//This is how long between this action being processed and the next action being processed
	private float duration;
	
	//Does this action make text show up in the log
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
	
	/**
	 * This is run when this action is removed from the action queue.
	 */
	public void preAction() {};
	
	/**
	 * This is run when this action is done processing, duration seconds after preAction
	 */
	public void postAction() {};
}
