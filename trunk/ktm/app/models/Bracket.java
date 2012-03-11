package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Bracket extends Model {
	
	public String name;
	
	@ManyToOne
	public Round round;
	
	@OneToMany(mappedBy = "bracket", cascade = CascadeType.ALL)
	public List<Fight> fights;
	
	// constructor
	public Bracket(Round round, String name) {
		this.round = round;
		this.name = name;
		this.fights = new ArrayList<Fight>();
		this.save();
	}
	
	public String getName() {
		return this.name;
	}
	
	// add one empty fight
	public Fight addFight() {
		Fight newFight = new Fight(this).save();
		this.fights.add(newFight);
		this.save();
		return this.fights.get(this.fights.size()-1);
	}
	
	// add multiple empty fights
	public Bracket addFights(int numFights) {
		for(int i=0; i<numFights; i++){
			Fight newFight = new Fight(this).save();
			this.fights.add(newFight);
		}
		this.save();		
		return this;
	}
	
	public void clearResults() {
		for(Fight fi : fights){
			fi.clearResults();
		}
	}
}
