package com.mygdx.game.board;

import java.util.ArrayList;

import com.mygdx.game.RainyDayGame;
import com.mygdx.game.actors.SquareActor;
import com.mygdx.game.states.PlayState;

public class Square {

	
	private PlayState ps;
	private ArrayList<Square> neighbors;
	
	private SquareActor actor;
	
	public Square(PlayState ps, int x, int y, int width, int height) {
		this.ps = ps;
		actor = new SquareActor(RainyDayGame.assetManager, x, y, width, height);
		actor.setDebug(true);
		ps.getBoardStage().addActor(actor);
	}
	
	public void addNeighbor(Square neighbor) {
		neighbors.add(neighbor);
	}
}
