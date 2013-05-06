package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Fight extends REST {

	public static play.mvc.Result index() {
		List<models.Fight> fights = models.Fight.find.all();
		return REST.renderJSON(fights);
	}

	public static play.mvc.Result create() {
		ArrayList<models.Fight> fights = REST.parseBodyJson();
		models.Fight fight = fights.get(0);
		fight.save();
		return REST.renderJSON(fight);
	}

	public static play.mvc.Result show(Long id) {
		models.Fight fight = models.Fight.find.byId(id);
		if (fight == null) {
			return notFound("Couldn't find fight (id: "+ id +") in database");
		}
		return REST.renderJSON(fight);
	}

	public static play.mvc.Result update(Long id) {
		models.Fight originFight = models.Fight.find.byId(id);
		if (originFight == null) {
			return notFound("Couldn't find fight (id: "+ id +") in database");
		}

		ArrayList<models.Fight> fights = REST.parseBodyJson();
		models.Fight fight = fights.get(0);

		originFight.merge(fight);
		return REST.renderJSON(originFight);
	}
}
