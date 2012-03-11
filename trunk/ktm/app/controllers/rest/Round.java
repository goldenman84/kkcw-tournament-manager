package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import models.Bracket;

public class Round extends REST {

	public static void index() {
		List<models.Round> rounds = models.Round.findAll();
		REST.renderJSON(rounds, REST.getDefaultSerializer());
	}
	
	public static void create() throws Exception {
		ArrayList<models.Round> rounds = REST.parseBodyJson(params);
		models.Round round = rounds.get(0);
		round.save();
		REST.renderJSON(round, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Round round = models.Round.findById(id);
		notFoundIfNull(round, "Couldn't find round (id: "+ id +") in database");
		REST.renderJSON(round, REST.getDefaultSerializer());
	}
	
	public static void update(Long id) {
		models.Round originRound = models.Round.findById(id);
		notFoundIfNull(originRound, "Couldn't find round (id: " + id + ") in database");
		
		ArrayList<models.Round> rounds = REST.parseBodyJson(params);
		models.Round round = rounds.get(0);
		
		originRound.merge(round);
		REST.renderJSON(originRound, REST.getDefaultSerializer());
	}
	
	public static void brackets(Long id) {
		models.Round round = models.Round.findById(id);
		notFoundIfNull(round, "Couldn't find round (id: "+ id +") in database");
		
		List<Bracket> brackets = Bracket.find("byRound", round).fetch();
		REST.renderJSON(brackets, REST.getDefaultSerializer());
	}
}
