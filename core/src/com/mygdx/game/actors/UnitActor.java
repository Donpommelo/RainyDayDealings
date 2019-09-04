package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;

public class UnitActor extends ARDDActor {

	public static final int width = 25;
	public static final int height = 25;
	
	private PlayState ps;
	private UnitCard unit;
	
	public UnitActor(PlayState ps, UnitCard unit) {
		super(0, 0, width, height);
		this.ps = ps;
		this.unit = unit;
	}
	
	@Override
    public void draw(Batch batch, float alpha) {
		RainyDayGame.SYSTEM_FONT_SPRITE.draw(batch, unit.getName(), getX(), getY() + height);
    }
	
	public void syncUnitPosition() {
		if (unit.getOccupied() == null) {
			remove();
		} else {
			setPosition(unit.getOccupied().getActor().getX(), unit.getOccupied().getActor().getY());
			if (getParent() == null) {
				ps.getBoardStage().addActor(this);
			}
		}
	}
}
