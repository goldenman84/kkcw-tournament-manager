package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import play.mvc.*;

public class Result extends REST {
	
	public static void index() {
		List<models.Result> results = models.Result.findAll();
		REST.renderJSON(results, REST.getDefaultSerializer());
	}
	
	public static void create() {
		String body = params.all().get("body")[0];
		validation.required(body);
		if (validation.hasErrors()) {;
			response.status = 400;
			renderJSON(validation.errors().get(0).message("body content"));
		}
		
		ArrayList<models.Result> results = REST.deserialize(body);
		models.Result result = results.get(0);
		result.save();
		REST.renderJSON(result, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Result result = models.Result.findById(id);
		notFoundIfNull(result, "Couldn't find result (id: "+ id +") in database");
		REST.renderJSON(result, REST.getDefaultSerializer());
	}
}
