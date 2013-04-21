import java.util.List;
import java.util.Map;

import models.Tournament;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Application has started");

		// Check if the database is empty => no tournaments defined
		if (Tournament.find.findRowCount() == 0) {
			Logger.info("Database tables will be created using fixtures data...");

			Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");

			Ebean.save(all.get("tournaments"));
			Ebean.save(all.get("fighters"));
			Ebean.save(all.get("fightareas"));
			Ebean.save(all.get("categories"));
			Ebean.save(all.get("rounds"));
			Ebean.save(all.get("brackets"));
			Ebean.save(all.get("results"));
			Ebean.save(all.get("fights"));

			Logger.info("All tables have been successfully created.");
		}
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}
}
