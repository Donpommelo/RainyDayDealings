package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.managers.GameStateManager;

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
	private GameStateManager gsm;
	
	public static AssetManager assetManager;
	
	public static BitmapFont SYSTEM_FONT_UI, SYSTEM_FONT_SPRITE;
    public static Color DEFAULT_TEXT_COLOR;
 
    private final static int DEFAULT_WIDTH = 1080;
	private final static int DEFAULT_HEIGHT = 720;
	
	//currentMenu is whatever stage is being drawn in the current gameState
    private Stage currentMenu;
    
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
	    
	    assetManager = new AssetManager(new InternalFileHandleResolver());
	    gsm = new GameStateManager(this);
	    gsm.addSplashState(null);
	}

	@Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
		currentMenu.act();

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		viewportCamera.apply();
		gsm.render();
		
		currentMenu.getViewport().apply();
		currentMenu.getBatch().setColor(1, 1, 1, 1);
		currentMenu.draw();
	}
	
	/**
	 * Run when the window resizes. We adjust each camera.
	 */
	@Override
	public void resize (int width, int height) {
		viewportCamera.update((int)(width * BOX2DSCALE), (int)(height * BOX2DSCALE), true);
		viewportSprite.update((int)(width * BOX2DSCALE), (int)(height * BOX2DSCALE), true);
		viewportUI.update((int)(width * BOX2DSCALE), (int)(height * BOX2DSCALE), true);
	}
	
	/**
	 * This is run when we add a new menu. (Usually when a new stage is added by a state)
	 * We want to always have the player touching the new menu
	 * @param menu
	 */
	public void newMenu(Stage menu) {
		currentMenu = menu;
		currentMenu.setViewport(viewportUI);
		Gdx.input.setInputProcessor(currentMenu);
	}
	
	/**
	 * Upon deleting, this is run. Again, we delegate to the gsm.
	 * We also need to dispose of anything else here. (such as the batch)
	 */
	@Override
	public void dispose () {
		gsm.dispose();
		batch.dispose();
	}
	
	public OrthographicCamera getCamera() {return camera;}
	
	public OrthographicCamera getHud() {return hud;}
	
	public OrthographicCamera getSprite() {return sprite;}

	public SpriteBatch getBatch() {return batch;}
}
