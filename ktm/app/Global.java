import java.util.*;

import play.*;
import play.libs.*;

import javax.persistence.*;
import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

  @Override
  public void onStart(Application app) {
    Logger.info("Application has started");

		// Check if the database is empty
        if (Tournament.find.findRowCount() == 0) {
            Ebean.save((List) Yaml.load("initial-data.yml"));
        }
  }  
  
  @Override
  public void onStop(Application app) {
    Logger.info("Application shutdown...");
  }  
}
