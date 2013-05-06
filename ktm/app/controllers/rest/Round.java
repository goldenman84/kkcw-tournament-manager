package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.Bracket;
import models.Category;

public class Round extends REST {

	public static play.mvc.Result index() {
		List<models.Round> rounds = models.Round.find.all();
		return REST.renderJSON(rounds);
	}

	public static play.mvc.Result create() throws Exception {
		ArrayList<models.Round> rounds = REST.parseBodyJson();
		models.Round round = rounds.get(0);
		round.save();
		return REST.renderJSON(round);
	}

	public static play.mvc.Result show(Long id) {
		models.Round round = models.Round.find.byId(id);

		if (round == null) {
			return notFound("Couldn't find round (id: "+ id +") in database");
		}
		return REST.renderJSON(round);
	}

	public static play.mvc.Result update(Long id) {
		models.Round originRound = models.Round.find.byId(id);

		if (originRound == null) {
			return notFound("Couldn't find round (id: " + id + ") in database");
		}

		ArrayList<models.Round> rounds = REST.parseBodyJson();
		models.Round round = rounds.get(0);

		originRound.merge(round);
		return REST.renderJSON(originRound);
	}

	public static play.mvc.Result brackets(Long id) {
		models.Round round = models.Round.find.byId(id);

		if (round == null) {
			return notFound("Couldn't find round (id: "+ id +") in database");
		}

		List<Bracket> brackets = models.Bracket.find.where().eq("round", round).findList();
		return REST.renderJSON(brackets);
	}
}
