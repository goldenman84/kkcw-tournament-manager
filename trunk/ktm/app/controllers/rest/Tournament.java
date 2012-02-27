package controllers.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tournament extends REST {

	public static void index() {
		List<models.Tournament> tournaments = models.Tournament.findAll();
		REST.renderJSON(tournaments, REST.getDefaultSerializer());
	}

	public static void create() throws Exception {
		String body = params.all().get("body")[0];
		validation.required(body);
		if (validation.hasErrors()) {
			response.status = 400;
			renderJSON(validation.errors().get(0).message("body content"));
		}
		
		ArrayList<models.Tournament> tournaments = REST.deserialize(body);
		models.Tournament tournament = tournaments.get(0);
		tournament.save();
		REST.renderJSON(tournament, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Tournament tournament = models.Tournament.findById(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: " + id + ") in database");
		REST.renderJSON(tournament, REST.getDefaultSerializer());
	}
	
	public static void update(Long id) {
		models.Tournament originTournament = models.Tournament.findById(id);
		notFoundIfNull(originTournament, "Couldn't find tournament (id: " + id + ") in database");
		
		String body = params.all().get("body")[0];
		validation.required(body);
		if (validation.hasErrors()) {;
			response.status = 400;
			renderJSON(validation.errors().get(0).message("body content"));
		}
		
		ArrayList<models.Tournament> tournaments = REST.deserialize(body);
		models.Tournament tournament = tournaments.get(0);
		
		Date dt = tournament.getDate();
		String name = tournament.getName();
		if (dt != null) {
			originTournament.setDate(dt);
		}
		if (name != null) {
			originTournament.setName(name);
		}
		
		originTournament.save();
		REST.renderJSON(originTournament, REST.getDefaultSerializer());
	}
	
	public static void categories(Long id) {
		models.Tournament tournament = models.Tournament.findById(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: " + id + ") in database");

		List<models.Category> categories = models.Category.find("tournament", tournament).fetch();
		REST.renderJSON(categories, REST.getDefaultSerializer());
	}
}
