package com.mygdx.game.board;

import java.util.ArrayList;

import com.mygdx.game.actors.SquareActor;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

/**
 * This is a square in the board.
 * @author Zachary Tu
 *
 */
public class Square {

	
//	private PlayState ps;
	
	//list of squares that are 1 away from this one.
	private ArrayList<Square> neighbors;
	
	//list of units currently inside of this square
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
