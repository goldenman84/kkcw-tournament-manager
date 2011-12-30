package controllers.REST;

import java.util.List;

import org.hibernate.mapping.Array;

import com.sun.org.apache.xml.internal.serialize.Serializer;

import flexjson.JSONSerializer;

import models.Bracket;
import models.Category;
import models.Fight;
import models.Fighter;
import models.Result;
import models.Round;
import models.Tournament;

import play.db.jpa.Model;
import play.mvc.*;

public class REST extends Controller {

	public static void index() {
		render();
	}
	
	public static void categories() {
		List<Category> categories = Category.findAll();
		REST.renderJSON(categories, REST.getDefaultSerializer());
	}
	
	public static void category(Long id) {
		Category category = Category.findById(id);
		REST.renderJSON(category, REST.getDefaultSerializer());
	}
	
	public static void category_fighters(Long id) {
		Category category = Category.findById(id);
		if (category != null) {
			List<Fighter> fighters = Fighter.find("category", category).fetch();
			REST.renderJSON(fighters, REST.getDefaultSerializer());
		}
		else {
			REST.renderJSON(new Object(), REST.getDefaultSerializer());
		}
	}
	
	public static void category_rounds(Long id) {
		Category category = Category.findById(id);
		if (category != null) {
			List<Round> rounds = Round.find("category", category).fetch();
			REST.renderJSON(rounds, REST.getDefaultSerializer());
		}
		else {
			REST.renderJSON(new Object(), REST.getDefaultSerializer());
		}
	}
	
	public static void brackets() {
		List<Bracket> brackets = Bracket.findAll();
		REST.renderJSON(brackets, REST.getDefaultSerializer());
	}
	
	public static void rounds() {
		List<Round> rounds = Round.findAll();
		REST.renderJSON(rounds, REST.getDefaultSerializer());
	}
	
	public static void fights() {
		List<Fight> fights = Fight.findAll();
		REST.renderJSON(fights, REST.getDefaultSerializer());
	}
	
	public static void results() {
		List<Result> results = Result.findAll();
		REST.renderJSON(results, REST.getDefaultSerializer());
	}
	
	
	public static void fighters() {
		List<Fighter> fighters = Fighter.findAll();
		REST.renderJSON(fighters, REST.getDefaultSerializer());
	}
	protected static void renderJSON(Object model, JSONSerializer defaultSerializer) {
		if (model == null) {
			model = new Object();
		}
		String json = REST.toJsonString(model);
		renderJSON(json);
	}
	
	protected static void renderJSON(List<?> models, JSONSerializer defaultSerializer) {
		String json = REST.toJsonString(models);
		renderJSON(json);
	}
	
	protected static String toJsonString(Object objects) {
		JSONSerializer modelSerializer = REST.getDefaultSerializer();
		return modelSerializer.serialize(objects);
	}
	
	protected static JSONSerializer getDefaultSerializer() {
		return new JSONSerializer().exclude("*.entityId",
				"*.persistent");
	}

}
