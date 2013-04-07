package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Tournament extends REST {

	public static void index() {
		List<models.Tournament> tournaments = models.Tournament.find.all();
		REST.renderJSON(tournaments, REST.getDefaultSerializer());
	}

	public static void create() throws Exception {
		ArrayList<models.Tournament> tournaments = REST.parseBodyJson(params);
		models.Tournament tournament = tournaments.get(0);
		tournament.save();
		REST.renderJSON(tournament, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Tournament tournament = models.Tournament.find.byId(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: " + id + ") in database");
		REST.renderJSON(tournament, REST.getDefaultSerializer());
	}
	
	public static void update(Long id) {
		models.Tournament originTournament = models.Tournament.find.byId(id);
		notFoundIfNull(originTournament, "Couldn't find tournament (id: " + id + ") in database");
		
		ArrayList<models.Tournament> tournaments = REST.parseBodyJson(params);
		models.Tournament tournament = tournaments.get(0);
		
		originTournament.merge(tournament);
		REST.renderJSON(originTournament, REST.getDefaultSerializer());
	}
	
	public static void categories(Long id) {
		models.Tournament tournament = models.Tournament.find.byId(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: " + id + ") in database");

		List<models.Category> categories = models.Category.find("tournament", tournament).fetch();
		REST.renderJSON(categories, REST.getDefaultSerializer());
	}
}
