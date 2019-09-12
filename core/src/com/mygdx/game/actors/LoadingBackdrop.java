package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.RainyDayGame;

/**
 * This is a simple actor drawn when the game is loading
 * @author Zachary Tu
 *
 */
public class LoadingBackdrop extends ARDDActor {

private TextureAtlas atlas;
	
	//This is the animation of this sprite
	private Animation<TextureRegion> loading;
	
	//precentage of assets loaded.
	private float progress;
	
	//width and height of image
	private float width, height;
	
	public LoadingBackdrop() {
		super();
		atlas = new TextureAtlas("ui/anchor_logo.atlas");
		loading = new Animation<TextureRegion>(0.08f, atlas.findRegions("anchor_logo"));
		
		//This image takes up the whole screen.
		this.width = loading.getKeyFrame(0).getRegionWidth();
		this.height = loading.getKeyFrame(0).getRegionHeight();
	}
	
	@Override
    public void draw(Batch batch, float alpha) {
		
		//Draw the animation at the percentage of progress
        progress = assetManager.getProgress();
        batch.draw((TextureRegion) loading.getKeyFrame(progress * loading.getAnimationDuration(), true), 
        		(RainyDayGame.CONFIG_WIDTH - width) / 2, (RainyDayGame.CONFIG_HEIGHT - height) / 2, width, height);
    }
}
