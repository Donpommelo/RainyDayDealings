package com.mygdx.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.inputs.PlayerController;
import com.mygdx.game.managers.AssetList;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.TiledObjectManager;

public class PlayState extends GameState {

	private int roundNum;
	
	//This is the player's controller that receives inputs
	protected InputProcessor controller;
		
	//These process and store the map parsed from the Tiled file.
	protected TiledMap map;
	protected OrthogonalTiledMapRenderer tmr;
	
	//Background and black screen used for transitions
	protected Texture bg, black;
	
	private Stage boardStage;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		roundNum = 1;
		
		//load map
		map = new TmxMapLoader().load("boards/test.tmx");
		tmr = new OrthogonalTiledMapRenderer(map);
		boardStage = new Stage();
		gsm.getApp();
		boardStage.setViewport(RainyDayGame.viewportCamera);
		TiledObjectManager.parseSquares(this, map.getLayers().get("square-layer").getObjects());
		
		//Init background image
		this.bg = RainyDayGame.assetManager.get(AssetList.BACKGROUND1.toString());
		this.black = RainyDayGame.assetManager.get(AssetList.BLACK.toString());
		
		controller = new PlayerController(this);
	}

	@Override
	public void show() {
		stage = new Stage() {
			
		};
		
		
		
		app.newMenu(stage);
		
		resetController();
	}
	
	/**
	 * This method gives input to the player as well as the menu.
	 * This is called when a play state is created.
	 */
	public void resetController() {
		controller = new PlayerController(this);
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(boardStage);
		
		inputMultiplexer.addProcessor(controller);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void update(float delta) {
		boardStage.act();
		cameraUpdate();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Render Background
		batch.setProjectionMatrix(hud.combined);
		batch.begin();
		batch.draw(bg, 0, 0, RainyDayGame.CONFIG_WIDTH, RainyDayGame.CONFIG_HEIGHT);
		batch.end();
		
		//Render Tiled Map + world
		tmr.setView(camera);
		tmr.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		boardStage.draw();
		batch.end();
	}

	public void cameraUpdate() {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
	public ArrayList<UnitCard> getActiveUnits() {
		return null;
	}
	
	public Stage getBoardStage() {
		return boardStage;
	}
}
