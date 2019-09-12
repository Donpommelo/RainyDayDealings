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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.actors.ActionLog;
import com.mygdx.game.actors.HandActor;
import com.mygdx.game.actors.PortraitActor;
import com.mygdx.game.actors.TOQActor;
import com.mygdx.game.actors.UnitActionActor;
import com.mygdx.game.board.Square;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.inputs.PlayerController;
import com.mygdx.game.managers.ActionManager;
import com.mygdx.game.managers.AssetList;
import com.mygdx.game.managers.DeckManager;
import com.mygdx.game.managers.EffectManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.NumberManager;
import com.mygdx.game.managers.PhaseManager;
import com.mygdx.game.managers.TiledObjectManager;
import com.mygdx.game.managers.UnitManager;
import com.mygdx.game.stuff.Action;
import com.mygdx.game.stuff.Deck;
import com.mygdx.game.stuff.SelectionStage;
import com.mygdx.game.stuff.Team;
import com.mygdx.game.utils.CameraStyles;

/**
 * This is the state where all the playing happens
 * @author Zachary Tu
 *
 */
public class PlayState extends GameState {
	
	
	//This is the player's controller that receives inputs
	protected InputProcessor controller;
		
	//These process and store the map parsed from the Tiled file.
	protected TiledMap map;
	protected OrthogonalTiledMapRenderer tmr;
	
	//Background and black screen used for transitions
	protected Texture bg, black;
	
	private Stage boardStage;
	
	//The current zoom of the camera
	private float zoom;

	//This is the zoom that the camera will lerp towards
	protected float zoomDesired;
	
	//This is the entity that the camera tries to focus on
	private Vector2 cameraTarget;
	private int cameraXMove, cameraYMove = 0;
	private int cameraMinX, cameraMinY, cameraMaxX, cameraMaxY;
	
	//speed that the camera moves
	private final static float cameraSpeed = 15.0f;
	
	//spped that the camera zoom changes
	private final static float zoomSpeed = 0.4f;
	
	//zoom bounds of the level
	private final static float zoomMin = 0.5f;
	private final static float zoomMax = 2.0f;
	
	//All of the teams in the game (usually just player and enemy)
	private ArrayList<Team> teams;
	
	//ui actors and actor managers
	private HandActor handActor;
	private TOQActor toqActor;
	private UnitActionActor actionActor;
	private ActionLog log;
	
	//This process the queue of actions. Added to by any effect.
	private ArrayList<Action> actionQueue;
	private Action currentAction;
	private float actionTimer;
	
	//i want to speak with your manager
	private ActionManager am;
	private EffectManager em;
	private DeckManager dm;
	private PhaseManager pm;
	private NumberManager nm;
	private UnitManager um;
	
	//This is the current selection stage. Null if the plaer is not currently making a selection
	private SelectionStage currentSelection;
	
	//portrait of the currently selected unit
	private PortraitActor portrait;
	
	//Should dragging move the camera. Disabled when the player is dragging a card
	private boolean dragToScroll;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
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
		
		//Set up player input
		controller = new PlayerController(this);
		
		//Set up camera location, zoom and boundaries
		cameraTarget = new Vector2(camera.position.x, camera.position.y);
		zoom = 1.0f;
		zoomDesired = 1.0f;
		
		dragToScroll = true;
		
		cameraMinX = 0;
		cameraMaxX = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
		cameraMinY = 0;
		cameraMaxY = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
		
		//set up teams. (tentatively hard coded_
		this.teams = new ArrayList<Team>();
		teams.add(new Team(this, new Deck(this), false));
		teams.add(new Team(this, new Deck(this), true));
		
		actionQueue = new ArrayList<Action>();
		actionTimer = 0.0f;
		
		am = new ActionManager(this);
		em = new EffectManager(this);
		dm = new DeckManager(this);
		pm = new PhaseManager(this);
		nm = new NumberManager(this);
		um = new UnitManager(this);
		
		setStartingLocations();
		pm.startofLevel();
	}

	@Override
	public void show() {
		stage = new Stage() {
			{
				
				
				
				
			}
		};
		
		//Create ui actors
		log = new ActionLog(this);
		handActor = new HandActor(this);
		toqActor = new TOQActor(this);
		actionActor = new UnitActionActor(this);
		
		app.newMenu(stage);
		
		//reset player inputs
		resetController();
	}
	
	/**
	 * This method gives input to the player as well as the menu.
	 * This is called when a play state is created.
	 */
	public void resetController() {
		controller = new PlayerController(this);
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		
		inputMultiplexer.addProcessor(controller);
		inputMultiplexer.addProcessor(boardStage);
		inputMultiplexer.addProcessor(stage);

		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void update(float delta) {
		
		//If there is a current action, decrement its timer
		//When an action completes, run its post action and clear it
		if (currentAction != null) {
			actionTimer -= delta;
			
			if (actionTimer <= 0.0f) {
				currentAction.postAction();
				currentAction = null;
			}
		} else if (!actionQueue.isEmpty()) {
			
			//if there is no current action, but there are queued up actions, pop off an action and run it.
			
			currentAction = actionQueue.remove(0);
			
			currentAction.preAction();
			actionTimer = currentAction.getDuration();
			
			if (currentAction.isVisible()) {
				log.addAction(currentAction.getText());
			}
		}
		
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
		boardStage.getViewport().apply();
		boardStage.getBatch().setColor(1, 1, 1, 1);
		boardStage.draw();
		batch.end();
	}

	/**
	 * This is called every update. This resets the camera zoom and makes it move towards a desired point (if camera target is not null).
	 */
	public void cameraUpdate() {
		zoom = zoom + (zoomDesired - zoom) * 0.05f;
		camera.zoom = zoom;
		sprite.zoom = zoom;
		
		//move camera target within boundaries
		cameraTarget.set(obeyCameraBounds(cameraTarget.x + cameraXMove * cameraSpeed, cameraTarget.y + cameraYMove * cameraSpeed));
		
		CameraStyles.lerpToTarget(camera, cameraTarget);
		CameraStyles.lerpToTarget(sprite, cameraTarget);
	}
	
	/**
	 * This sets the zoom of the camera to the input float (accounting for zoom limitations)
	 */
	public void setZoom(float incrementZoom) {
		
		float newZoom = zoomDesired + incrementZoom * zoomSpeed;
		
		if (newZoom < zoomMin) {
			zoomDesired = zoomMin;
		} else if (newZoom > zoomMax) {
			zoomDesired = zoomMax;
		} else {
			zoomDesired = newZoom;
		}
	}
	
	/**
	 * Set the camera target to input location
	 * @param x, y: location of camera position
	 * @param lerp: do we lerp or instantly move
	 */
	public void setCamera(float x, float y, boolean lerp) {
		Vector2 newCamera = obeyCameraBounds(x, y);
		
		cameraTarget.x = newCamera.x;
		cameraTarget.y = newCamera.y;
		
		if (!lerp) {
			camera.position.x = newCamera.x;
			camera.position.y = newCamera.y;
		}		
	}
	
	/**
	 * This helper method simply takes in coordinates and makes them obey camera boundaries
	 * @param x
	 * @param y
	 * @return
	 */
	public Vector2 obeyCameraBounds(float x, float y) {
		
		float newX = x;
		float newY = y;
		
		if (x < cameraMinX) {
			newX = cameraMinX;
		}
		
		if (x > cameraMaxX) {
			newX = cameraMaxX;
		}
		
		if (y < cameraMinY) {
			newY = cameraMinY;
		}
		
		if (y > cameraMaxY) {
			newY = cameraMaxY;
		}
		
		return new Vector2(newX, newY);
	}
	
	@Override
	public void dispose() {
		
	}
	
	/**
	 * Add an action to the queue.
	 * @param action: new action
	 */
	public void addAction(Action action) {
		actionQueue.add(action);
	}
	
	/**
	 * temporary setting starting locations for starting units
	 */
	public void setStartingLocations() {
		for (Team team: teams) {
			if (team.isPlayer()) {
				team.getStartingUnits().get(0).moveSquare(TiledObjectManager.starting.get("player1"));
				team.getStartingUnits().get(1).moveSquare(TiledObjectManager.starting.get("player2"));
//				team.getStartingUnits().get(2).moveSquare(TiledObjectManager.starting.get("player3"));
//				team.getStartingUnits().get(3).moveSquare(TiledObjectManager.starting.get("player4"));
			} else {
				team.getStartingUnits().get(0).moveSquare(TiledObjectManager.starting.get("enemy1"));
				team.getStartingUnits().get(1).moveSquare(TiledObjectManager.starting.get("enemy2"));
			}
		}
	}
	
	/**
	 * This method searches all squares and returns all unit occupants
	 * @return: active units
	 */
	public ArrayList<UnitCard> getActiveUnits() {
		
		ArrayList<UnitCard> activeUnits = new ArrayList<UnitCard>();
		
		for (Square square: TiledObjectManager.squares) {
			activeUnits.addAll(square.getOccupants());
		}
		
		return activeUnits;
	}
	
	public Stage getBoardStage() {
		return boardStage;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public void setCameraXMove(int cameraXMove) {
		this.cameraXMove = cameraXMove;
	}

	public void setCameraYMove(int cameraYMove) {
		this.cameraYMove = cameraYMove;
	}

	public HandActor getHandActor() {
		return handActor;
	}

	public TOQActor getToqActor() {
		return toqActor;
	}
	
	public UnitActionActor getActionActor() {
		return actionActor;
	}	

	public ArrayList<Action> getActionQueue() {
		return actionQueue;
	}

	public ActionManager getAm() {
		return am;
	}

	public EffectManager getEm() {
		return em;
	}

	public DeckManager getDm() {
		return dm;
	}

	public PhaseManager getPm() {
		return pm;
	}
	
	public NumberManager getNm() {
		return nm;
	}
	
	public UnitManager getUm() {
		return um;
	}

	public void setUm(UnitManager um) {
		this.um = um;
	}

	public SelectionStage getCurrentSelection() {
		return currentSelection;
	}

	public void setCurrentSelection(SelectionStage currentSelection) {
		this.currentSelection = currentSelection;
	}

	public PortraitActor getPortrait() {
		return portrait;
	}

	public void setPortrait(PortraitActor portrait) {
		this.portrait = portrait;
	}

	public boolean isDragToScroll() {
		return dragToScroll;
	}

	public void setDragToScroll(boolean dragToScroll) {
		this.dragToScroll = dragToScroll;
	}	
	
}
