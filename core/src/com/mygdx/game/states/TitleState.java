package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.actors.MenuWindow;
import com.mygdx.game.actors.Text;
import com.mygdx.game.actors.TitleBackdrop;
import com.mygdx.game.managers.GameStateManager;

/**
 * The TitleState is created upon initializing the game and will display an image.
 * @author Zachary Tu
 *
 */
public class TitleState extends GameState {

	//This table contains the options for the title.
	private Table table;
	
	//These are all of the display and buttons visible to the player.
	private Text playOption, settingOption, exitOption;
	
	//Dimentions and position of the title menu
	private final static int width = 300;
	private final static int height = 180;
	private final static int xOffset = 100;
		
	/**
	 * Constructor will be called once upon initialization of the StateManager.
	 * @param gsm
	 */
	public TitleState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void show() {
		stage = new Stage() {
			{
				addActor(new TitleBackdrop(RainyDayGame.assetManager));
				addActor(new MenuWindow(RainyDayGame.assetManager, gsm, RainyDayGame.CONFIG_WIDTH - width - xOffset, 0, width, height));
				
				table = new Table();
				table.setLayoutEnabled(true);
				table.setPosition(RainyDayGame.CONFIG_WIDTH - width - xOffset, 0);
				table.setSize(width, height);
				addActor(table);
				
				playOption = new Text(RainyDayGame.assetManager, "PLAY", 0, 0, Color.BLACK);
				playOption.setScale(0.5f);
				
				settingOption = new Text(RainyDayGame.assetManager, "SETTINGS", 0, 0, Color.BLACK);
				settingOption.setScale(0.5f);
				
				exitOption = new Text(RainyDayGame.assetManager, "EXIT", 0, 0, Color.BLACK);
				exitOption.setScale(0.5f);
				
				//Play Option plays the game
				playOption.addListener(new ClickListener() {
					
					@Override
			        public void clicked(InputEvent e, float x, float y) {
						getGsm().addPlayState(TitleState.class);
			        }
			    });
				
				//Setting Options brings up setiting menu
				settingOption.addListener(new ClickListener() {
					
					@Override
			        public void clicked(InputEvent e, float x, float y) {
			        	Gdx.app.exit();
			        }
			    });
				
				//Exit Option closes the game
				exitOption.addListener(new ClickListener() {
					
					@Override
			        public void clicked(InputEvent e, float x, float y) {
			        	Gdx.app.exit();
			        }
			    });
				
				table.add(playOption).expandY().row();
				table.add(settingOption).expandY().row();
				table.add(exitOption).expandY().row();
			}
		};
		app.newMenu(stage);
	}
	
	@Override
	public void update(float delta) {}

	@Override
	public void render() {}

	@Override
	public void dispose() { stage.dispose(); }
}
