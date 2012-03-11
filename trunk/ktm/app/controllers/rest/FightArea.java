package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class FightArea extends REST {
	
	public static void index() {
		List<models.FightArea> fightareas = models.FightArea.findAll();
		REST.renderJSON(fightareas, REST.getDefaultSerializer());
	}
	
	public static void create() {
		ArrayList<models.FightArea> fightareas = REST.parseBodyJson(params);
		models.FightArea fightarea = fightareas.get(0);
		fightarea.save();
		REST.renderJSON(fightarea, REST.getDefaultSerializer());
	}
	
	public static void show(Long id) {
		models.FightArea fightarea = models.FightArea.findById(id);
		notFoundIfNull(fightarea, "Couldn't find fightarea (id: "+ id +") in database");
		REST.renderJSON(fightarea, REST.getDefaultSerializer());
	}
	
	public static void update(Long id) {
		models.FightArea originFightArea = models.FightArea.findById(id);
		notFoundIfNull(originFightArea, "Couldn't find fightarea (id: " + id + ") in database");

		ArrayList<models.FightArea> fightareas = REST.parseBodyJson(params);
		models.FightArea fightarea = fightareas.get(0);

		originFightArea.merge(fightarea);
		REST.renderJSON(originFightArea, REST.getDefaultSerializer());
	}
}
