package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import models.Category;

import org.springframework.ui.Model;

import com.avaje.ebean.Ebean;

public class Tournament extends REST {

	public static play.mvc.Result index() {
		List<models.Tournament> tournaments = models.Tournament.find.all();
		return REST.renderJSON(tournaments);
	}

	public static play.mvc.Result create() throws Exception {
		ArrayList<models.Tournament> tournaments = REST.parseBodyJson();
		models.Tournament tournament = tournaments.get(0);
		tournament.save();
		return REST.renderJSON(tournament);
	}

	public static play.mvc.Result show(Long id) {
		models.Tournament tournament = models.Tournament.find.byId(id);

		if (tournament == null) {
			return notFound("Couldn't find tournament (id: " + id + ") in database");
		}
		return REST.renderJSON(tournament);
	}

	public static play.mvc.Result update(Long id) {
		models.Tournament originTournament = models.Tournament.find.byId(id);

		if (originTournament == null) {
			return notFound("Couldn't find tournament (id: " + id + ") in database");
		}

		ArrayList<models.Tournament> tournaments = REST.parseBodyJson();
		models.Tournament tournament = tournaments.get(0);

		originTournament.merge(tournament);
		return REST.renderJSON(originTournament);
	}

	public static play.mvc.Result categories(Long id) {
		models.Tournament tournament = models.Tournament.find.byId(id);

		if (tournament == null) {
			return notFound("Couldn't find tournament (id: " + id + ") in database");
		}

		List<models.Category> categories = models.Category.find.where().eq("tournament", tournament).findList();
		return REST.renderJSON(categories);
	}
}
