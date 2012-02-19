package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import flexjson.JSON;

import java.util.*;

@Entity
public class Category extends Model {
	
	public String name;
	
	public static enum EliminationMode {
		Single, Double
	}
	public EliminationMode mode;

	@ManyToOne
	public Tournament tournament;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	public List<Round> rounds;
	
	@OneToMany
	public List<Fighter> fighters;
	
	@ManyToMany
	public List<FightArea> fightareas;
	
	public Category(Tournament tournament, String name, EliminationMode mode) {
		this.rounds = new ArrayList<Round>();
		this.fighters = new ArrayList<Fighter>();
		this.fightareas = new ArrayList<FightArea>();
		this.tournament = tournament;
		this.name = name;
		this.mode = mode;
	}
	
	public Category addFightArea(String name) {
		FightArea fightarea = new FightArea(name).save();
		return this.addFightArea(fightarea);
	}
	
	public Category addFightArea(FightArea fightarea) {
		this.fightareas.add(fightarea);
		this.save();
		return this;
	}
	
	public Round addRound() {
		Round newRound = new Round(this).save();
		appendRound(newRound);
		return getLastRound();
	}
	
	public Round appendRound(Round newRound){		
		this.rounds.add(newRound);
		this.save();
		return getLastRound();
	}
	
	@JSON(include=false) 
	public Round getLastRound(){
		return this.rounds.get(this.rounds.size()-1);
	}
	
	public void clearRounds() {
		for(Round rd : rounds) {
			rd.delete();
		}
		this.rounds.clear();
		this.save();
	}
}
