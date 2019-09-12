package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.managers.AssetList;
import com.mygdx.game.utils.Stats;

/**
 * The portrait actor is visible when more information about a unit is visible.
 * @author Zachary Tu
 *
 */
public class PortraitActor extends ARDDActor {

	
	public final static int width = 150;
	public final static int height = 150;
	
	private Texture bust;
	
	private BitmapFont font;
	private UnitCard unit;
	
	//visible stats
	private int buffedHp, buffedPow, buffedDef, buffedSpd;
	
	public PortraitActor(UnitCard unit, int x, int y) {
		super(x, y, width, height);
		this.unit = unit;
		
		bust = RainyDayGame.assetManager.get(AssetList.SHADE0.toString());
		font = RainyDayGame.SYSTEM_FONT_UI;
		
		updateBuffedStats();
	}
	
	@Override
    public void draw(Batch batch, float alpha) {
		
		batch.draw(bust, this.getX(), this.getY(), width, height);
		
		font.getData().setScale(0.25f);
		font.draw(batch, unit.getName(), this.getX(), this.getY() + height + 20);
		font.draw(batch, "HP: " + unit.getCurrentHp() + "/" + buffedHp  + " RAIN: " + unit.getCurrentRain(), this.getX(), this.getY() - 5);
		font.draw(batch, "POW: " + buffedPow + " DEF: " + buffedDef + " SPD: " + buffedSpd, this.getX(), this.getY() - 25);
	}
	
	/**
	 * This is run when a stat changes. Make sure the visible stat values are synced with the unit's buffed stats.
	 */
	public void updateBuffedStats() {
		this.buffedHp = unit.getBuffedStat(Stats.HP);
		this.buffedPow = unit.getBuffedStat(Stats.POW);
		this.buffedDef = unit.getBuffedStat(Stats.DEF);
		this.buffedSpd = unit.getBuffedStat(Stats.SPD);
	}

	public UnitCard getUnit() {
		return unit;
	}	
}
