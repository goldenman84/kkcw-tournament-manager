package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Fighter extends REST {

	public static void index() {
		List<models.Fighter> fighters = models.Fighter.find.all();
		REST.renderJSON(fighters, REST.getDefaultSerializer());
	}

	public static void create() {
		ArrayList<models.Fighter> fighters = REST.parseBodyJson(params);
		models.Fighter fighter = fighters.get(0);
		fighter.save();
		REST.renderJSON(fighter, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Fighter fighter = models.Fighter.find.byId(id);
		notFoundIfNull(fighter, "Couldn't find fighter (id: " + id + ") in database");
		REST.renderJSON(fighter, REST.getDefaultSerializer());
	}

	public static void update(Long id) {
		models.Fighter originFighter = models.Fighter.find.byId(id);
		notFoundIfNull(originFighter, "Couldn't find fighter (id: " + id + ") in database");

		ArrayList<models.Fighter> fighters = REST.parseBodyJson(params);
		models.Fighter fighter = fighters.get(0);

		originFighter.merge(fighter);
		REST.renderJSON(originFighter, REST.getDefaultSerializer());
	}
}
