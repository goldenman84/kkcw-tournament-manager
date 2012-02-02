package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Tournament extends REST {

	public static void index() {
		List<models.Tournament> tournaments = models.Tournament.findAll();
		REST.renderJSON(tournaments, REST.getDefaultSerializer());
	}

	public static void create() throws Exception {
		String[] body = params.all().get("body");
		validation.isTrue("request body content is empty", body.length >= 0);
		if (validation.hasErrors()) {
		 response.status = 400;
		 REST.renderJSON(validation.errors(), REST.getDefaultSerializer());
		}
		
		ArrayList<models.Tournament> tournaments = REST.deserialize(body[0]);
		models.Tournament tournament = tournaments.get(0);
		tournament.save();
		REST.renderJSON(tournament, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Tournament tournament = models.Tournament.findById(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: " + id + ") in database");
		REST.renderJSON(tournament, REST.getDefaultSerializer());
	}

	public static void categories(Long id) {
		models.Tournament tournament = models.Tournament.findById(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: " + id + ") in database");

		List<models.Category> categories = models.Category.find("tournament", tournament).fetch();
		REST.renderJSON(categories, REST.getDefaultSerializer());
	}
}
