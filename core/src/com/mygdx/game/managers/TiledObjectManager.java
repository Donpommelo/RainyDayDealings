package com.mygdx.game.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.board.Square;
import com.mygdx.game.states.PlayState;

public class TiledObjectManager {

	public static ArrayList<Square> squares = new ArrayList<Square>();
	public static Map<Square, String> neighboring = new HashMap<Square, String>();
	public static Map<String, Square> neighbored = new HashMap<String, Square>();
	
	public static void parseSquares(PlayState ps, MapObjects objects) {
		
		squares.clear();
		neighboring.clear();
		neighbored.clear();
		
		for(MapObject object : objects) {
			
			if(object instanceof RectangleMapObject) {
				
				Rectangle rect = ((RectangleMapObject)object).getRectangle();
				
				Square newSquare = new Square(ps, (int)(rect.x), (int)(rect.y), (int)rect.width, (int)rect.height);
				
				squares.add(newSquare);
				neighboring.put(newSquare, object.getProperties().get("neighbors", String.class));
				neighbored.put(object.getProperties().get("squareID", String.class), newSquare);
			}
		}
	}
	
	public static void parseNeighbors() {
		for (Square key : neighboring.keySet()) {
    		for (String id : neighboring.get(key).split(",")) {
    			if (!id.equals("")) {
    				key.addNeighbor(neighbored.getOrDefault(id, null));
    			}
    		}
    	}
	}
}
