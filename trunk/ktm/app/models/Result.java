package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Result extends Model {
	
	public static enum Assessment {
		None, Win, Loss, Disqualification, Bye
	}
	
	public static enum Condition {
		OK, Injury
	}

	public Assessment fighterOneAssessment;
	public Assessment fighterTwoAssessment;
	
	public Condition fighterOneCondition;
	public Condition fighterTwoCondition;
	
	public Result() {
		this.fighterOneAssessment = Result.Assessment.None;
		this.fighterTwoAssessment = Result.Assessment.None;
		this.fighterOneCondition = Result.Condition.OK;
		this.fighterTwoCondition = Result.Condition.OK;
	}
	
	public Result(Assessment stateOne, Assessment stateTwo) {
		this.fighterOneAssessment = stateOne;
		this.fighterTwoAssessment = stateTwo;
		this.fighterOneCondition = Result.Condition.OK;
		this.fighterTwoCondition = Result.Condition.OK;
	}
	
	public Assessment getFighterOneAssessment() {
		return this.fighterOneAssessment;
	}
	
	public Assessment setFighterOneAssessment(Assessment assessment) {
		return (this.fighterOneAssessment = assessment);
	}
	
	public Assessment getFighterTwoAssessment() {
		return this.fighterTwoAssessment;
	}
	
	public Assessment setFighterTwoAssessment(Assessment assessment) {
		return (this.fighterTwoAssessment = assessment);
	}
	
	public Condition getFighterOneCondition() {
		return this.fighterOneCondition;
	}
	
	public Condition setFighterOneCondition(Condition condition) {
		return (this.fighterOneCondition = condition);
	}
	
	public Condition getFighterTwoCondition() {
		return this.fighterTwoCondition;
	}
	
	public Condition setFighterTwoCondition(Condition condition) {
		return (this.fighterTwoCondition = condition);
	}
	
	/**
	 * Merges a given Result instance to itself and saves the changes in DB.
	 * 
	 * @param {models.Result} result The Result to merge the properties from.
	 * @param {models.Result} The modified and persisted Result.
	 */
	public Result merge(Result result) {
		Assessment fighterOneAssessment = result.getFighterOneAssessment();
		Assessment fighterTwoAssessment = result.getFighterTwoAssessment();
		Condition fighterOneCondition = result.getFighterOneCondition();
		Condition fighterTwoCondition = result.getFighterTwoCondition();
		
		if (fighterOneAssessment != null) {
			this.setFighterOneAssessment(fighterOneAssessment);
		}
		if (fighterTwoAssessment != null) {
			this.setFighterTwoAssessment(fighterTwoAssessment);
		}
		if (fighterOneCondition != null) {
			this.setFighterOneCondition(fighterOneCondition);
		}
		if (fighterTwoCondition != null) {
			this.setFighterTwoCondition(fighterTwoCondition);
		}
		
		this.save();
		return this;
	}
}
