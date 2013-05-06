package controllers.rest;

import java.util.ArrayList;
import java.util.List;

public class Bracket extends REST {

	public static play.mvc.Result index() {
		List<models.Bracket> brackets = models.Bracket.find.all();
		return REST.renderJSON(brackets);
	}

	public static play.mvc.Result create() {
		ArrayList<models.Bracket> brackets = REST.parseBodyJson();
		models.Bracket bracket = brackets.get(0);
		bracket.save();
		return REST.renderJSON(bracket);
	}

	public static play.mvc.Result show(Long id) {
		models.Bracket bracket = models.Bracket.find.byId(id);
		if (bracket == null) {
			return notFound("Couldn't find bracket (id: "+ id +") in database");
		}
		return REST.renderJSON(bracket);
	}

	public static play.mvc.Result update(Long id) {
		models.Bracket originBracket = models.Bracket.find.byId(id);

		if (originBracket == null) {
			return notFound("Couldn't find bracket (id: " + id + ") in database");
		}

		ArrayList<models.Bracket> brackets = REST.parseBodyJson();
		models.Bracket bracket = brackets.get(0);

		originBracket.merge(bracket);
		return REST.renderJSON(originBracket);
	}
}
