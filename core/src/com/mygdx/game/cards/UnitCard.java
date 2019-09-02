package com.mygdx.game.cards;

import com.mygdx.game.cardtags.CardTag;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.Team;
import com.mygdx.game.utils.Stats;

public class UnitCard extends Card{	
	
	private Team team;
	private int Hp, Pow, Def, Spd;
	private int currentHp, currentRain, satLevel, highestSatLevel;
	
	public UnitCard(Team team, String name, int rainCost, int hp, int pow, int def, int spd, CardTag... tags) {
		super(name, rainCost, tags);
		this.team = team;
		this.Hp = hp;
		this.Pow = pow;
		this.Def = def;
		this.Spd = spd;
		
		this.currentHp = hp;
		this.currentRain = 0;
		this.satLevel = 0;
		this.highestSatLevel = 0;
	}
	
	public int getBuffedStat(Stats stat) {
		
		int baseStat = 0;
		
		switch(stat) {
		case HP:
			baseStat = Hp;
			break;
		case POW:
			baseStat = Pow;
			break;
		case DEF:
			baseStat = Def;
			break;
		case SPD:
			baseStat = Spd;
			break;		
		}
		
		return baseStat;
	}

	public Team getTeam() {
		return team;
	}	
}
