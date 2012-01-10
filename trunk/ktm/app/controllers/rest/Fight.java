package controllers.rest;

import java.util.List;
import play.mvc.*;

public class Fight extends REST {

	public static void index() {
		List<models.Fight> fights = models.Fight.findAll();
		REST.renderJSON(fights, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Fight fight = models.Fight.findById(id);
		notFoundIfNull(fight, "Couldn't find fight (id: "+ id +") in database");
		REST.renderJSON(fight, REST.getDefaultSerializer());
	}
}
