package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import models.Category.EliminationMode;

import java.util.*;

@Entity
public class Result extends Model {
	
	public static enum State {
		Undecided, Won, Lost, Disqualified, Injured
	}
	
	@OneToOne(mappedBy = "result")
	public Fight fight;
	
	@OneToOne
	public Fighter fighterOne;

	@OneToOne
	public Fighter fighterTwo;

	public State fighterOneState;
	public State fighterTwoState;
	
	public Result(Fight fight) {
		this.fight = fight;
		this.fighterOneState = Result.State.Undecided;
		this.fighterTwoState = Result.State.Undecided;
	}
	
	public Result(Fight fight, Fighter fighterOne, Fighter fighterTwo) {
		this.fight = fight;
		this.fighterOne = fighterOne;
		this.fighterTwo = fighterTwo;
		this.fighterOneState = Result.State.Undecided;
		this.fighterTwoState = Result.State.Undecided;
	}

	public Result(Fight fight, Fighter fighterOne, State stateOne, Fighter fighterTwo, State stateTwo) {
		this.fight = fight;
		this.fighterOne = fighterOne;
		this.fighterTwo = fighterTwo;
		this.fighterOneState = stateOne;
		this.fighterTwoState = stateTwo;
	}
}
