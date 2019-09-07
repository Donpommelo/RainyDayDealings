package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.board.Square;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.numbers.Number;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Action;
import com.mygdx.game.stuff.SelectionStage;
import com.mygdx.game.utils.Stats;

public class UnitActionActor {

	private final static int portraitX = 0;
	private final static int portraitY = 600;
	private final static float scale = 0.5f;
	private final static int padY = 25;


	private PlayState ps;
	private UnitCard unit;
	private Table table;
	private Text title, move, skill, endTurn;
	
	private int movesUsed, skillsUsed, cardsUsed;
	
	public UnitActionActor(final PlayState ps) {
		this.ps = ps;
		
		table = new Table();
		
		title = new Text("", 0, 0);
		title.setScale(scale);
		
		
		move = new Text("MOVE", 0, 0);
		move.setScale(scale);
		
		final UnitActionActor me = this;
		
		move.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				
				if (movesUsed < 1 + unit.getBuffedStat(Stats.MOVE_LIMIT)) {
					movesUsed++;
					
					if (me.getUnit() == null) {
						return;
					}
					
					Number number = ps.getNm().rollNumber();
					moveSequence(number.getNum());
				}
	        }
	    });
		
		skill = new Text("SKILL", 0, 0);
		skill.setScale(scale);
		
		skill.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				//TODO: skill usage
	        }
	    });

		endTurn = new Text("END TURN", 0 ,0);
		endTurn.setScale(scale);
		
		endTurn.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				//end turn
				if (ps.getActionQueue().isEmpty()) {
					ps.getPm().postTurn();
				}
	        }
	    });

		table.add(title).row();
		table.add(move).align(Align.right).padTop(padY).row();
		table.add(skill).align(Align.right).row();
		table.add(endTurn).align(Align.right);
		
		table.setPosition(portraitX, portraitY);
		ps.getStage().addActor(table);
	}
	
	public void switchUnit(UnitCard unit) {
		this.unit = unit;
		title.setText(unit.getName());
		
		movesUsed = 0;
		skillsUsed = 0;
		cardsUsed = 0;
		
		
//		if (unit.getTeam().isPlayer()) {
//			move.setVisible(true);
//			skill.setVisible(true);
//			endTurn.setVisible(true);
//		} else {
//			move.setVisible(false);
//			skill.setVisible(false);
//			endTurn.setVisible(false);
//		}
	}

	public void moveSequence(final int numSquares) {
		
		final UnitActionActor me = this;
		
		SelectionStage newStage = new SelectionStage(ps) {
			
			@Override
			public void onSelectSquare(SquareActor square) {
				if (validSquares.contains(square)) {
					moveSquare(numSquares, square.getSquare());
					super.onSelectSquare(square);
				}
			}
		};
		
		for (Square square : me.getUnit().getOccupied().getNeighbors()) {
			newStage.addValidSquare(square.getActor());
		}
		
		ps.setCurrentSelection(newStage);
	}
	
	public void moveSquare(final int numSquares, final Square next) {
		final UnitActionActor me = this;
		
		ps.addAction(new Action("", 0.25f, false) {
			
			@Override
			public void preAction() {
				Square last = me.getUnit().getOccupied();
				me.getUnit().moveSquare(next);

				if (numSquares <= 1) {
					return;
				}
				
				if (next.getNeighbors().size() == 0) {
					return;
				} else if (next.getNeighbors().size() == 1) {
					moveSquare(numSquares - 1, next.getNeighbors().get(0));
				} else if (next.getNeighbors().size() == 2) {
					for (Square neighbor: next.getNeighbors()) {
						if (neighbor != last) {
							moveSquare(numSquares - 1, neighbor);
						}
					}
				} else {
					moveSequence(numSquares - 1);
				}
			}
		});
	}
	
	public UnitCard getUnit() {
		return unit;
	}
}
