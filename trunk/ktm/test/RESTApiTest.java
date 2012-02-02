import java.sql.Date;
import java.util.ArrayList;

import models.Fight;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

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
	public void testGetTournament() {
		Response response = GET("/api/tournaments");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.Tournament> tournaments = controllers.rest.REST.deserialize(content);
		models.Tournament tournament = tournaments.get(0);
		assertNotNull(tournament);
		assertTrue(tournament.getId() instanceof Long);
		assertEquals("WintiCup Test", tournament.getName());
		Long dateValue = new java.lang.Long("1334361600000");
		assertEquals(new Date(dateValue), tournament.getDate());
	}

	@Test
	public void testGetCategories() {
		Response response = GET("/api/categories");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.Category> categories = controllers.rest.REST.deserialize(content);
		assertEquals(models.Category.findAll().size(), categories.size());
		models.Category category = categories.get(0);
		assertNotNull(category);
		assertTrue(category.getId() instanceof Long);
	}

	@Test
	public void testGetBrackets() {
		Response response = GET("/api/brackets");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.Bracket> brackets = controllers.rest.REST.deserialize(content);
		assertEquals(models.Bracket.findAll().size(), brackets.size());
		models.Bracket bracket = brackets.get(0);
		assertNotNull(bracket);
		assertTrue(bracket.getId() instanceof Long);
		assertTrue(bracket.name instanceof String);
	}

	@Test
	public void testGetRounds() {
		Response response = GET("/api/rounds");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.Round> rounds = controllers.rest.REST.deserialize(content);
		assertEquals(models.Round.findAll().size(), rounds.size());
		models.Round round = rounds.get(0);
		assertNotNull(round);
		assertTrue(round.getId() instanceof Long);
	}

	@Test
	public void testGetFighters() {
		Response response = GET("/api/fighters");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.Fighter> fighters = controllers.rest.REST.deserialize(content);
		assertEquals(models.Fighter.findAll().size(), fighters.size());
		models.Fighter fighter = fighters.get(0);
		assertNotNull(fighter);
		assertTrue(fighter.getId() instanceof Long);
		assertTrue(fighter.age >= 0);
		assertTrue(fighter.size >= 0);
		assertTrue(fighter.firstname instanceof String);
		assertTrue(fighter.lastname instanceof String);
	}

	@Test
	public void testGetFights() {
		Response response = GET("/api/fights");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.Fight> fights = controllers.rest.REST.deserialize(content);
		assertEquals(models.Fight.findAll().size(), fights.size());
		models.Fight fight = fights.get(0);
		assertNotNull(fight);
		assertTrue(fight.getId() instanceof Long);
		assertTrue(fight.state instanceof Fight.State);
	}

	@Test
	public void testGetResults() {
		Response response = GET("/api/results");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.Result> results = controllers.rest.REST.deserialize(content);
		assertEquals(models.Result.findAll().size(), results.size());
		models.Result result = results.get(0);
		assertNotNull(result);
		assertTrue(result.getId() instanceof Long);
		assertTrue(result.fighterOneCondition instanceof models.Result.Condition);
		assertTrue(result.fighterTwoCondition instanceof models.Result.Condition);
		assertTrue(result.fighterOneAssessment instanceof models.Result.Assessment);
		assertTrue(result.fighterTwoAssessment instanceof models.Result.Assessment);
	}

	@Test
	public void testPostTournament() {
		String body = "[{\"class\":\"models.Tournament\"," +
		              "\"date\":1334361600000,\"name\":\"Test\"}]";
		Response response = POST("/api/tournaments", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();
		
		ArrayList<models.Tournament> tournaments  = controllers.rest.REST.deserialize(content);
		models.Tournament tournament = tournaments.get(0);
		assertNotNull(tournament);
		assertTrue(tournament.getId() instanceof Long);
		assertEquals("Test", tournament.getName());
		Long dateValue = new java.lang.Long("1334361600000");
		assertEquals(new Date(dateValue), tournament.getDate());
	}
}
