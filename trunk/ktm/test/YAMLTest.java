import org.hibernate.dialect.FirebirdDialect;
import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;


public class YAMLTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}
	
	@Test
	public void loadYAMLData() {
		Fixtures.loadModels("data.yml");
	}
	
}
