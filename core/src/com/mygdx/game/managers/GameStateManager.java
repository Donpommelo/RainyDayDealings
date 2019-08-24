package com.mygdx.game.managers;

import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.states.*;

public class GameStateManager {

	//An instance of the current game
	private RainyDayGame app;
	
	//Stack of GameStates. These are all the states that the player has opened in that order.
	private Stack<GameState> states;
	
	//skin for ui windows as well as other patches and atlases. Why are these kept here? Dunno.
	private Skin skin;
	private NinePatchDrawable dialogPatch, simplePatch;
	
	//Not sure if this is a sensible thing to do, but we have an rng here so I don't need to make one whenever elsewhere
	public static Random generator;
		
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
		
		generator = new Random();
	}
	
	/**
	 * This loads several assets like atlases, skins and patches.
	 * This is called by initmanager after the atlases have been loaded.
	 */
	public void loadAssets() {
		BitmapFont font24 = new BitmapFont();
		this.skin = new Skin();
		this.skin.addRegions((TextureAtlas) RainyDayGame.assetManager.get(AssetList.UISKINATL.toString()));
		this.skin.add("default-font", font24);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		this.dialogPatch = new NinePatchDrawable(((TextureAtlas) RainyDayGame.assetManager.get(AssetList.UIPATCHATL.toString())).createPatch("UI_box_dialogue"));
		this.simplePatch = new NinePatchDrawable(((TextureAtlas) RainyDayGame.assetManager.get(AssetList.UIPATCHATL.toString())).createPatch("UI_box_simple"));
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
	 * This is run when we initialize the game. This code adds the new splash state.
	 * @param lastState: the state we are adding on top of. ensures no accidental double-adding
	 */
	public void addSplashState(Class<? extends GameState> lastState) {
		if (states.empty()) {
			states.push(new InitState(this));
			states.peek().show();
		} else if (states.peek().getClass().equals(lastState)) {
			states.push(new InitState(this));
			states.peek().show();
		}
	}
	
	/**
	 * This is run when we transition to the title state.
	 * @param lastState: the state we are adding on top of. ensures no accidental double-adding
	 */
	public void addTitleState(Class<? extends GameState> lastState) {
		if (states.empty()) {
			states.push(new TitleState(this));
			states.peek().show();
		} else if (states.peek().getClass().equals(lastState)) {
			states.push(new TitleState(this));
			states.peek().show();
		}
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
	
	public Skin getSkin() {
		return skin;
	}
	
	public NinePatchDrawable getDialogPatch() {
		return dialogPatch;
	}
	
	public NinePatchDrawable getSimplePatch() {
		return simplePatch;
	}
}
