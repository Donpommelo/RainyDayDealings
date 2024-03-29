package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.managers.GameStateManager;

/**
 * Static background actor for title screen.
 */
public class MenuWindow extends ARDDActor{
	
	private GameStateManager gsm;
	
	public MenuWindow(GameStateManager stateManager, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.gsm = stateManager;
	}
	
	@Override
    public void draw(Batch batch, float alpha) {
        gsm.getSimplePatch().draw(batch, getX(), getY(), getWidth(), getHeight());
    }
}