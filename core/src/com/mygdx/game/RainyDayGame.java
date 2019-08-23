package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * RainyDayGame is the game. This is created upon launching the game. It delegates the rendering + updating logic to the GamestateManager.
 * @author Zachary Tu
 *
 */
public class RainyDayGame extends ApplicationAdapter {
	
	public static int CONFIG_WIDTH;
	public static int CONFIG_HEIGHT;
	
	//The main camera scales to the viewport size scaled to this for some reason.
	private final static float BOX2DSCALE = 1.0f;
	
	//Camera and Spritebatch. This is pretty standard stuff. camera follows player. hud is for menu/scene2d stuff
	private OrthographicCamera camera, sprite, hud;
	public static FitViewport viewportCamera, viewportSprite, viewportUI;

	//This is the batch used to render stuff
	private SpriteBatch batch;

	//This is the Gamestate Manager. It manages the current game state.
	//private GameStateManager gsm;
	
	public static BitmapFont SYSTEM_FONT_UI, SYSTEM_FONT_SPRITE;
    public static Color DEFAULT_TEXT_COLOR;
 
    private final static int DEFAULT_WIDTH = 1080;
	private final static int DEFAULT_HEIGHT = 720;
	
	@Override
	public void create () {
		
		CONFIG_WIDTH = DEFAULT_WIDTH;
		CONFIG_HEIGHT = DEFAULT_HEIGHT;
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera(CONFIG_WIDTH * BOX2DSCALE, CONFIG_HEIGHT * BOX2DSCALE);
	    sprite = new OrthographicCamera(CONFIG_WIDTH * BOX2DSCALE, CONFIG_HEIGHT * BOX2DSCALE);
	    hud = new OrthographicCamera(CONFIG_WIDTH * BOX2DSCALE, CONFIG_HEIGHT * BOX2DSCALE);
		viewportCamera = new FitViewport(CONFIG_WIDTH * BOX2DSCALE, CONFIG_HEIGHT * BOX2DSCALE, camera);
	    viewportCamera.apply();
	    viewportSprite = new FitViewport(CONFIG_WIDTH * BOX2DSCALE, CONFIG_HEIGHT * BOX2DSCALE, sprite);
	    viewportSprite.apply();	    
	    viewportUI = new FitViewport(CONFIG_WIDTH * BOX2DSCALE, CONFIG_HEIGHT * BOX2DSCALE, hud);
	    viewportUI.apply();
	    
	    hud.zoom = 1 / BOX2DSCALE;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		
	}
}
