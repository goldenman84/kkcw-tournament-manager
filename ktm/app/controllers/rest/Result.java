package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Result extends REST {
	
	public static void index() {
		List<models.Result> results = models.Result.find.all();
		REST.renderJSON(results, REST.getDefaultSerializer());
	}
	
	public static void create() {
		ArrayList<models.Result> results = REST.parseBodyJson(params);
		models.Result result = results.get(0);
		result.save();
		REST.renderJSON(result, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Result result = models.Result.find.byId(id);
		notFoundIfNull(result, "Couldn't find result (id: "+ id +") in database");
		REST.renderJSON(result, REST.getDefaultSerializer());
	}
	
	public static void update(Long id) {
		models.Result originResult = models.Result.find.byId(id);
		notFoundIfNull(originResult, "Couldn't find result (id: " + id + ") in database");

		ArrayList<models.Result> results = REST.parseBodyJson(params);
		models.Result result = results.get(0);

		originResult.merge(result);
		REST.renderJSON(originResult, REST.getDefaultSerializer());
	}
}
