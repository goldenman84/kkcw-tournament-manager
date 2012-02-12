package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Bracket extends REST {
	
	public static void index() {
		List<models.Bracket> brackets = models.Bracket.findAll();
		REST.renderJSON(brackets, REST.getDefaultSerializer());
	}
	
	public static void create() {
		String body = params.all().get("body")[0];
		validation.required(body);
		if (validation.hasErrors()) {;
			response.status = 400;
			renderJSON(validation.errors().get(0).message("body content"));
		}
		
		ArrayList<models.Bracket> brackets = REST.deserialize(body);
		models.Bracket bracket = brackets.get(0);
		notFoundIfNull(bracket, "Couldn't find a valid bracket data in request body");
		bracket.save();
		
		REST.renderJSON(bracket, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Bracket bracket = models.Bracket.findById(id);
		notFoundIfNull(bracket, "Couldn't find bracket (id: "+ id +") in database");
		REST.renderJSON(bracket, REST.getDefaultSerializer());
	}
}
