package com.mygdx.game.managers;

import java.util.Stack;

import com.mygdx.game.RainyDayGame;
import com.mygdx.game.states.GameState;

public class GameStateManager {

	//An instance of the current game
	private RainyDayGame app;
	
	//Stack of GameStates. These are all the states that the player has opened in that order.
	private Stack<GameState> states;
	
	//This enum lists all the different types of gamestates.
	public enum State {
		SPLASH,
		TITLE,
		PLAY,
	}
	
	/**
	 * Constructor called by the game upon initialization
	 * @param game: instance of the current game.
	 */
	public GameStateManager(RainyDayGame game) {
		this.app = game;
		this.states = new Stack<GameState>();
	}
	
	/**
	 * This loads several assets like atlases, skins and patches.
	 * This is called by initmanager after the atlases have been loaded.
	 */
	public void loadAssets() {
		
	}
	
	/**
	 * Run every engine tick. This delegates to the top state telling it how much time has passed since last update.
	 * @param delta: elapsed time in seconds since last engine tick.
	 */
	public void update(float delta) {
		states.peek().update(delta);
	}
	
	/**
	 * Run every engine tick after updating. This will draw stuff and works pretty much like update.
	 */
	public void render() {
		states.peek().render();
	}
	
	/**
	 * Run upon deletion (exiting game). This disposes of all states and clears the stack.
	 */
	public void dispose() {
		for (GameState gs : states) {
			gs.dispose();
		}
		states.clear();
	}
	
	/**
	 * This is run when the window resizes.
	 * @param w: new width of the screen.
	 * @param h: new height of the screen.
	 */
	public void resize(int w, int h) {
		for (Object state : states.toArray()) {
			((GameState) state).resize(w, h);
		};
	}
	
	/**
	 * Remove the top state from the stack
	 * @param lastState: the state we expect to remove. ensures no double-removing
	 */
	public void removeState(Class<? extends GameState> lastState) {
		if (!states.empty()) {
			if (states.peek().getClass().equals(lastState)) {
				states.pop().dispose();
				states.peek().show();
			}
		}
	}
	
	public Stack<GameState> getStates() {
		return states;
	}

	public RainyDayGame getApp() {
		return app;
	}
}
