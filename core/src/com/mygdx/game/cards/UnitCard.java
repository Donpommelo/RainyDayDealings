package com.mygdx.game.cards;

import com.mygdx.game.actors.UnitActor;
import com.mygdx.game.board.Square;
import com.mygdx.game.cardtags.CardTag;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;
import com.mygdx.game.stuff.Team;
import com.mygdx.game.utils.Stats;
import com.mygdx.game.utils.CardTagProcTime;
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
		this(ps, team, NameGenerator.generateFirst(), 0, 5, 5, 5, 5);
	}
	
	public void die() {
		occupied.getOccupants().remove(this);
		this.actor.remove();
		
		reset();
		
		team.getTrashDeck().addCard(this);
	}
	
	public void reset() {
		occupied = null;
		currentHp = getBuffedStat(Stats.HP);
		
		//TODO: remove all attached cards
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
		default:
			break;		
		}
		
		baseStat = ps.getEm().cardTagProcTime(CardTagProcTime.STAT_REQ, baseStat, this, null, null, null, null);
		
		return baseStat;
	}
	
	public void moveSquare(Square newSquare) {
		if (occupied != null) {
			occupied.getOccupants().remove(this);
		}
		
		occupied = newSquare;
		newSquare.getOccupants().add(this);
		
		actor.syncUnitPosition();
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

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public int getCurrentRain() {
		return currentRain;
	}

	public void setCurrentRain(int currentRain) {
		this.currentRain = currentRain;
	}

	public int getSatLevel() {
		return satLevel;
	}

	public void setSatLevel(int satLevel) {
		this.satLevel = satLevel;
	}

	public int getHighestSatLevel() {
		return highestSatLevel;
	}

	public void setHighestSatLevel(int highestSatLevel) {
		this.highestSatLevel = highestSatLevel;
	}
}
