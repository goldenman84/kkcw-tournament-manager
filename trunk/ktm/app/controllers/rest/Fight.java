package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Fight extends REST {

	public static void index() {
		List<models.Fight> fights = models.Fight.findAll();
		REST.renderJSON(fights, REST.getDefaultSerializer());
	}
	
	public static void create() {
		String body = params.all().get("body")[0];
		validation.required(body);
		if (validation.hasErrors()) {;
			response.status = 400;
			renderJSON(validation.errors().get(0).message("body content"));
		}
		
		ArrayList<models.Fight> fights = REST.deserialize(body);
		models.Fight fight = fights.get(0);
		fight.save();
		REST.renderJSON(fight, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Fight fight = models.Fight.findById(id);
		notFoundIfNull(fight, "Couldn't find fight (id: "+ id +") in database");
		REST.renderJSON(fight, REST.getDefaultSerializer());
	}
	
	public static void update(Long id) {
		models.Fight originFight = models.Fight.findById(id);
		notFoundIfNull(originFight, "Couldn't find fight (id: " + id + ") in database");

		ArrayList<models.Fight> fights = REST.parseBodyJson(params);
		models.Fight fight = fights.get(0);

		originFight.merge(fight);
		REST.renderJSON(originFight, REST.getDefaultSerializer());
	}
}
