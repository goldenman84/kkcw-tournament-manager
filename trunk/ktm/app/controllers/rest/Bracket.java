package controllers.rest;

import java.util.List;
import play.mvc.*;

public class Bracket extends REST {
	
	public static void index() {
		List<models.Bracket> brackets = models.Bracket.findAll();
		REST.renderJSON(brackets, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Bracket bracket = models.Bracket.findById(id);
		notFoundIfNull(bracket, "Couldn't find bracket (id: "+ id +") in database");
		REST.renderJSON(bracket, REST.getDefaultSerializer());
	}
}
