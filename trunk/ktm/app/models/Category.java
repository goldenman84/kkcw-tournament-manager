package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;
import flexjson.JSON;

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
	
	public String getName() {
		return this.name;
	}
	
	public String setName(String name) {
		return (this.name = name);
	}
	
	public EliminationMode getMode() {
		return this.mode;
	}
	
	public EliminationMode setMode(EliminationMode mode) {
		return (this.mode = mode);
	}
	
	public Tournament getTournament() {
		return this.tournament;
	}
	
	public Tournament setTournament(Tournament t) {
		return (this.tournament = t);
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
	
	/**
	 * Clears the rounds assigned to this category.
	 */
	public void clearRounds() {
		
		// TODO: deleting rounds without first iterating through
		// all sub items is causing a hibernate error. This problem
		// should be solved one day...
		for(Round rd : rounds) {
			for(Bracket br : rd.brackets) {
				for (Fight f : br.fights) {
					Logger.info(
						"The fight with id = %s has %s fighters", 
						f.getId(), f.fighters.size()
					);
				}
			}
		}
		
		for(Round rd : rounds) {
			rd.delete();
		}
		this.rounds.clear();
		this.save();
	}
	
	/**
	 * Merges a given Category instance to itself and saves the changes in DB.
	 * @param {models.Category} mcThe Category to merge the properties from.
	 * @param {models.Category} The modified and persisted Category.
	 */
	public Category merge(Category mc) {
		String name = mc.getName();
		EliminationMode mode = mc.getMode();
		Tournament tournament = mc.getTournament();
		
		if (name != null) { this.setName(name); }
		if (mode != null) { this.setMode(mode); }
		if (tournament != null) { this.setTournament(tournament); }
		
		this.save();
		return this;
	}
}
