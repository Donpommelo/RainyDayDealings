package com.mygdx.game.managers;

import com.mygdx.game.actors.SquareActor;
import com.mygdx.game.board.Square;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Action;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.SelectionStage;
import com.mygdx.game.utils.CardTagProcTime;

/**
 * The action manager is where i'm sticking all of the stuff that performs an action.
 * @author Zachary Tu
 *
 */
public class ActionManager {

	private PlayState ps;
	
	public ActionManager(PlayState ps) {
		this.ps = ps;
	}
	
	/**
	 * This is run when a unit plays a card. Process rain/card effect/proc effects
	 * @param unit: player
	 * @param card: card played
	 */
	public void playCard(final UnitCard unit, final Card card) {
		
		//TODO: check stuff like card-limit and signature restrictions
		
		ps.getEm().cardTagProcTime(CardTagProcTime.BEFORE_CARD_PLAYED, 0, unit, null, card, null, null);
		
		ps.getUm().rainChange(unit, unit, card.getRainCost());
		
		ps.addAction(new Action(unit.getName() + " played " + card.getName(), 1.0f, true) {});

		card.onPlay(unit);
		
		ps.getEm().cardTagProcTime(CardTagProcTime.AFTER_CARD_PLAYED, 0, unit, null, card, null, null);
		
	}
	
	/**
	 * This is run when a unit selects an neighboring square to initiate a movement into
	 * @param numSquares: The amount of squares to move
	 * @param unit: The unit that does the moving
	 */
	public void moveSequence(final int numSquares, final UnitCard unit) {
		
		//Pop up a new selection stage that wants a neighboring square.
		SelectionStage newStage = new SelectionStage(ps) {
			
			@Override
			public void onSelectSquare(SquareActor square) {
				
				//When selecting a valid square, we move into it and remove the selection stage.
				if (validSquares.contains(square)) {
					moveSquare(numSquares, square.getSquare(), unit);
					super.onSelectSquare(square);
				}
			}
		};
		
		//add neighboring squares to valid square list
		for (Square square : unit.getOccupied().getNeighbors()) {
			newStage.addValidSquare(square.getActor());
		}
		
		ps.setCurrentSelection(newStage);
	}
	
	/**
	 * This is run when a unit moves into a square as a part of a movement action
	 * @param numSquares: The number of more squares to move.
	 * @param next: This is the square to attempt to move into
	 * @param unit: The unit doing the movement.
	 */
	public void moveSquare(final int numSquares, final Square next, final UnitCard unit) {
		
		//this action represents a single movement into a neighboring square
		ps.addAction(new Action("", 0.25f, false) {
			
			@Override
			public void preAction() {
				
				//Move into square and remember previous square
				Square last = unit.getOccupied();
				unit.moveSquare(next);

				//If we just wanted to move 1 square, we are done.
				if (numSquares <= 1) {
					return;
				}
				
				if (next.getNeighbors().size() == 0) {
					
					//If we enter a square with no neighbors, I probably fucked up making the map.
					return;
				} else if (next.getNeighbors().size() == 1) {
					
					//If moving into a dead end, we next move into the only possible neighbor next.
					moveSquare(numSquares - 1, next.getNeighbors().get(0), unit);
				} else if (next.getNeighbors().size() == 2) {
					
					//If we are moving into a square with 2 neighbors (including the square we are entering from), we move into the other neighbor.
					for (Square neighbor: next.getNeighbors()) {
						if (neighbor != last) {
							moveSquare(numSquares - 1, neighbor, unit);
						}
					}
				} else {
					
					//If entering a fork in the road (>2 neighbors), we do another move sequence
					moveSequence(numSquares - 1, unit);
				}
			}
		});
	}
}
