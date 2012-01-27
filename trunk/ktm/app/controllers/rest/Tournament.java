package controllers.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import controllers.rest.factories.DateFactory;
import flexjson.JSONDeserializer;

public class Tournament extends REST {

	public static void index() {
		List<models.Tournament> tournaments = models.Tournament.findAll();
		REST.renderJSON(tournaments, REST.getDefaultSerializer());
	}
	
	public static void create(models.Tournament newTournament) {
		newTournament.save();
		REST.renderJSON(newTournament, REST.getDefaultSerializer());
	}

	public static void show(Long id) {
		models.Tournament tournament = models.Tournament.findById(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: "+ id +") in database");
		REST.renderJSON(tournament, REST.getDefaultSerializer());
	}

	public static void categories(Long id) {
		models.Tournament tournament = models.Tournament.findById(id);
		notFoundIfNull(tournament, "Couldn't find tournament (id: "+ id +") in database");
		
		List<models.Category> categories = models.Category.find("tournament", tournament).fetch();
		REST.renderJSON(categories, REST.getDefaultSerializer());
	}
	
	public static models.Tournament deserialize(String content) {
		ArrayList<HashMap<String, Object>> tournaments = (ArrayList<HashMap<String, Object>>) new JSONDeserializer()
				.use(Date.class, new DateFactory()).deserialize(content);

		HashMap<String, Object> tournament = tournaments.get(0);
		
		Long id =  Long.valueOf((Integer) tournament.get("id"));
		String name = (String) tournament.get("name");
		Date date =  new Date((Long) tournament.get("date"));
		return new models.Tournament(id, name, date);
	}
}
