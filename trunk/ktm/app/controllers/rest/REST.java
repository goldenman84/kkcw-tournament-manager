package controllers.rest;

import java.util.List;

import org.hibernate.mapping.Array;

import com.sun.org.apache.xml.internal.serialize.Serializer;

import flexjson.JSONSerializer;
import flexjson.transformer.EnumTransformer;

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
