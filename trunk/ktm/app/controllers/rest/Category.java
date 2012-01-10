package controllers.rest;

import java.util.List;
import play.mvc.*;

public class Category extends REST {

	public static void index() {
		List<models.Category> categories = models.Category.findAll();
		REST.renderJSON(categories, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Category category = models.Category.findById(id);
		notFoundIfNull(category, "Couldn't find category (id: "+ id +") in database");
		REST.renderJSON(category, REST.getDefaultSerializer());
	}

	public static void fighters(Long id) {
		models.Category category = models.Category.findById(id);
		notFoundIfNull(category, "Couldn't find category (id: "+ id +") in database");
		REST.renderJSON(category.fighters, REST.getDefaultSerializer());
	}

	public static void rounds(Long id) {
		models.Category category = models.Category.findById(id);
		notFoundIfNull(category, "Couldn't find category (id: "+ id +") in database");
		
		List<models.Round> rounds = models.Round.find("category", category).fetch();
		REST.renderJSON(rounds, REST.getDefaultSerializer());
	}
	
	public static void fight_areas(Long id) {
		// TODO: implement this method!
		REST.renderJSON(new Object(), REST.getDefaultSerializer());
	}
}
