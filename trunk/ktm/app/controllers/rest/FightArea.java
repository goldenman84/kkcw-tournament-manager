package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class FightArea extends REST {
	
	public static void index() {
		List<models.FightArea> fightareas = models.FightArea.findAll();
		REST.renderJSON(fightareas, REST.getDefaultSerializer());
	}
	
	public static void create() {
		String body = params.all().get("body")[0];
		validation.required(body);
		if (validation.hasErrors()) {;
			response.status = 400;
			renderJSON(validation.errors().get(0).message("body content"));
		}
		
		ArrayList<models.FightArea> fightareas = REST.deserialize(body);
		models.FightArea fightarea = fightareas.get(0);
		fightarea.save();
		REST.renderJSON(fightarea, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.FightArea fightarea = models.FightArea.findById(id);
		notFoundIfNull(fightarea, "Couldn't find fightarea (id: "+ id +") in database");
		REST.renderJSON(fightarea, REST.getDefaultSerializer());
	}
}