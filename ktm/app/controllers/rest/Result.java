package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

public class Result extends REST {

	public static play.mvc.Result index() {
		List<models.Result> results = Ebean.find(models.Result.class).where().findList();

		return REST.renderJSON(results);
	}

	public static play.mvc.Result create() {
		ArrayList<models.Result> results = REST.parseBodyJson();
		models.Result result = results.get(0);
		result.save();
		return REST.renderJSON(result);
	}

	public static play.mvc.Result show(Long id) {
		Result result =  (Result) Ebean.find(models.Result.class).where().idEq(id);

		if(result == null) {
			return notFound("Couldn't find result (id: "+ id +") in database");
		}
		return REST.renderJSON(result);
	}

	public static play.mvc.Result update(Long id) {
		models.Result originResult = models.Result.find.where().idEq(id).findUnique();

		if (originResult == null) {
			return notFound("Couldn't find result (id: " + id + ") in database");
		}

		ArrayList<models.Result> results = REST.parseBodyJson();
		models.Result result = results.get(0);

		originResult.merge(result);
		return REST.renderJSON(originResult);
	}
}
