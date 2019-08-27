package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.managers.AssetList;

public class TitleBackdrop extends ARDDActor {

	private Texture backgroundTexture;
	
	public TitleBackdrop() {
		super();
		backgroundTexture = getAssetManager().get(AssetList.TITLE_CARD.toString());
		backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
	}
	
	@Override
    public void draw(Batch batch, float alpha) {
        batch.draw(backgroundTexture, 0, 0, RainyDayGame.CONFIG_WIDTH, RainyDayGame.CONFIG_HEIGHT);
    }
}
