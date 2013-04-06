package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Bracket extends REST {
	
	public static void index() {
		List<models.Bracket> brackets = models.Bracket.findAll();
		REST.renderJSON(brackets, REST.getDefaultSerializer());
	}
	
	public static void create() {
		ArrayList<models.Bracket> brackets = REST.parseBodyJson(params);
		models.Bracket bracket = brackets.get(0);
		bracket.save();
		REST.renderJSON(bracket, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Bracket bracket = models.Bracket.findById(id);
		notFoundIfNull(bracket, "Couldn't find bracket (id: "+ id +") in database");
		REST.renderJSON(bracket, REST.getDefaultSerializer());
	}
	
	public static void update(Long id) {
		models.Bracket originBracket = models.Bracket.findById(id);
		notFoundIfNull(originBracket, "Couldn't find bracket (id: " + id + ") in database");
		
		ArrayList<models.Bracket> brackets = REST.parseBodyJson(params);
		models.Bracket bracket = brackets.get(0);
		
		originBracket.merge(bracket);
		REST.renderJSON(originBracket, REST.getDefaultSerializer());
	}
}
