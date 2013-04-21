package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Fighter extends REST {

	public static play.mvc.Result index() {
		List<models.Fighter> fighters = models.Fighter.find.all();
		return REST.renderJSON(fighters);
	}

	public static play.mvc.Result create() {
		ArrayList<models.Fighter> fighters = REST.parseBodyJson();
		models.Fighter fighter = fighters.get(0);
		fighter.save();
		return REST.renderJSON(fighter);
	}

	public static play.mvc.Result show(Long id) {
		models.Fighter fighter = models.Fighter.find.byId(id);

		if (fighter == null) {
			return notFound("Couldn't find fighter (id: " + id + ") in database");
		}
		return REST.renderJSON(fighter);
	}

	public static play.mvc.Result update(Long id) {
		models.Fighter originFighter = models.Fighter.find.byId(id);

		if (originFighter == null) {
			return notFound("Couldn't find fighter (id: " + id + ") in database");
		}

		ArrayList<models.Fighter> fighters = REST.parseBodyJson();
		models.Fighter fighter = fighters.get(0);

		originFighter.merge(fighter);
		return REST.renderJSON(originFighter);
	}
}
