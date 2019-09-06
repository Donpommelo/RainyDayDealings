package com.mygdx.game.board;

import java.util.ArrayList;

import com.mygdx.game.actors.SquareActor;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

public class Square {

	
//	private PlayState ps;
	private ArrayList<Square> neighbors;
	private ArrayList<UnitCard> occupants;
	
	private SquareActor actor;
	
	public Square(PlayState ps, int x, int y, int width, int height) {
//		this.ps = ps;
		neighbors = new ArrayList<Square>();
		occupants = new ArrayList<UnitCard>();
		actor = new SquareActor(ps, this, x, y, width, height);
		ps.getBoardStage().addActor(actor);
	}
	
	public void addNeighbor(Square neighbor) {
		neighbors.add(neighbor);
	}

	public ArrayList<UnitCard> getOccupants() {
		return occupants;
	}
	
	public SquareActor getActor() {
		return actor;
	}

	public ArrayList<Square> getNeighbors() {
		return neighbors;
	}	
}
