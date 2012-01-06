package models;

import play.*;
import play.db.jpa.*;
import play.libs.F;

import javax.persistence.*;

import java.util.*;

@Entity
public class Fight extends Model {
	
	public static enum State {
		Undecided, Decided
	}
	
	@ManyToOne
	public Bracket bracket;

	@ManyToMany
	public List<Fighter> fighters;
	
	@OneToOne
	public Result result;
	
	public State state;
	
	public Fight(Bracket bracket) {
		this.fighters = new ArrayList<Fighter>();
		this.bracket = bracket;
		this.state = Fight.State.Undecided;
	}
	
	public Fight addFighter(Fighter fighter) {
		this.fighters.add(fighter);
		//this.save();
		return this;
	}
	
	public Fight setResult(Result result) {
		this.result = result;
		//this.save();
		return this;
	}
	
	public Fighter getWinner(){
		
		Fighter null_winner = null;
		
		// Winner with bye (freilos)
		if(this.result.fighterOneAssessment.equals(Result.Assessment.Bye)){
			return fighters.get(1);
		}
		else if(this.result.fighterTwoAssessment.equals(Result.Assessment.Bye)){
			return fighters.get(0);
		}
		
		// Winner by disqualification
		if(this.result.fighterOneAssessment.equals(Result.Assessment.Disqualification)){
			return fighters.get(1);
		}
		else if(this.result.fighterTwoAssessment.equals(Result.Assessment.Disqualification)){
			return fighters.get(0);
		}
		
		// Winner by regular win
		if(this.result.fighterOneAssessment.equals(Result.Assessment.Win)){
			return fighters.get(0);
		}
		else if(this.result.fighterTwoAssessment.equals(Result.Assessment.Win)){
			return fighters.get(1);
		}
		
		return null_winner; // no winner! - calling method has to check for equals(null)
	}
	
	public Fighter getLoser(){
		
		Fighter null_winner = null;
		
		// regular loss
		if(this.result.fighterOneAssessment.equals(Result.Assessment.Loss)){
			return fighters.get(0);
		}
		else if(this.result.fighterTwoAssessment.equals(Result.Assessment.Loss)){
			return fighters.get(1);
		}
		
		// disqualification - no loser, continue with bye!
		if(this.result.fighterOneAssessment.equals(Result.Assessment.Disqualification) ||
				this.result.fighterTwoAssessment.equals(Result.Assessment.Disqualification)){
			return null_winner;
		}
		
		return null_winner; // no winner! - calling method has to check for equals(null)		
	}
	
	public void setBye(){
		this.result.fighterTwoAssessment = Result.Assessment.Bye;
	}
}
