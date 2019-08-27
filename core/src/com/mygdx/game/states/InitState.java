package com.mygdx.game.states;

import com.mygdx.game.RainyDayGame;
import com.mygdx.game.actors.LoadingBackdrop;
import com.mygdx.game.managers.AssetList;
import com.mygdx.game.managers.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * This is the very first called state of the game. This is pretty much a loading screen where the AssetManager is loaded.
 * After loading is complete, we automatically transition into the Title Screen.
 * @author Zachary Tu
 *
 */
public class InitState extends GameState {

	/**
	 * Constructor will be called once upon initialization of the StateManager.
	 * @param gsm
	 */
	public InitState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void show() {
		stage = new Stage() {
			{
				//Our only actor here is a loading screen image
				addActor(new LoadingBackdrop());
			}
		};
		app.newMenu(stage);
		loadAssets();
	}

	/**
	 * This is where we load all of the assets of the game. Done upon this state being shown.
	 */
	private void loadAssets() {		
		RainyDayGame.SYSTEM_FONT_UI = new BitmapFont(Gdx.files.internal(AssetList.FIXEDSYS_FONT.toString()), false);
		RainyDayGame.SYSTEM_FONT_SPRITE = new BitmapFont();
		RainyDayGame.DEFAULT_TEXT_COLOR = Color.WHITE;
		
		for (AssetList asset: AssetList.values()) {
            if (asset.getType() != null) {
            	RainyDayGame.assetManager.load(asset.toString(), asset.getType());
            }
        }
	}

	/**
	 * 
	 */
	@Override
	public void update(float delta) {
		if (RainyDayGame.assetManager.update()) {
			
			//If we are done loading, do to title state and set up gsm assets (static atlases and stuff like that)
			getGsm().loadAssets();
			getGsm().addTitleState(InitState.class);
		}
	}

	@Override
	public void render() {}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
