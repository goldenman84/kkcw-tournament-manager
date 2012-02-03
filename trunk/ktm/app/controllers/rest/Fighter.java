package controllers.rest;

import java.util.ArrayList;
import java.util.List;
import play.mvc.*;

public class Fighter extends REST {
	
	public static void index() {
		List<models.Fighter> fighters = models.Fighter.findAll();
		REST.renderJSON(fighters, REST.getDefaultSerializer());
	}
	
	public static void create() {
		String body = params.all().get("body")[0];
		validation.required(body);
		if (validation.hasErrors()) {;
			response.status = 400;
			renderJSON(validation.errors().get(0).message("body content"));
		}
		
		ArrayList<models.Fighter> fighters = REST.deserialize(body);
		models.Fighter fighter = fighters.get(0);
		fighter.save();
		REST.renderJSON(fighter, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Fighter fighter = models.Fighter.findById(id);
		notFoundIfNull(fighter, "Couldn't find fighter (id: "+ id +") in database");
		REST.renderJSON(fighter, REST.getDefaultSerializer());
	}
}
