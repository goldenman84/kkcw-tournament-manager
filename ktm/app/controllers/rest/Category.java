package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import models.Bracket;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

public class Category extends REST {

	public static play.mvc.Result index() {
		List<models.Category> categories = models.Category.find.all();
		return REST.renderJSON(categories);
	}

	public static play.mvc.Result create() {
		ArrayList<models.Category> categories = REST.parseBodyJson();
		models.Category category = categories.get(0);
		category.save();
		return REST.renderJSON(category);
	}

	public static play.mvc.Result show(Long id) {
		models.Category category = models.Category.find.byId(id);
		if (category == null) {
			return notFound("Couldn't find category (id: "+ id +") in database");
		}
		return REST.renderJSON(category);
	}

	public static play.mvc.Result update(Long id) {
		models.Category originCategory = models.Category.find.byId(id);
		if (originCategory == null) {
			return notFound("Couldn't find category (id: "+ id +") in database");
		}

		ArrayList<models.Category> categories = REST.parseBodyJson();
		models.Category category = categories.get(0);

		originCategory.merge(category);
		return REST.renderJSON(originCategory);
	}

	public static play.mvc.Result fighters(Long id) {
		models.Category category = models.Category.find.byId(id);
		if (category == null) {
			return notFound("Couldn't find category (id: "+ id +") in database");
		}
		return REST.renderJSON(category.fighters);
	}

	public static play.mvc.Result rounds(Long id) {
		models.Category category = models.Category.find.byId(id);
		if (category == null) {
			return notFound("Couldn't find category (id: "+ id +") in database");
		}

		List<models.Round> rounds = models.Round.find.where().eq("category", category).findList();
		return REST.renderJSON(rounds);
	}

	public static play.mvc.Result fightareas(Long id) {
		models.Category category = models.Category.find.where().idEq(id).findUnique();
		if (category == null) {
			return notFound("Couldn't find category (id: "+ id +") in database");
		}

		List<models.FightArea> fightareas = category.fightareas;

		return REST.renderJSON(fightareas);
	}
}
