package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

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
	
	public Category(Tournament tournament, String name, EliminationMode mode) {
		this.rounds = new ArrayList<Round>();
		this.tournament = tournament;
		this.name = name;
		this.mode = mode;
	}
	
	public Round addRound() {
		Round newRound = new Round(this).save();
		appendRound(newRound);
		return getLastRound();
	}
	
	public Round appendRound(Round newRound){		
		this.rounds.add(newRound);
		//this.save();
		return getLastRound();
	}
	
	public Round getLastRound(){
		return this.rounds.get(this.rounds.size()-1);
	}
}
