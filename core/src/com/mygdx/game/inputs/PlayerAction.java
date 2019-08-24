package com.mygdx.game.inputs;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.managers.GameStateManager;

/**
 * This enum maps to each possible action the player can perform to an input.
 * @author Zachary Tu
 *
 */
public enum PlayerAction {

	PAUSE(Input.Keys.ESCAPE),
	PLAY_1(Input.Keys.NUM_1),
	PLAY_2(Input.Keys.NUM_2),
	PLAY_3(Input.Keys.NUM_3),
	PLAY_4(Input.Keys.NUM_4),
	PLAY_5(Input.Keys.NUM_5),
	PLAY_6(Input.Keys.NUM_6),
	PLAY_7(Input.Keys.NUM_7),
	PLAY_8(Input.Keys.NUM_8),
	PLAY_9(Input.Keys.NUM_9),
	PLAY_10(Input.Keys.NUM_0),
	PLAY_11(Input.Keys.MINUS),
	PLAY_12(Input.Keys.EQUALS),
	PLAY_13(Input.Keys.LEFT_BRACKET),
	PLAY_14(Input.Keys.RIGHT_BRACKET),
	PLAY_15(Input.Keys.BACKSLASH),
	CYCLE_LEFT(Input.Keys.Q),
	CYCLE_RIGHT(Input.Keys.E),
	SELECT(Input.Keys.SPACE),
	CAMERA_LEFT(Input.Keys.A),
	CAMERA_RIGHT(Input.Keys.D),
	CAMERA_UP(Input.Keys.W),
	CAMERA_DOWN(Input.Keys.S),
	MOVE(Input.Keys.Z),
	SKILL(Input.Keys.X),
	WAIT(Input.Keys.C),
	END_TURN(Input.Keys.ESCAPE),
	ZOOM_IN(-1000),
	ZOOM_OUT(1000),
	;
	
	private int key;
	
	PlayerAction(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	/**
	 * Reset key to default bindings
	 */
	public static void resetKeys() {
				
		PAUSE.setKey(Input.Keys.ESCAPE);
		PLAY_1.setKey(Input.Keys.NUM_1);
		PLAY_2.setKey(Input.Keys.NUM_2);
		PLAY_3.setKey(Input.Keys.NUM_3);
		PLAY_4.setKey(Input.Keys.NUM_4);
		PLAY_5.setKey(Input.Keys.NUM_5);
		PLAY_6.setKey(Input.Keys.NUM_6);
		PLAY_7.setKey(Input.Keys.NUM_7);
		PLAY_8.setKey(Input.Keys.NUM_8);
		PLAY_9.setKey(Input.Keys.NUM_9);
		PLAY_10.setKey(Input.Keys.NUM_0);
		PLAY_11.setKey(Input.Keys.MINUS);
		PLAY_12.setKey(Input.Keys.EQUALS);
		PLAY_13.setKey(Input.Keys.LEFT_BRACKET);
		PLAY_14.setKey(Input.Keys.RIGHT_BRACKET);
		PLAY_15.setKey(Input.Keys.BACKSLASH);
		CYCLE_LEFT.setKey(Input.Keys.Q);
		CYCLE_RIGHT.setKey(Input.Keys.E);
		SELECT.setKey(Input.Keys.SPACE);
		CAMERA_LEFT.setKey(Input.Keys.A);
		CAMERA_RIGHT.setKey(Input.Keys.D);
		CAMERA_UP.setKey(Input.Keys.W);
		CAMERA_DOWN.setKey(Input.Keys.S);
		MOVE.setKey(Input.Keys.Z);
		SKILL.setKey(Input.Keys.X);
		WAIT.setKey(Input.Keys.C);
		END_TURN.setKey(Input.Keys.ESCAPE);
		ZOOM_IN.setKey(-1000);
		ZOOM_OUT.setKey(1000);
	}
	
	/**
	 * Retrieve saved bindings from save file.
	 * TODO: If fail to read file, reset to default?
	 */
	public static void retrieveKeys() {
		JsonValue base;
		base = GameStateManager.reader.parse(Gdx.files.internal("save/Keybind.json"));
		
		for (JsonValue d : base) {
			PlayerAction.valueOf(d.name()).setKey(d.getInt("value"));
		}
	}
	
	/**
	 * Save edited keybinds to file
	 */
	public static void saveKeys() {		
		Gdx.files.local("save/Keybind.json").writeString("", false);
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for (PlayerAction a : PlayerAction.values()) {
			map.put(a.name(), a.getKey());
		}
		
		Gdx.files.local("save/Keybind.json").writeString(GameStateManager.json.toJson(map), true);
	}
}
