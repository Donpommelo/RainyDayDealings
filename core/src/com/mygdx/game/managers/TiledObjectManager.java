package com.mygdx.game.managers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.board.Square;
import com.mygdx.game.states.PlayState;

public class TiledObjectManager {

	public static void parseSquares(PlayState ps, MapObjects objects) {
		for(MapObject object : objects) {
			
			if(object instanceof RectangleMapObject) {
				
				Rectangle rect = ((RectangleMapObject)object).getRectangle();
				
				new Square(ps, (int)(rect.x), (int)(rect.y), (int)rect.width, (int)rect.height);
			}
		}
	}
}
