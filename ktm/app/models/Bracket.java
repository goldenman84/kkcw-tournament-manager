package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import com.avaje.ebean.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class Bracket extends Model {

	@Id
	public Long id;

	@Constraints.Required
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

	// ebean finder class
	public static Finder<Long,Bracket> find = new Finder<Long,Bracket>(
		Long.class, Bracket.class
	);

	public String getName() {
		return this.name;
	}

	public String setName(String name) {
		return (this.name = name);
	}

	public Round getRound() {
		return this.round;
	}

	public Round setRound(Round round) {
		return (this.round = round);
	}

	public List<Fight> getFights() {
		return this.fights;
	}

	// add one empty fight
	public Fight addFight() {
		Fight newFight = new Fight(this);
		newFight.save();
		this.fights.add(newFight);
		this.save();
		return this.fights.get(this.fights.size() - 1);
	}

	// add multiple empty fights
	public Bracket addFights(int numFights) {
		for (int i = 0; i < numFights; i++) {
			Fight newFight = new Fight(this);
			newFight.save();
			this.fights.add(newFight);
		}
		this.save();
		return this;
	}

	public void clearResults() {
		for (Fight fi : fights) {
			fi.clearResults();
		}
	}

	/**
	 * Merges a given Bracket instance to itself and saves the changes in DB.
	 *
	 * @param {models.Bracket} bracket The Bracket to merge the properties from.
	 * @param {models.Bracket} The modified and persisted Bracket.
	 */
	public Bracket merge(Bracket bracket) {
		String name = bracket.getName();
		Round round = bracket.getRound();

		if (name != null) {
			this.setName(name);
		}
		if (round != null) {
			this.round = round;
		}

		this.save();
		return this;
	}
}
