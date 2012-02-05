package controllers.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.mvc.Controller;
import controllers.rest.factories.DateFactory;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class REST extends Controller {

	public static void index() {
		render();
	}
		
	protected static void renderJSON(Object model, JSONSerializer defaultSerializer) {
		if (model == null) {
			model = new Object();
		}
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(model);
		String json = REST.toJsonString(list);
		renderJSON(json);
	}
	
	protected static void renderJSON(List<?> models, JSONSerializer defaultSerializer) {
		String json = REST.toJsonString(models);
		renderJSON(json);
	}
	
	public static String toJsonString(Object objects) {
		JSONSerializer modelSerializer = REST.getDefaultSerializer();
		return modelSerializer.serialize(objects);
	}
	
	public static JSONSerializer getDefaultSerializer() {
		return new JSONSerializer().exclude("*.entityId",
				"*.persistent");
	}
	
	public static <T> ArrayList<T> deserialize(String content) {
		ArrayList<T> list = (ArrayList<T>) new JSONDeserializer()
				.use(Date.class, new DateFactory()).deserialize(content);
		return list;
	}

}
