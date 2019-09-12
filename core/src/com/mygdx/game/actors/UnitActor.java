package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

/**
 * A UnitActor is an actor representing a single unit on a square on the board (or the toq atm). 
 * They follow the unit's movement and can be selected.
 * @author Zachary Tu
 *
 */
public class UnitActor extends ARDDActor {

	public static final int width = 16;
	public static final int height = 16;
	
	private PlayState ps;
	private UnitCard unit;
	
	public UnitActor(final PlayState ps, final UnitCard unit) {
		super(0, 0, width, height);
		this.ps = ps;
		this.unit = unit;
		
		final UnitActor me = this;
		
		addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				
				//If the player is supposed to be selecting something right now (selection stage exists) select the unit.
				//Otherwise, select the unit to make their portrait visible in the upper right hand corner of the screen
				if (ps.getCurrentSelection() != null) {
					ps.getCurrentSelection().onSelectUnit(me);
				} else {
					if (ps.getPortrait() != null) {
						
						if (ps.getPortrait().getUnit().equals(unit)) {
							ps.getPortrait().remove();
							ps.setPortrait(null);
							return;
						}
						ps.getPortrait().remove();
					}
					
					PortraitActor portrait = new PortraitActor(unit, 900, 545);
					ps.getStage().addActor(portrait);
					ps.setPortrait(portrait);
				}
	        }
		});
	}
	
	@Override
    public void draw(Batch batch, float alpha) {
		RainyDayGame.SYSTEM_FONT_SPRITE.draw(batch, unit.getName(), getX(), getY() + height);
    }
	
	/**
	 * This is run when the unit moves into a new square. Its location is moved if there are multiple units in the square so they do not overlap
	 */
	public void syncUnitPosition() {
		if (unit.getOccupied() == null) {
			remove();
		} else {
			
			int squareNum = unit.getOccupied().getOccupants().indexOf(unit);
			
			setPosition(unit.getOccupied().getActor().getX() + squareNum % 4 * width, unit.getOccupied().getActor().getY() + squareNum / 4 * height);
			if (getParent() == null) {
				ps.getBoardStage().addActor(this);
			}
		}
	}

	public UnitCard getUnit() {
		return unit;
	}
}
