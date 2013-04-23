package models;

import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import com.avaje.ebean.*;

import play.db.ebean.Model;
import flexjson.JSON;

@Entity
public class Fight extends Model {

	public static enum State {
		Undecided, Decided
	}

	@Id
	public Long id;

	@ManyToOne
	public Bracket bracket;

	@ManyToMany
	public List<Fighter> fighters;

	@OneToOne(cascade = CascadeType.REMOVE)
	public Result result;

	@ManyToOne
	public FightArea fightarea;

	public State state;

	public Fight(Bracket bracket) {
		this.fighters = new ArrayList<Fighter>();
		this.bracket = bracket;
		this.state = Fight.State.Undecided;
		this.save();
	}

	// ebean finder class
	public static Finder<Long,Fight> find = new Finder<Long,Fight>(
		Long.class, Fight.class
	);
	public Bracket getBracket() {
		return this.bracket;
	}

	public Bracket setBracket(Bracket bracket) {
		return (this.bracket = bracket);
	}

	public List<Fighter> getFighters() {
		return this.fighters;
	}

	public List<Fighter> setFighters(List<Fighter> fighters) {
		return (this.fighters = fighters);
	}

	public Result getResult() {
		return this.result;
	}

	public Result setResult(Result result) {
		return (this.result = result);
	}

	public FightArea getFightArea() {
		return this.fightarea;
	}

	public FightArea setFightArea(FightArea fightarea) {
		return (this.fightarea = fightarea);
	}

	public State getState() {
		return this.state;
	}

	public State setState(State state) {
		return (this.state = state);
	}

	public Fight addFighter(Fighter fighter) {
		this.fighters.add(fighter);
		this.save();
		return this;
	}

	// setResult() as method name cannot be used due to flexjson.deserializer
	public Fight assignResult(Result result) {
		this.result = result;
		this.save();
		return this;
	}

	public Fight assignFightArea(FightArea fightarea) {
		this.fightarea = fightarea;
		this.save();
		return this;
	}

	public void clearResults() {
		state = Fight.State.Undecided;
		if (result != null) {
			result.fighterOneAssessment = Result.Assessment.None;
			result.fighterTwoAssessment = Result.Assessment.None;
			result.fighterOneCondition = Result.Condition.OK;
			result.fighterTwoCondition = Result.Condition.OK;
		}
		fighters.clear();
		this.save();
	}

	@JSON(include = false)
	public Fighter getWinner() throws ActivationException {

		// throw exception if not decided!
		if (this.state == Fight.State.Undecided) {
			throw new ActivationException();
		}

		Fighter null_winner = null;

		// Winner with bye (freilos)
		if (this.result.fighterOneAssessment.equals(Result.Assessment.Bye)) {
			return fighters.get(1);
		} else if (this.result.fighterTwoAssessment.equals(Result.Assessment.Bye)) {
			return fighters.get(0);
		}

		// Winner by disqualification
		if (this.result.fighterOneAssessment.equals(Result.Assessment.Disqualification)) {
			return fighters.get(1);
		} else if (this.result.fighterTwoAssessment.equals(Result.Assessment.Disqualification)) {
			return fighters.get(0);
		}

		// Winner by regular win
		if (this.result.fighterOneAssessment.equals(Result.Assessment.Win)) {
			return fighters.get(0);
		} else if (this.result.fighterTwoAssessment.equals(Result.Assessment.Win)) {
			return fighters.get(1);
		}

		return null_winner; // no winner! - calling method has to check for
							// equals(null)
	}

	@JSON(include = false)
	public Fighter getLoser() throws ActivationException {

		// throw exception if not decided!
		if (this.state == Fight.State.Undecided) {
			throw new ActivationException();
		}

		Fighter null_winner = null;

		// regular loss
		if (this.result.fighterOneAssessment.equals(Result.Assessment.Loss)) {
			return fighters.get(0);
		} else if (this.result.fighterTwoAssessment.equals(Result.Assessment.Loss)) {
			return fighters.get(1);
		}

		// disqualification - no loser, continue with bye!
		if (this.result.fighterOneAssessment.equals(Result.Assessment.Disqualification)
				|| this.result.fighterTwoAssessment.equals(Result.Assessment.Disqualification)) {
			return null_winner;
		}

		return null_winner; // no winner! - calling method has to check for
							// equals(null)
	}

	@JSON(include = false)
	public void setBye() {
		if (this.result == null){
			this.result = new Result();
			this.result.save();
		}
		this.result.fighterTwoAssessment = Result.Assessment.Bye;
		this.save();
	}

	/**
	 * Merges a given Fight instance to itself and saves the changes in DB.
	 *
	 * @param {models.Fight} fight The Fight to merge the properties from.
	 * @param {models.Fight} The modified and persisted Fight.
	 */
	public Fight merge(Fight fight) {
		Bracket bracket = fight.getBracket();
		List<Fighter> fighters = fight.getFighters();
		Result result = fight.getResult();
		FightArea fightarea = fight.getFightArea();
		State state = fight.getState();

		if (bracket != null) {
			this.setBracket(bracket);
		}
		if (fighters != null && fighters.size() > 0) {
			this.setFighters(fighters);
		}
		if (result != null) {
			this.setResult(result);
		}
		if (fightarea != null) {
			this.setFightArea(fightarea);
		}
		if (state != null) {
			this.setState(state);
		}

		this.save();
		return this;
	}
}
