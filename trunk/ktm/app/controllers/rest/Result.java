package controllers.rest;

import java.util.List;

import play.mvc.*;

public class Result extends REST {
	
	public static void index() {
		List<models.Result> results = models.Result.findAll();
		REST.renderJSON(results, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Result result = models.Result.findById(id);
		notFoundIfNull(result, "Couldn't find result (id: "+ id +") in database");
		REST.renderJSON(result, REST.getDefaultSerializer());
	}
}
