package controllers.REST;

import java.util.List;
import play.mvc.*;

public class Fighter extends REST {
	
	public static void index() {
		List<models.Fighter> fighters = models.Fighter.findAll();
		REST.renderJSON(fighters, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.Fighter fighter = models.Fighter.findById(id);
		notFoundIfNull(fighter, "Couldn't find fighter (id: "+ id +") in database");
		REST.renderJSON(fighter, REST.getDefaultSerializer());
	}
}
