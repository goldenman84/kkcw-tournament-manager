package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Category extends REST {

	public static void index() {
		List<models.Category> categories = models.Category.findAll();
		REST.renderJSON(categories, REST.getDefaultSerializer());
	}
	
	public static void create() {
		ArrayList<models.Category> categories = REST.parseBodyJson(params);
		models.Category category = categories.get(0);
		category.save();
		REST.renderJSON(category, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Category category = models.Category.findById(id);
		notFoundIfNull(category, "Couldn't find category (id: "+ id +") in database");
		REST.renderJSON(category, REST.getDefaultSerializer());
	}
	
	public static void update(Long id) {
		models.Category originCategory = models.Category.findById(id);
		notFoundIfNull(originCategory, "Couldn't find category (id: " + id + ") in database");
		
		ArrayList<models.Category> categories = REST.parseBodyJson(params);
		models.Category category = categories.get(0);
		
		originCategory.merge(category);
		REST.renderJSON(originCategory, REST.getDefaultSerializer());
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
