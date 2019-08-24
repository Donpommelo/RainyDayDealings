package com.mygdx.game.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.states.PlayState;

/**
 * The PlayerController controls the player using key events to process various player actions.
 * The Player Controller is used by the host to map their keystrokes to the playercontroller
 * @author Zachary Tu
 *
 */
public class PlayerController implements InputProcessor {

	private PlayState ps;
	
	//Is the player currently holding move left/right/up/down? This is used for processing holding both buttons -> releasing one. 
	private boolean leftHeld = false;
	private boolean rightHeld = false;
	private boolean upHeld = false;
	private boolean downHeld = false;
	
	public PlayerController(PlayState ps) {
		this.ps = ps;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if (keycode == PlayerAction.PAUSE.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_1.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_2.getKey()) {
					
		}
		
		if (keycode == PlayerAction.PLAY_3.getKey()) {
			
		}

		if (keycode == PlayerAction.PLAY_4.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_5.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_6.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_7.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_8.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_9.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_10.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_11.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_12.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_13.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_14.getKey()) {
			
		}
		
		if (keycode == PlayerAction.PLAY_15.getKey()) {
			
		}
		
		if (keycode == PlayerAction.CYCLE_LEFT.getKey()) {
			
		}
		
		if (keycode == PlayerAction.CYCLE_RIGHT.getKey()) {
			
		}
		
		if (keycode == PlayerAction.SELECT.getKey()) {
			
		}
		
		if (keycode == PlayerAction.MOVE.getKey()) {
			
		}
		
		if (keycode == PlayerAction.SKILL.getKey()) {
			
		}
		
		if (keycode == PlayerAction.WAIT.getKey()) {
			
		}
		
		if (keycode == PlayerAction.END_TURN.getKey()) {
			
		}
		
		if (keycode == PlayerAction.CAMERA_LEFT.getKey()) {
			leftHeld = true;
			if (!rightHeld) {
				ps.setCameraXMove(-1);
			} else {
				ps.setCameraXMove(0);
			}
		}
		
		if (keycode == PlayerAction.CAMERA_RIGHT.getKey()) {
			rightHeld = true;
			if (!leftHeld) {
				ps.setCameraXMove(1);
			} else {
				ps.setCameraXMove(0);
			}
		}
		
		if (keycode == PlayerAction.CAMERA_UP.getKey()) {
			upHeld = true;
			if (!downHeld) {
				ps.setCameraYMove(1);
			} else {
				ps.setCameraYMove(0);
			}
		}
		
		if (keycode == PlayerAction.CAMERA_DOWN.getKey()) {
			downHeld = true;
			if (!upHeld) {
				ps.setCameraYMove(-1);
			} else {
				ps.setCameraYMove(0);
			}
		}
		
		if (keycode == PlayerAction.ZOOM_IN.getKey()) {
			ps.setZoom(-1);
		}
		
		if (keycode == PlayerAction.ZOOM_OUT.getKey()) {
			ps.setZoom(1);
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == PlayerAction.CAMERA_LEFT.getKey()) {
			leftHeld = false;
			if (rightHeld) {
				ps.setCameraXMove(1);
			} else {
				ps.setCameraXMove(0);
			}
		}
		
		if (keycode == PlayerAction.CAMERA_RIGHT.getKey()) {
			rightHeld = false;
			if (leftHeld) {
				ps.setCameraXMove(-1);
			} else {
				ps.setCameraXMove(0);
			}
		}
		
		if (keycode == PlayerAction.CAMERA_UP.getKey()) {
			upHeld = false;
			if (downHeld) {
				ps.setCameraYMove(-1);
			} else {
				ps.setCameraYMove(0);
			}
		}
		
		if (keycode == PlayerAction.CAMERA_DOWN.getKey()) {
			downHeld = false;
			if (upHeld) {
				ps.setCameraYMove(1);
			} else {
				ps.setCameraYMove(0);
			}
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		keyDown(button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		keyUp(button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
	    ps.setCamera(ps.camera.position.x - Gdx.input.getDeltaX(), ps.camera.position.y + Gdx.input.getDeltaY(), false);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		keyDown(amount * 1000);
		keyUp(-amount * 1000);
		return false;
	}

}
