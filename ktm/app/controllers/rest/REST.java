package controllers.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.mvc.Controller;
import controllers.rest.factories.DateFactory;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class REST extends Controller {

	public static play.mvc.Result index() {
		response().setContentType("text/html");

		return ok("<h1>API Documentation</h1>");
	}

	protected static Status renderJSON(Object model) {
		if (model == null) {
			model = new Object();
		}

		String json = REST.toJsonString(model);
		return ok(json, "utf-8").as("application/json");
	}

	protected static play.mvc.Result renderJSON(List<?> models, JSONSerializer defaultSerializer) {
		String json = REST.toJsonString(models);
		return renderJSON(json);
	}

	public static String toJsonString(Object objects) {
		JSONSerializer modelSerializer = REST.getDefaultSerializer();
		return modelSerializer.serialize(objects);
	}

	public static JSONSerializer getDefaultSerializer() {
		return new JSONSerializer().exclude("*.entityId", "*.persistent");
	}

	public static <T> ArrayList<T> deserialize(String content) {
		ArrayList<T> list = (ArrayList<T>) new JSONDeserializer().use(Date.class, new DateFactory())
				.deserialize(content);
		return list;
	}

	/**
	 * Parses the JSON string HTML response body and converts this JSON data to
	 * valid play.db.jpa.Model instances.
	 * 
	 * @param {play.mvc.Scope.Params} params The response parameters received by
	 *        the controller instance.
	 * @return {ArrayList} An ArrayList with model instances, converted from JSON
	 *         data.
	 */
	public static <T> ArrayList<T> parseBodyJson() {
		String body = request().body().asText();

		if (body == "") {
			throw new Error("Invalid request body recieved");
		}

		ArrayList<T> models = REST.deserialize(body);
		return models;
	}
}
