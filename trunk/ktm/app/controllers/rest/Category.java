package controllers.rest;

import java.util.ArrayList;
import java.util.Iterator;
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
	
	public static void create() throws Exception {
		String body = params.all().get("body")[0];
		validation.required(body);
		if (validation.hasErrors()) {;
			response.status = 400;
			renderJSON(validation.errors().get(0).message("body content"));
		}
		
		ArrayList<models.Category> categories = REST.deserialize(body);
		models.Category category = categories.get(0);
		notFoundIfNull(category, "Couldn't find a valid category data in request body");
		category.save();
		
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
	
	public static void fightareas(Long id) {
		models.Category category = models.Category.findById(id);
		notFoundIfNull(category, "Couldn't find category (id: "+ id +") in database");
		
		List<models.FightArea> fightareas = category.fightareas;
		REST.renderJSON(fightareas, REST.getDefaultSerializer());
	}
}
