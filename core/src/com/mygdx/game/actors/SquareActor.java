package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.board.Square;
import com.mygdx.game.states.PlayState;

/**
 * This actor represents a single square in the board
 * @author Zachary Tu
 *
 */
public class SquareActor extends ARDDActor {

	//This is the square this actor represents
	private Square square;
	
	public SquareActor(final PlayState ps, Square square, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.square = square;
		final SquareActor me = this;
		
		//When clicked, if there is an active selection stage, select this square.
		addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				if (ps.getCurrentSelection() != null) {
					ps.getCurrentSelection().onSelectSquare(me);
				}
	        }
		});
	}
	
	public Square getSquare() {
		return square;
	}
}
