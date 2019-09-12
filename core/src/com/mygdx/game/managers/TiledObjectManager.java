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
	
	//starting squares denote where the player's units will spawn on
	public static Map<String, Square> starting = new HashMap<String, Square>();

	/**
	 * This parses a tiled object and turns it into a level
	 * @param ps: playstate
	 * @param objects: objects in the tiled object
	 */
	public static void parseSquares(PlayState ps, MapObjects objects) {
		
		//muh memory leak
		squares.clear();
		neighboring.clear();
		neighbored.clear();
		starting.clear();
		
		for(MapObject object : objects) {
		
			//parse map objects into squares in the board
			if(object instanceof RectangleMapObject) {
				
				Rectangle rect = ((RectangleMapObject)object).getRectangle();
				
				Square newSquare = new Square(ps, (int)(rect.x), (int)(rect.y), (int)rect.width, (int)rect.height);
				
				squares.add(newSquare);
				
				if (object.getProperties().get("neighbors", String.class) != null) {
					neighboring.put(newSquare, object.getProperties().get("neighbors", String.class));
				}
				if (object.getProperties().get("squareId", String.class) != null) {
					neighbored.put(object.getProperties().get("squareId", String.class), newSquare);
				}
				if (object.getProperties().get("startId", String.class) != null) {
					starting.put(object.getProperties().get("startId", String.class), newSquare);
				}
			}
		}
		
		parseNeighbors();
	}
	
	/**
	 * This connects each square to its neighbors in the board
	 */
	public static void parseNeighbors() {
		for (Square key : neighboring.keySet()) {
    		for (String id : neighboring.get(key).split(",")) {
    			if (!id.equals("")) {
    				key.addNeighbor(neighbored.get(id));
    			}
    		}
    	}
	}
}
