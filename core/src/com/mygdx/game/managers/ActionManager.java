package com.mygdx.game.managers;

import com.mygdx.game.actors.SquareActor;
import com.mygdx.game.board.Square;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Action;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.SelectionStage;
import com.mygdx.game.utils.CardTagProcTime;

public class ActionManager {

	private PlayState ps;
	
	public ActionManager(PlayState ps) {
		this.ps = ps;
	}
	
	public void playCard(final UnitCard unit, final Card card) {
		
		//TODO: check stuff like card-limit and signature restrictions
		
		ps.getEm().cardTagProcTime(CardTagProcTime.BEFORE_CARD_PLAYED, 0, unit, null, card, null, null);
		
		ps.getUm().rainChange(unit, unit, card.getRainCost());
		
		ps.addAction(new Action(unit.getName() + " played " + card.getName(), 1.0f, true) {});

		card.onPlay(unit);
		
		ps.getEm().cardTagProcTime(CardTagProcTime.AFTER_CARD_PLAYED, 0, unit, null, card, null, null);
		
	}
	
	public void moveSequence(final int numSquares, final UnitCard unit) {
		
		SelectionStage newStage = new SelectionStage(ps) {
			
			@Override
			public void onSelectSquare(SquareActor square) {
				if (validSquares.contains(square)) {
					moveSquare(numSquares, square.getSquare(), unit);
					super.onSelectSquare(square);
				}
			}
		};
		
		for (Square square : unit.getOccupied().getNeighbors()) {
			newStage.addValidSquare(square.getActor());
		}
		
		ps.setCurrentSelection(newStage);
	}
	
	public void moveSquare(final int numSquares, final Square next, final UnitCard unit) {
		
		ps.addAction(new Action("", 0.25f, false) {
			
			@Override
			public void preAction() {
				Square last = unit.getOccupied();
				unit.moveSquare(next);

				if (numSquares <= 1) {
					return;
				}
				
				if (next.getNeighbors().size() == 0) {
					return;
				} else if (next.getNeighbors().size() == 1) {
					moveSquare(numSquares - 1, next.getNeighbors().get(0), unit);
				} else if (next.getNeighbors().size() == 2) {
					for (Square neighbor: next.getNeighbors()) {
						if (neighbor != last) {
							moveSquare(numSquares - 1, neighbor, unit);
						}
					}
				} else {
					moveSequence(numSquares - 1, unit);
				}
			}
		});
	}
}
