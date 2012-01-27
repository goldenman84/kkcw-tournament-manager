import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.log4j.lf5.util.DateFormatManager;
import org.junit.*;
import org.junit.After;
import org.junit.Before;

import com.google.gson.JsonSerializer;

import controllers.rest.REST;
import controllers.rest.factories.DateFactory;
import controllers.rest.factories.NumberFactory;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import play.test.*;
import play.classloading.ApplicationClasses.ApplicationClass;
import play.classloading.ApplicationClassloader;
import play.db.jpa.Model;
import play.mvc.*;
import play.mvc.Http.*;
import models.Tournament;

public class RESTApiTest extends FunctionalTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");
	}

	@After
	public void teardown() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void testGetTournaments() {
		Response response = GET("/api/tournaments");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();
		System.out.println("GET: " + content);

		Tournament tournament = controllers.rest.Tournament.deserialize(content);
		assertNotNull(tournament);
		assertTrue(tournament.getId() instanceof Long);
		assertEquals("WintiCup Test", tournament.getName());
		Long dateValue = new java.lang.Long("1334361600000");
		assertEquals(new Date(dateValue) , tournament.getDate());
		
	}
}
