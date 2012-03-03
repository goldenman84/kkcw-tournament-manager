import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import models.Category;
import models.Fight;
import models.Result;
import models.Round;
import models.Tournament;

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
	public void testGetFightAreas() {
		Response response = GET("/api/fightareas");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.FightArea> fightareas = controllers.rest.REST.deserialize(content);
		assertEquals(models.FightArea.findAll().size(), fightareas.size());
		models.FightArea fightarea = fightareas.get(0);
		assertNotNull(fightarea);
		assertTrue(fightarea.getId() instanceof Long);
		assertTrue(fightarea.name instanceof String);
	}

	@Test
	public void testGetFightAreasFromCategory() {
		// find a category which has some fight areas assigned
		List<models.Category> categories = models.Category.findAll();
		models.Category  category = null;
		for (models.Category cat : categories) {
			if (cat.fightareas.size() > 0) {
				category = cat;
				break;
			}
		}
		assertTrue(category.fightareas.size() > 0);
		
		Response response = GET("/api/categories/"+ category.getId() +"/fightareas");
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();

		ArrayList<models.FightArea> fightareas = controllers.rest.REST.deserialize(content);
		assertEquals(models.FightArea.findAll().size(), fightareas.size());
		models.FightArea fightarea = fightareas.get(0);
		assertNotNull(fightarea);
		assertTrue(fightarea.getId() instanceof Long);
		assertTrue(fightarea.name instanceof String);
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
		int numOfTournaments = models.Tournament.findAll().size();
		
		String body = "[{\"class\":\"models.Tournament\"," +
		              "\"date\":1334361600000,\"name\":\"New Tournament\"}]";
		
		Response response = POST("/api/tournaments", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		assertEquals(numOfTournaments + 1, models.Tournament.findAll().size());
		String content = response.out.toString();
		
		ArrayList<models.Tournament> tournaments  = controllers.rest.REST.deserialize(content);
		models.Tournament tournament = tournaments.get(0);
		assertNotNull(tournament);
		assertTrue(tournament.getId() instanceof Long);
		assertEquals("New Tournament", tournament.getName());
		Long dateValue = new java.lang.Long("1334361600000");
		assertEquals(new Date(dateValue), tournament.getDate());
	}
	
	@Test
	public void testPostCategory() {
		models.Tournament tournament = (models.Tournament) models.Tournament.findAll().get(0);
		assertNotNull(tournament);
		String tournamentAsJson = controllers.rest.REST.toJsonString(tournament);
		
		int numOfCategories = models.Category.findAll().size();
		String body = "[{\"class\":\"models.Category\",\"mode\":\"Double\"," + 
		              "\"name\": \"New Category\", \"tournament\":  "+ tournamentAsJson +"}]";
		
		Response response = POST("/api/categories", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		assertEquals(numOfCategories + 1, models.Category.findAll().size());
		String content = response.out.toString();
		
		ArrayList<models.Category> categories  = controllers.rest.REST.deserialize(content);
		models.Category category = categories.get(0);
		assertNotNull(category);
		assertTrue(category.getId() instanceof Long);
		assertEquals("New Category", category.name);
	}
	
	@Test
	public void testPostRound() {
		models.Category category = (models.Category) models.Category.findAll().get(0);
		assertNotNull(category);
		String categoryAsJson = controllers.rest.REST.toJsonString(category);
		
		int numOfRounds = models.Round.findAll().size();
		String body = "[{\"category\": " + categoryAsJson + ", \"class\":\"models.Round\"}]";
		
		Response response = POST("/api/rounds", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		assertEquals(numOfRounds + 1, models.Round.findAll().size());
		String content = response.out.toString();
		
		ArrayList<models.Round> rounds  = controllers.rest.REST.deserialize(content);
		models.Round round = rounds.get(0);
		assertNotNull(round);
		assertTrue(round.getId() instanceof Long);
		assertEquals(round.category, category);
	}
	
	@Test
	public void testPostBracket() {
		models.Round round = (models.Round) models.Round.findAll().get(0);
		assertNotNull(round);
		String roundAsJson = controllers.rest.REST.toJsonString(round);
		
		int numOfBrackets = models.Bracket.findAll().size();
		String body =  "[{\"class\":\"models.Bracket\",\"name\":\"New Bracket\", \"round\": " + 
		               roundAsJson +"}]";
		
		Response response = POST("/api/brackets", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		assertEquals(numOfBrackets + 1, models.Bracket.findAll().size());
		String content = response.out.toString();
		
		ArrayList<models.Bracket> brackets  = controllers.rest.REST.deserialize(content);
		models.Bracket bracket = brackets.get(0);
		assertNotNull(bracket);
		assertTrue(bracket.getId() instanceof Long);
		assertEquals("New Bracket", bracket.name);
		assertEquals(bracket.round, round);
	}
	
	@Test
	public void testPostFighter() {
		int numOfFighters = models.Fighter.findAll().size();
		
		
		String body = "[{\"age\":12,\"category\":null,\"class\":\"models.Fighter\"," + 
		              "\"firstname\":\"first_test\",\"lastname\":\"last_test\",\"size\":123}]";
		
		Response response = POST("/api/fighters", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		assertEquals(numOfFighters + 1, models.Fighter.findAll().size());
		String content = response.out.toString();
		
		ArrayList<models.Fighter> fighters  = controllers.rest.REST.deserialize(content);
		models.Fighter fighter = fighters.get(0);
		assertNotNull(fighter);
		assertTrue(fighter.getId() instanceof Long);
		assertEquals("first_test", fighter.firstname);
		assertEquals("last_test", fighter.lastname);
		assertEquals(12, fighter.age);
		assertEquals(123, fighter.size);
		assertEquals(null, fighter.category);
	}
	
	@Test
	public void testPostFightAreas() {
		int numOfFightAreas = models.FightArea.findAll().size();
		
		String body = "[{\"class\":\"models.FightArea\", \"name\":\"Tatami_1\"}]";
		
		Response response = POST("/api/fightareas", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		assertEquals(numOfFightAreas + 1, models.FightArea.findAll().size());
		String content = response.out.toString();
		
		ArrayList<models.FightArea> fightareas  = controllers.rest.REST.deserialize(content);
		models.FightArea fightarea = fightareas.get(0);
		assertNotNull(fightarea);
		assertTrue(fightarea.getId() instanceof Long);
		assertEquals("Tatami_1", fightarea.name);
	}
	
	@Test
	public void testPostFight() {
		models.Bracket bracket = (models.Bracket) models.Bracket.findAll().get(0);
		assertNotNull(bracket);
		String bracketAsJson = controllers.rest.REST.toJsonString(bracket);
		
		int numOfFights = models.Fight.findAll().size();
		String body = "[{\"bracket\": " + bracketAsJson + ",\"class\":\"models.Fight\"," + 
		              "\"result\":null,\"state\":\"Undecided\"}]";
		
		Response response = POST("/api/fights", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		assertEquals(numOfFights + 1, models.Fight.findAll().size());
		String content = response.out.toString();
		
		ArrayList<models.Fight> fights  = controllers.rest.REST.deserialize(content);
		models.Fight fight = fights.get(0);
		assertNotNull(fight);
		assertTrue(fight.getId() instanceof Long);
		assertEquals(bracket, fight.bracket);
		assertEquals(null, fight.result);
		assertEquals(models.Fight.State.Undecided, fight.state);
	}
	
	
	@Test
	public void testPostResult() {
		int numOfResults = models.Result.findAll().size();
		String body = "[{\"class\":\"models.Result\",\"fighterOneAssessment\":\"None\","+ 
		              "\"fighterOneCondition\":\"OK\",\"fighterTwoAssessment\":\"None\","+ 
		              "\"fighterTwoCondition\":\"OK\"}]";
		
		Response response = POST("/api/results", "application/json", body);
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		assertEquals(numOfResults + 1, models.Result.findAll().size());
		String content = response.out.toString();
		
		ArrayList<models.Result> results  = controllers.rest.REST.deserialize(content);
		models.Result result = results.get(0);
		assertNotNull(result);
		assertTrue(result.getId() instanceof Long);
		
		assertEquals(result.fighterOneAssessment, Result.Assessment.None);
		assertEquals(result.fighterTwoAssessment, Result.Assessment.None);
		assertEquals(result.fighterOneCondition, Result.Condition.OK);
		assertEquals(result.fighterTwoCondition, Result.Condition.OK);
	}
	
	@Test
	public void testPutTournament() {
		List<Tournament> tournaments = Tournament.findAll();
		assertTrue(tournaments.size() > 0);
		
		Tournament tournament = tournaments.get(0);
		assertEquals("WintiCup Test", tournament.getName());
		
		String body = "[{\"class\":\"models.Tournament\", \"name\":\"WintiCup Updated\"}]";
		Response response = PUT("/api/tournaments/" + tournament.getId(), "application/json", body);
		
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();
		
		ArrayList<models.Tournament> updatedTournaments  = controllers.rest.REST.deserialize(content);
		models.Tournament updatedTournament = updatedTournaments.get(0);
		
		assertNotNull(updatedTournament);
		assertTrue(updatedTournament.getId() instanceof Long);
		assertEquals(updatedTournament.getId(), tournament.getId());
		assertEquals("WintiCup Updated", updatedTournament.getName());
		
		Tournament.em().clear(); // update the db cache
		
		assertEquals(Tournament.findAll().size(), 1);
		Tournament dbTournament = Tournament.findById(updatedTournament.getId());
		assertEquals(updatedTournament.getName(), dbTournament.getName());
	}
	
	@Test
	public void testPutCategory() {
		List<Category> categories = Category.findAll();
		assertTrue(categories.size() > 0);
		
		Category cat = categories.get(0);
		assertEquals("Piccolo", cat.getName());
		assertEquals(Category.EliminationMode.Double, cat.getMode());
		assertEquals(Tournament.findAll().get(0), cat.getTournament());
		
		Date dt = new Date(new Long("1334361600001"));
		Tournament tournament = new Tournament("New Tournament", dt).save();
		String tournamentJson = controllers.rest.REST.toJsonString(tournament);
		
		String body = "[{\"class\":\"models.Category\", \"name\":\"Piccolo Updated\", " + 
		              "\"mode\":\"Single\", \"tournament\": " + tournamentJson + "}]";
		Response response = PUT("/api/categories/" + cat.getId(), "application/json", body);
		
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();
		
		ArrayList<models.Category> updatedCategories  = controllers.rest.REST.deserialize(content);
		models.Category updatedCategory = updatedCategories.get(0);
		
		assertNotNull(updatedCategory);
		assertTrue(updatedCategory.getId() instanceof Long);
		assertEquals(updatedCategory.getId(), cat.getId());
		assertEquals("Piccolo Updated", updatedCategory.getName());
		assertEquals(Category.EliminationMode.Single, updatedCategory.getMode());
		assertEquals(tournament, updatedCategory.getTournament());
		assertEquals("New Tournament", updatedCategory.getTournament().getName());
		assertEquals(new Date(new Long("1334361600001")), updatedCategory.getTournament().getDate());
		
		Category.em().clear(); // update the db cache
		
		assertEquals(2, Category.findAll().size());
		Category dbCategory = Category.findById(updatedCategory.getId());
		assertEquals(updatedCategory.getName(), dbCategory.getName());
		assertEquals(updatedCategory.getMode(), dbCategory.getMode());
		assertEquals(updatedCategory.getTournament(), dbCategory.getTournament());
		assertEquals(updatedCategory.getTournament().getName(), dbCategory.getTournament().getName());
		assertEquals(updatedCategory.getTournament().getDate(), dbCategory.getTournament().getDate());
	}
	
	@Test
	public void testPutRound() {
		List<Round> rounds = Round.findAll();
		assertEquals(2, rounds.size());
		
		Round rd = rounds.get(0);
		assertEquals("Piccolo", rd.getCategory().getName());
		
		Tournament tournament = (Tournament) Tournament.findAll().get(0);
		
		Category cat = new Category	(tournament, "New Category", models.Category.EliminationMode.Single).save();
		assertEquals(3, Category.findAll().size());
		
		String categoryJson = controllers.rest.REST.toJsonString(cat);
		
		String body = "[{\"class\":\"models.Round\", \"category\": " + categoryJson + "}]";
		Response response = PUT("/api/rounds/" + rd.getId(), "application/json", body);
		
		assertIsOk(response);
		assertContentType("application/json", response);
		assertCharset(play.Play.defaultWebEncoding, response);
		String content = response.out.toString();
		
		ArrayList<models.Round> updatedRounds  = controllers.rest.REST.deserialize(content);
		models.Round updatedRound = updatedRounds.get(0);
		
		assertNotNull(updatedRound);
		assertTrue(updatedRound.getId() instanceof Long);
		assertEquals(updatedRound.getId(), rd.getId());
		assertEquals("New Category", updatedRound.getCategory().getName());
		
		Category.em().clear(); // update the db cache
		
		assertEquals(2, Round.findAll().size());
		Round dbRound = Round.findById(updatedRound.getId());
		assertEquals(updatedRound.getCategory().getName(), dbRound.getCategory().getName());
	}
}
