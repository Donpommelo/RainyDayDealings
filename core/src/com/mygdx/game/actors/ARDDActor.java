package com.mygdx.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.RainyDayGame;

/**
 * All actors in the game are descended from this actor.
 * Contains stuff for managing location and hitbox
 * @author Zachary Tu
 *
 */
public class ARDDActor extends Actor {

	//Reference to asset manager for obtaining assets.
	protected AssetManager assetManager;
	
	private Polygon hitBox = null;
	
	public ARDDActor() {
		super();
		this.assetManager = RainyDayGame.assetManager;
		setDebug(true);
	}
	
	public ARDDActor(int x, int y) {
		this();
		setX(x);
		setY(y);
	}
	
	public ARDDActor(int x, int y, int width, int height) {
		this(x, y);
		setWidth(width);
		setHeight(height);
		updateHitBox();
	}
	
	public Polygon getHitBox() {
		return hitBox;
	}

	public void setHitBox(Polygon hitBox) {
		this.hitBox = hitBox;
	}
	
	public void updateHitBox() {
		hitBox = new Polygon(new float[] {
               getX(), getY(),
               getX() + getWidth(), getY(),
               getX() + getWidth(), getY() + getHeight(),
               getX(), getY() + getHeight()
       });
       hitBox.setOrigin(getX(), getY());
	}
	
	public float getCenterX() {
		return getX() + getWidth() / 2;
	}

	public float getCenterY() {
		return getY() + getHeight() / 2;
	}
}
