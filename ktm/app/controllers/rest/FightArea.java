package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class FightArea extends REST {

	public static play.mvc.Result index() {
		List<models.FightArea> fightareas = models.FightArea.find.all();
		return REST.renderJSON(fightareas);
	}

	public static play.mvc.Result create() {
		ArrayList<models.FightArea> fightareas = REST.parseBodyJson();
		models.FightArea fightarea = fightareas.get(0);
		fightarea.save();
		return REST.renderJSON(fightarea);
	}

	public static play.mvc.Result show(Long id) {
		models.FightArea fightarea = models.FightArea.find.byId(id);

		if (fightarea == null) {
			return notFound("Couldn't find fightarea (id: "+ id +") in database");
		}
		return REST.renderJSON(fightarea);
	}

	public static play.mvc.Result update(Long id) {
		models.FightArea originFightArea = models.FightArea.find.byId(id);
		if (originFightArea == null) {
			return notFound("Couldn't find fightarea (id: "+ id +") in database");
		}

		ArrayList<models.FightArea> fightareas = REST.parseBodyJson();
		models.FightArea fightarea = fightareas.get(0);

		originFightArea.merge(fightarea);
		return REST.renderJSON(originFightArea);
	}
}
