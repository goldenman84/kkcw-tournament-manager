package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import models.Category.EliminationMode;

import java.util.*;

@Entity
public class Result extends Model {
	
	public static enum Assessment {
		None, Win, Loss, Disqualification, Bye
	}
	
	public static enum Condition {
		OK, Injury
	}
	
	//@OneToOne
	//public Fight fight;

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
}
