package controllers.rest;

import java.util.List;

import models.Bracket;
import play.mvc.*;

public class Round extends REST {

	public static void index() {
		List<models.Round> rounds = models.Round.findAll();
		REST.renderJSON(rounds, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Round round = models.Round.findById(id);
		notFoundIfNull(round, "Couldn't find round (id: "+ id +") in database");
		REST.renderJSON(round, REST.getDefaultSerializer());
	}
	
	public static void brackets(Long id) {
		models.Round round = models.Round.findById(id);
		notFoundIfNull(round, "Couldn't find round (id: "+ id +") in database");
		
		List<Bracket> brackets = Bracket.find("byRound", round).fetch();
		REST.renderJSON(brackets, REST.getDefaultSerializer());
	}
}
