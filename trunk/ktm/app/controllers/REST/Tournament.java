package controllers.REST;

import java.util.List;

import models.Category;

import play.db.jpa.*;
import play.mvc.*;

public class Tournament extends REST {

	public static void index() {
		List<models.Tournament> tournaments = models.Tournament.findAll();
		REST.renderJSON(tournaments, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Tournament tournament = models.Tournament.findById(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: "+ id +") in database");
		REST.renderJSON(tournament, REST.getDefaultSerializer());
	}

	public static void categories(Long id) {
		models.Tournament tournament = models.Tournament.findById(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: "+ id +") in database");
		
		List<models.Category> categories = models.Category.find("tournament", tournament).fetch();
		REST.renderJSON(categories, REST.getDefaultSerializer());
	}
}
