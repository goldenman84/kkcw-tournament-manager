package models;

import play.*;
import play.db.jpa.*;
import play.libs.F;

import javax.persistence.*;

import java.util.*;

@Entity
public class Fight extends Model {
	
	@ManyToOne
	public Bracket bracket;

	//@OneToMany(mappedBy = "fight")
	@ManyToMany
	public List<Fighter> fighters;
	
	@OneToOne
	public Result result;
	
	public Fight(Bracket bracket) {
		this.fighters = new ArrayList<Fighter>();
		this.bracket = bracket;
	}
	
	public Fight addFighter(Fighter fighter) {
		this.fighters.add(fighter);
		this.save();
		return this;
	}
	
	public Fight setResult(Result result) {
		this.result = result;
		this.save();
		return this;
	}
}
