package controllers.rest;

import java.util.Date;
import java.util.List;

public class Tournament extends REST {

	public static void index() {
		List<models.Tournament> tournaments = models.Tournament.findAll();
		REST.renderJSON(tournaments, REST.getDefaultSerializer());
	}

	public static void create(models.Tournament newTournament) {
		String name = params.get("name");
		String datestr = params.get("date");
		if (name != null) {
			newTournament.setName(name);
		}
		if (datestr != null) {
			Date date = new Date(new Long(datestr));
			newTournament.setDate(date);
		}
		newTournament.save();
		REST.renderJSON(newTournament, REST.getDefaultSerializer());
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
