package com.mygdx.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ARDDActor extends Actor {

	//Reference to asset manager for obtaining assets.
	protected AssetManager assetManager;
	
	private Polygon hitBox = null;
	
	public ARDDActor(AssetManager assetManager) {
		super();
		this.assetManager = assetManager;
	}
	
	public ARDDActor(AssetManager assetManager, int x, int y) {
		this(assetManager);
		setX(x);
		setY(y);
	}
	
	public ARDDActor(AssetManager assetManager, int x, int y, int width, int height) {
		this(assetManager, x, y);
		setWidth(width);
		setHeight(height);
		updateHitBox();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
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
