package com.mygdx.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum AssetList {

	TITLE_CARD("HADAL_PANIC_3.jpg", Texture.class),
	GAME_OVER_CARD("GAME_OVER_CARD.png", Texture.class),
	
	BUTLER_FONT("fonts/butler.fnt", null),
	LEARNING_FONT("fonts/learning_curve.fnt", null),
	FIXEDSYS_FONT("fonts/fixedsys.fnt", null),
	
	UIPATCHIMG("ui/window.png", Texture.class),
	UIPATCHATL("ui/window.atlas", TextureAtlas.class),
	UISKINIMG("ui/uiskin.png", Texture.class),
	UISKINATL("ui/uiskin.atlas", TextureAtlas.class),
	
	;
	
	//Enum constructor and methods.
	private String pathname;
    private Class<?> type;
    
    AssetList(String s, Class<?> c) {
        this.pathname = s;
        this.type = c;
    }

    @Override
    public String toString() {
        return this.pathname;
    }

    public Class<?> getType() { 
    	return type; 
    }
}
