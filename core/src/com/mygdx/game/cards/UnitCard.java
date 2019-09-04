package com.mygdx.game.cards;

import com.mygdx.game.actors.UnitActor;
import com.mygdx.game.board.Square;
import com.mygdx.game.cardtags.CardTag;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.Team;
import com.mygdx.game.utils.Stats;
import com.mygdx.game.utils.NameGenerator;

public class UnitCard extends Card {	
	
	private Team team;
	private int Hp, Pow, Def, Spd;
	private int currentHp, currentRain, satLevel, highestSatLevel;
	
	private Square occupied;
	private UnitActor actor;
	
	public UnitCard(PlayState ps, Team team, String name, int rainCost, int hp, int pow, int def, int spd, CardTag... tags) {
		super(ps, name, rainCost, tags);
		this.team = team;
		this.Hp = hp;
		this.Pow = pow;
		this.Def = def;
		this.Spd = spd;
		
		this.currentHp = hp;
		this.currentRain = 0;
		this.satLevel = 0;
		this.highestSatLevel = 0;
		
		this.actor = new UnitActor(ps, this);
	}
	
	public UnitCard(PlayState ps,Team team) {
		this(ps, team, NameGenerator.generateFirstLast(true), 0, 5, 5, 5, 5);
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
	
	public void moveSquare(Square newSquare) {
		if (newSquare.getOccupant() == null) {
			
			if (occupied != null) {
				occupied.setOccupant(null);
			}
			
			occupied = newSquare;
			newSquare.setOccupant(this);
			
			actor.syncUnitPosition();
		}
	}

	public UnitActor getUnitActor() {
		return actor;
	}


	public Team getTeam() {
		return team;
	}

	public Square getOccupied() {
		return occupied;
	}

	
}
