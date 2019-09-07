package com.mygdx.game.managers;

import com.mygdx.game.cards.UnitCard;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.CardTagProcTime;
import com.mygdx.game.utils.EffectTag;
import com.mygdx.game.utils.Stats;

public class UnitManager {

	private PlayState ps;
	
	public UnitManager(PlayState ps) {
		this.ps = ps;
	}
	
	public void hpChange(UnitCard perp, UnitCard vic, int amount, EffectTag... tags) {
		
		int finalAmount = amount;
		
		finalAmount = ps.getEm().cardTagProcTime(CardTagProcTime.HP_CHANGE, finalAmount, perp, vic, null, null, null);
		
		vic.setCurrentHp(vic.getCurrentHp() + finalAmount);
		
		int maxHp = vic.getBuffedStat(Stats.HP);
		
		if (vic.getCurrentHp() > maxHp) {
			
			//TODO: if overheal effects are a thing, activate here?
			vic.setCurrentHp(maxHp);
		}
		
		if (vic.getCurrentHp() <= 0) {
			vic.die();
		}
	}
	
	public void rainChange(UnitCard perp, UnitCard vic, int amount) {
		
		int finalAmount = amount;
		
		finalAmount = ps.getEm().cardTagProcTime(CardTagProcTime.RAIN_CHANGE, finalAmount, perp, vic, null, null, null);
		
		vic.setCurrentRain(vic.getCurrentRain() + finalAmount);
		
		//TODO: check for saturation point level
	}
	
	
}
