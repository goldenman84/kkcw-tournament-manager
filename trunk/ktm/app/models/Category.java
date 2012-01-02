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
	
	@ManyToMany
	public List<Fighter> fighters;
	
	public Category(Tournament tournament, String name, EliminationMode mode) {
		this.rounds = new ArrayList<Round>();
		this.fighters = new ArrayList<Fighter>();
		this.tournament = tournament;
		this.name = name;
		this.mode = mode;
	}
	
	public Category addRound() {
		Round newRound = new Round(this);
		this.rounds.add(newRound);
		this.save();
		return this;
	}
}
