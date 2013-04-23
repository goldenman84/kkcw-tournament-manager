import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Test;

import play.Logger;
import play.mvc.Result;

public class RESTApiTest extends BaseTest {
	@Test
	public void testGetTournament() {

		Date tDate = new Date(0);
		models.Tournament t = new models.Tournament("Winti Cup Test", tDate);
		t.save();

		Result result = play.test.Helpers.route(fakeRequest(GET, "/api/tournaments"));

		assertNotNull(result);
		assertEquals("application/json", play.test.Helpers.contentType(result));

		String content = play.test.Helpers.contentAsString(result);
		Logger.info("content: " + content);

		ArrayList<models.Tournament> tournaments = controllers.rest.REST.deserialize(content);
		models.Tournament tournament = tournaments.get(0);

		assertNotNull(tournament);
		assertTrue(tournament.getId() instanceof Long);
		assertEquals("Winti Cup Test", tournament.getName());

		assertEquals(tDate, tournament.getDate());
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////

	// !!!!! THE FOLLOWING TESTS MUST BE FIXED !!!!

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// @Test
	// public void testGetCategories() {
	// Response response = GET("/api/categories");
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Category> categories =
	// controllers.rest.REST.deserialize(content);
	// assertEquals(models.Category.findAll().size(), categories.size());
	// models.Category category = categories.get(0);
	// assertNotNull(category);
	// assertTrue(category.getId() instanceof Long);
	// }
	//
	// @Test
	// public void testGetBrackets() {
	// Response response = GET("/api/brackets");
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Bracket> brackets =
	// controllers.rest.REST.deserialize(content);
	// assertEquals(models.Bracket.findAll().size(), brackets.size());
	// models.Bracket bracket = brackets.get(0);
	// assertNotNull(bracket);
	// assertTrue(bracket.getId() instanceof Long);
	// assertTrue(bracket.name instanceof String);
	// }
	//
	// @Test
	// public void testGetRounds() {
	// Response response = GET("/api/rounds");
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Round> rounds =
	// controllers.rest.REST.deserialize(content);
	// assertEquals(models.Round.findAll().size(), rounds.size());
	// models.Round round = rounds.get(0);
	// assertNotNull(round);
	// assertTrue(round.getId() instanceof Long);
	// }
	//
	// @Test
	// public void testGetFighters() {
	// Response response = GET("/api/fighters");
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Fighter> fighters =
	// controllers.rest.REST.deserialize(content);
	// assertEquals(models.Fighter.findAll().size(), fighters.size());
	// models.Fighter fighter = fighters.get(0);
	// assertNotNull(fighter);
	// assertTrue(fighter.getId() instanceof Long);
	// assertTrue(fighter.age >= 0);
	// assertTrue(fighter.size >= 0);
	// assertTrue(fighter.firstname instanceof String);
	// assertTrue(fighter.lastname instanceof String);
	// }
	//
	// @Test
	// public void testGetFights() {
	// Response response = GET("/api/fights");
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Fight> fights =
	// controllers.rest.REST.deserialize(content);
	// assertEquals(models.Fight.findAll().size(), fights.size());
	// models.Fight fight = fights.get(0);
	// assertNotNull(fight);
	// assertTrue(fight.getId() instanceof Long);
	// assertTrue(fight.state instanceof Fight.State);
	// }
	//
	// @Test
	// public void testGetFightAreas() {
	// Response response = GET("/api/fightareas");
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.FightArea> fightareas =
	// controllers.rest.REST.deserialize(content);
	// assertEquals(models.FightArea.findAll().size(), fightareas.size());
	// models.FightArea fightarea = fightareas.get(0);
	// assertNotNull(fightarea);
	// assertTrue(fightarea.getId() instanceof Long);
	// assertTrue(fightarea.name instanceof String);
	// }
	//
	// @Test
	// public void testGetFightAreasFromCategory() {
	// // find a category which has some fight areas assigned
	// List<models.Category> categories = models.Category.findAll();
	// models.Category category = null;
	// for (models.Category cat : categories) {
	// if (cat.fightareas.size() > 0) {
	// category = cat;
	// break;
	// }
	// }
	// assertTrue(category.fightareas.size() > 0);
	//
	// Response response = GET("/api/categories/" + category.getId() +
	// "/fightareas");
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.FightArea> fightareas =
	// controllers.rest.REST.deserialize(content);
	// assertEquals(models.FightArea.findAll().size(), fightareas.size());
	// models.FightArea fightarea = fightareas.get(0);
	// assertNotNull(fightarea);
	// assertTrue(fightarea.getId() instanceof Long);
	// assertTrue(fightarea.name instanceof String);
	// }
	//
	// @Test
	// public void testGetResults() {
	// Response response = GET("/api/results");
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Result> results =
	// controllers.rest.REST.deserialize(content);
	// assertEquals(models.Result.findAll().size(), results.size());
	// models.Result result = results.get(0);
	// assertNotNull(result);
	// assertTrue(result.getId() instanceof Long);
	// assertTrue(result.fighterOneCondition instanceof models.Result.Condition);
	// assertTrue(result.fighterTwoCondition instanceof models.Result.Condition);
	// assertTrue(result.fighterOneAssessment instanceof
	// models.Result.Assessment);
	// assertTrue(result.fighterTwoAssessment instanceof
	// models.Result.Assessment);
	// }
	//
	// @Test
	// public void testPostTournament() {
	// int numOfTournaments = models.Tournament.findAll().size();
	//
	// String body = "[{\"class\":\"models.Tournament\","
	// + "\"date\":1334361600000,\"name\":\"New Tournament\"}]";
	//
	// Response response = POST("/api/tournaments", "application/json", body);
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// assertEquals(numOfTournaments + 1, models.Tournament.findAll().size());
	// String content = response.out.toString();
	//
	// ArrayList<models.Tournament> tournaments =
	// controllers.rest.REST.deserialize(content);
	// models.Tournament tournament = tournaments.get(0);
	// assertNotNull(tournament);
	// assertTrue(tournament.getId() instanceof Long);
	// assertEquals("New Tournament", tournament.getName());
	// Long dateValue = new java.lang.Long("1334361600000");
	// assertEquals(new Date(dateValue), tournament.getDate());
	// }
	//
	// @Test
	// public void testPostCategory() {
	// models.Tournament tournament = (models.Tournament)
	// models.Tournament.findAll().get(0);
	// assertNotNull(tournament);
	// String tournamentAsJson = controllers.rest.REST.toJsonString(tournament);
	//
	// int numOfCategories = models.Category.findAll().size();
	// String body = "[{\"class\":\"models.Category\",\"mode\":\"Double\","
	// + "\"name\": \"New Category\", \"tournament\":  " + tournamentAsJson +
	// "}]";
	//
	// Response response = POST("/api/categories", "application/json", body);
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// assertEquals(numOfCategories + 1, models.Category.findAll().size());
	// String content = response.out.toString();
	//
	// ArrayList<models.Category> categories =
	// controllers.rest.REST.deserialize(content);
	// models.Category category = categories.get(0);
	// assertNotNull(category);
	// assertTrue(category.getId() instanceof Long);
	// assertEquals("New Category", category.name);
	// }
	//
	// @Test
	// public void testPostRound() {
	// models.Category category = (models.Category)
	// models.Category.findAll().get(0);
	// assertNotNull(category);
	// String categoryAsJson = controllers.rest.REST.toJsonString(category);
	//
	// int numOfRounds = models.Round.findAll().size();
	// String body = "[{\"category\": " + categoryAsJson +
	// ", \"class\":\"models.Round\"}]";
	//
	// Response response = POST("/api/rounds", "application/json", body);
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// assertEquals(numOfRounds + 1, models.Round.findAll().size());
	// String content = response.out.toString();
	//
	// ArrayList<models.Round> rounds =
	// controllers.rest.REST.deserialize(content);
	// models.Round round = rounds.get(0);
	// assertNotNull(round);
	// assertTrue(round.getId() instanceof Long);
	// assertEquals(round.category, category);
	// }
	//
	// @Test
	// public void testPostBracket() {
	// models.Round round = (models.Round) models.Round.findAll().get(0);
	// assertNotNull(round);
	// String roundAsJson = controllers.rest.REST.toJsonString(round);
	//
	// int numOfBrackets = models.Bracket.findAll().size();
	// String body =
	// "[{\"class\":\"models.Bracket\",\"name\":\"New Bracket\", \"round\": "
	// + roundAsJson + "}]";
	//
	// Response response = POST("/api/brackets", "application/json", body);
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// assertEquals(numOfBrackets + 1, models.Bracket.findAll().size());
	// String content = response.out.toString();
	//
	// ArrayList<models.Bracket> brackets =
	// controllers.rest.REST.deserialize(content);
	// models.Bracket bracket = brackets.get(0);
	// assertNotNull(bracket);
	// assertTrue(bracket.getId() instanceof Long);
	// assertEquals("New Bracket", bracket.name);
	// assertEquals(bracket.round, round);
	// }
	//
	// @Test
	// public void testPostFighter() {
	// int numOfFighters = models.Fighter.findAll().size();
	//
	// String body =
	// "[{\"age\":12,\"category\":null,\"class\":\"models.Fighter\","
	// + "\"firstname\":\"first_test\",\"lastname\":\"last_test\",\"size\":123}]";
	//
	// Response response = POST("/api/fighters", "application/json", body);
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// assertEquals(numOfFighters + 1, models.Fighter.findAll().size());
	// String content = response.out.toString();
	//
	// ArrayList<models.Fighter> fighters =
	// controllers.rest.REST.deserialize(content);
	// models.Fighter fighter = fighters.get(0);
	// assertNotNull(fighter);
	// assertTrue(fighter.getId() instanceof Long);
	// assertEquals("first_test", fighter.firstname);
	// assertEquals("last_test", fighter.lastname);
	// assertEquals(12, fighter.age);
	// assertEquals(123, fighter.size);
	// assertEquals(null, fighter.category);
	// }
	//
	// @Test
	// public void testPostFightAreas() {
	// int numOfFightAreas = models.FightArea.findAll().size();
	//
	// String body = "[{\"class\":\"models.FightArea\", \"name\":\"Tatami_1\"}]";
	//
	// Response response = POST("/api/fightareas", "application/json", body);
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// assertEquals(numOfFightAreas + 1, models.FightArea.findAll().size());
	// String content = response.out.toString();
	//
	// ArrayList<models.FightArea> fightareas =
	// controllers.rest.REST.deserialize(content);
	// models.FightArea fightarea = fightareas.get(0);
	// assertNotNull(fightarea);
	// assertTrue(fightarea.getId() instanceof Long);
	// assertEquals("Tatami_1", fightarea.name);
	// }
	//
	// @Test
	// public void testPostFight() {
	// models.Bracket bracket = (models.Bracket) models.Bracket.findAll().get(0);
	// assertNotNull(bracket);
	// String bracketAsJson = controllers.rest.REST.toJsonString(bracket);
	//
	// int numOfFights = models.Fight.findAll().size();
	// String body = "[{\"bracket\": " + bracketAsJson +
	// ",\"class\":\"models.Fight\","
	// + "\"result\":null,\"state\":\"Undecided\"}]";
	//
	// Response response = POST("/api/fights", "application/json", body);
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// assertEquals(numOfFights + 1, models.Fight.findAll().size());
	// String content = response.out.toString();
	//
	// ArrayList<models.Fight> fights =
	// controllers.rest.REST.deserialize(content);
	// models.Fight fight = fights.get(0);
	// assertNotNull(fight);
	// assertTrue(fight.getId() instanceof Long);
	// assertEquals(bracket, fight.bracket);
	// assertEquals(null, fight.result);
	// assertEquals(models.Fight.State.Undecided, fight.state);
	// }
	//
	// @Test
	// public void testPostResult() {
	// int numOfResults = models.Result.findAll().size();
	// String body =
	// "[{\"class\":\"models.Result\",\"fighterOneAssessment\":\"None\","
	// + "\"fighterOneCondition\":\"OK\",\"fighterTwoAssessment\":\"None\","
	// + "\"fighterTwoCondition\":\"OK\"}]";
	//
	// Response response = POST("/api/results", "application/json", body);
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// assertEquals(numOfResults + 1, models.Result.findAll().size());
	// String content = response.out.toString();
	//
	// ArrayList<models.Result> results =
	// controllers.rest.REST.deserialize(content);
	// models.Result result = results.get(0);
	// assertNotNull(result);
	// assertTrue(result.getId() instanceof Long);
	//
	// assertEquals(result.fighterOneAssessment, Result.Assessment.None);
	// assertEquals(result.fighterTwoAssessment, Result.Assessment.None);
	// assertEquals(result.fighterOneCondition, Result.Condition.OK);
	// assertEquals(result.fighterTwoCondition, Result.Condition.OK);
	// }
	//
	// @Test
	// public void testPutTournament() {
	// List<Tournament> tournaments = Tournament.findAll();
	// assertTrue(tournaments.size() > 0);
	//
	// Tournament tournament = tournaments.get(0);
	// assertEquals("WintiCup Test", tournament.getName());
	//
	// String body =
	// "[{\"class\":\"models.Tournament\", \"name\":\"WintiCup Updated\"}]";
	// Response response = PUT("/api/tournaments/" + tournament.getId(),
	// "application/json", body);
	//
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Tournament> updatedTournaments =
	// controllers.rest.REST.deserialize(content);
	// models.Tournament updatedTournament = updatedTournaments.get(0);
	//
	// assertNotNull(updatedTournament);
	// assertTrue(updatedTournament.getId() instanceof Long);
	// assertEquals(updatedTournament.getId(), tournament.getId());
	// assertEquals("WintiCup Updated", updatedTournament.getName());
	//
	// Tournament.em().clear(); // update the db cache
	//
	// assertEquals(Tournament.findAll().size(), 1);
	// Tournament dbTournament = Tournament.findById(updatedTournament.getId());
	// assertEquals(updatedTournament.getName(), dbTournament.getName());
	// }
	//
	// @Test
	// public void testPutCategory() {
	// List<Category> categories = Category.findAll();
	// assertTrue(categories.size() > 0);
	//
	// Category cat = categories.get(0);
	// assertEquals("Piccolo", cat.getName());
	// assertEquals(Category.EliminationMode.Double, cat.getMode());
	// assertEquals(Tournament.findAll().get(0), cat.getTournament());
	//
	// Date dt = new Date(new Long("1334361600001"));
	// Tournament tournament = new Tournament("New Tournament", dt).save();
	// String tournamentJson = controllers.rest.REST.toJsonString(tournament);
	//
	// String body =
	// "[{\"class\":\"models.Category\", \"name\":\"Piccolo Updated\", "
	// + "\"mode\":\"Single\", \"tournament\": " + tournamentJson + "}]";
	// Response response = PUT("/api/categories/" + cat.getId(),
	// "application/json", body);
	//
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Category> updatedCategories =
	// controllers.rest.REST.deserialize(content);
	// models.Category updatedCategory = updatedCategories.get(0);
	//
	// assertNotNull(updatedCategory);
	// assertTrue(updatedCategory.getId() instanceof Long);
	// assertEquals(updatedCategory.getId(), cat.getId());
	// assertEquals("Piccolo Updated", updatedCategory.getName());
	// assertEquals(Category.EliminationMode.Single, updatedCategory.getMode());
	// assertEquals(tournament, updatedCategory.getTournament());
	// assertEquals("New Tournament", updatedCategory.getTournament().getName());
	// assertEquals(new Date(new Long("1334361600001")),
	// updatedCategory.getTournament().getDate());
	//
	// Category.em().clear(); // update the db cache
	//
	// assertEquals(2, Category.findAll().size());
	// Category dbCategory = Category.findById(updatedCategory.getId());
	// assertEquals(updatedCategory.getName(), dbCategory.getName());
	// assertEquals(updatedCategory.getMode(), dbCategory.getMode());
	// assertEquals(updatedCategory.getTournament(), dbCategory.getTournament());
	// assertEquals(updatedCategory.getTournament().getName(),
	// dbCategory.getTournament().getName());
	// assertEquals(updatedCategory.getTournament().getDate(),
	// dbCategory.getTournament().getDate());
	// }
	//
	// @Test
	// public void testPutRound() {
	// List<Round> rounds = Round.findAll();
	// assertEquals(2, rounds.size());
	//
	// Round rd = rounds.get(0);
	// assertEquals("Piccolo", rd.getCategory().getName());
	//
	// Tournament tournament = (Tournament) Tournament.findAll().get(0);
	//
	// Category cat = new Category(tournament, "New Category",
	// models.Category.EliminationMode.Single)
	// .save();
	// assertEquals(3, Category.findAll().size());
	//
	// String categoryJson = controllers.rest.REST.toJsonString(cat);
	//
	// String body = "[{\"class\":\"models.Round\", \"category\": " + categoryJson
	// + "}]";
	// Response response = PUT("/api/rounds/" + rd.getId(), "application/json",
	// body);
	//
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Round> updatedRounds =
	// controllers.rest.REST.deserialize(content);
	// models.Round updatedRound = updatedRounds.get(0);
	//
	// assertNotNull(updatedRound);
	// assertTrue(updatedRound.getId() instanceof Long);
	// assertEquals(updatedRound.getId(), rd.getId());
	// assertEquals("New Category", updatedRound.getCategory().getName());
	//
	// Round.em().clear(); // update the db cache
	//
	// assertEquals(2, Round.findAll().size());
	// Round dbRound = Round.findById(updatedRound.getId());
	// assertEquals(updatedRound.getCategory().getName(),
	// dbRound.getCategory().getName());
	// }
	//
	// @Test
	// public void testPutBracket() {
	// List<Bracket> brackets = Bracket.findAll();
	// assertEquals(4, brackets.size());
	//
	// Bracket bracket = brackets.get(0);
	// assertEquals("Start Bracket", bracket.getName());
	// assertNotNull(bracket.getRound());
	// assertEquals(bracket, bracket.getRound().getBrackets().get(0));
	//
	// Category category = (Category) Category.findAll().get(0);
	// Round round = new models.Round(category).save();
	//
	// String roundAsJson = controllers.rest.REST.toJsonString(round);
	//
	// String body = "[{\"class\":\"models.Bracket\", \"round\": " + roundAsJson
	// + ", \"name\": \"Bracket Updated\" }]";
	// Response response = PUT("/api/brackets/" + bracket.getId(),
	// "application/json", body);
	//
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Bracket> updatedBrackets =
	// controllers.rest.REST.deserialize(content);
	// models.Bracket updatedBracket = updatedBrackets.get(0);
	//
	// assertNotNull(updatedBracket);
	// assertEquals(bracket.getId(), updatedBracket.getId());
	// assertEquals("Bracket Updated", updatedBracket.getName());
	// assertEquals(round.getId(), updatedBracket.getRound().getId());
	// assertEquals(round.getCategory().getId(),
	// updatedBracket.getRound().getCategory().getId());
	//
	// Bracket.em().clear(); // update the db cache
	//
	// assertEquals(4, Bracket.findAll().size());
	// Bracket dbBracket = Bracket.findById(updatedBracket.getId());
	//
	// assertEquals(updatedBracket.getName(), dbBracket.getName());
	// assertEquals(updatedBracket.getRound().getId(),
	// dbBracket.getRound().getId());
	// assertEquals(updatedBracket.getRound().getCategory().getId(),
	// dbBracket.getRound()
	// .getCategory().getId());
	// }
	//
	// @Test
	// public void testPutFighter() {
	// List<Fighter> fighters = Fighter.findAll();
	// assertEquals(fighters.size(), 13);
	//
	// Fighter fighter = fighters.get(0);
	// assertEquals("bob", fighter.getFirstname());
	// assertEquals("marley", fighter.getLastname());
	// assertEquals(14, fighter.getAge());
	// assertEquals(134, fighter.getSize());
	//
	// String body =
	// "[{\"class\":\"models.Fighter\", \"firstname\":\"New Firstname\", "
	// + "\"lastname\":\"New Lastname\", \"age\":15, \"size\":150 }]";
	// Response response = PUT("/api/fighters/" + fighter.getId(),
	// "application/json", body);
	//
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Fighter> updatedFighters =
	// controllers.rest.REST.deserialize(content);
	// models.Fighter updatedFighter = updatedFighters.get(0);
	//
	// assertNotNull(updatedFighter);
	// assertTrue(updatedFighter.getId() instanceof Long);
	// assertEquals(updatedFighter.getId(), fighter.getId());
	// assertEquals("New Firstname", updatedFighter.getFirstname());
	// assertEquals("New Lastname", updatedFighter.getLastname());
	// assertEquals(15, updatedFighter.getAge());
	// assertEquals(150, updatedFighter.getSize());
	//
	// Fighter.em().clear(); // update the db cache
	//
	// assertEquals(Fighter.findAll().size(), 13);
	// Fighter dbFighter = Fighter.findById(updatedFighter.getId());
	// assertEquals(updatedFighter.getFirstname(), dbFighter.getFirstname());
	// assertEquals(updatedFighter.getLastname(), dbFighter.getLastname());
	// assertEquals(updatedFighter.getAge(), dbFighter.getAge());
	// assertEquals(updatedFighter.getSize(), dbFighter.getSize());
	// }
	//
	// @Test
	// public void testPutFight() {
	// List<Fight> fights = Fight.findAll();
	// assertEquals(fights.size(), 6);
	//
	// Fight fight = fights.get(0);
	// assertNotNull(fight.getBracket());
	// assertEquals(2, fight.getFighters().size());
	// assertEquals("bob", fight.getFighters().get(0).getFirstname());
	// assertEquals("han", fight.getFighters().get(1).getFirstname());
	// assertNotNull("fight has a result", fight.getResult());
	// assertEquals(Fight.State.Undecided, fight.getState());
	// assertEquals("Tatami_1", fight.getFightArea().getName());
	//
	// Round round = (Round) Round.findAll().get(0);
	// Bracket bracket = new Bracket(round, "New Bracket").save();
	//
	// Result result = new Result(Assessment.Win, Assessment.Loss).save();
	// models.FightArea fightearea = new models.FightArea("Tatami_New").save();
	// Fight.State state = Fight.State.Decided;
	//
	// String bracketAsJson = controllers.rest.REST.toJsonString(bracket);
	// String resultAsJson = controllers.rest.REST.toJsonString(result);
	// String fightAreaAsJson = controllers.rest.REST.toJsonString(fightearea);
	// String stateAsJson = controllers.rest.REST.toJsonString(state);
	//
	// String body = "[{\"class\":\"models.Fight\",\"bracket\": " + bracketAsJson
	// + ", \"result\":"
	// + resultAsJson + ", \"fightarea\":" + fightAreaAsJson + ", \"state\": " +
	// stateAsJson
	// + "}]";
	// Response response = PUT("/api/fights/" + fight.getId(), "application/json",
	// body);
	//
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Fight> updatedFights =
	// controllers.rest.REST.deserialize(content);
	// models.Fight updatedFight = updatedFights.get(0);
	//
	// assertNotNull(updatedFight);
	// assertTrue(updatedFight.getId() instanceof Long);
	// assertEquals(updatedFight.getId(), fight.getId());
	// assertEquals(bracket.getName(), updatedFight.getBracket().getName());
	// assertEquals(result.getId(), updatedFight.getResult().getId());
	// assertEquals(Assessment.Win,
	// updatedFight.getResult().fighterOneAssessment);
	// assertEquals(Assessment.Loss,
	// updatedFight.getResult().fighterTwoAssessment);
	// assertEquals(fightearea.getName(), updatedFight.getFightArea().getName());
	// assertEquals(state.name(), updatedFight.getState().name());
	//
	// Fight.em().clear(); // update the db cache
	//
	// assertEquals(6, Fight.findAll().size());
	// Fight dbFight = Fight.findById(updatedFight.getId());
	// assertEquals(updatedFight.getBracket().getName(),
	// dbFight.getBracket().getName());
	// assertEquals(updatedFight.getResult().getId(),
	// dbFight.getResult().getId());
	// assertEquals(updatedFight.getResult().fighterOneAssessment,
	// dbFight.getResult().fighterOneAssessment);
	// assertEquals(updatedFight.getResult().fighterTwoAssessment,
	// dbFight.getResult().fighterTwoAssessment);
	// assertEquals(updatedFight.getFightArea().getName(),
	// dbFight.getFightArea().getName());
	// assertEquals(updatedFight.getState().name(), dbFight.getState().name());
	// }
	//
	// @Test
	// public void testPutFightArea() {
	// List<FightArea> fightareas = FightArea.findAll();
	// assertEquals(3, fightareas.size());
	//
	// FightArea fightarea = fightareas.get(0);
	// assertEquals("Tatami_1", fightarea.getName());
	//
	// String body =
	// "[{\"class\":\"models.FightArea\", \"name\": \"Tatami_Updated\" }]";
	// Response response = PUT("/api/fightareas/" + fightarea.getId(),
	// "application/json", body);
	//
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.FightArea> updatedFightAreas =
	// controllers.rest.REST.deserialize(content);
	// models.FightArea updatedFightArea = updatedFightAreas.get(0);
	//
	// assertNotNull(updatedFightArea);
	// assertTrue(updatedFightArea.getId() instanceof Long);
	// assertEquals(updatedFightArea.getId(), fightarea.getId());
	// assertEquals("Tatami_Updated", updatedFightArea.getName());
	//
	// FightArea.em().clear(); // update the db cache
	//
	// assertEquals(3, FightArea.findAll().size());
	// FightArea dbFightArea = FightArea.findById(updatedFightArea.getId());
	// assertEquals(updatedFightArea.getName(), dbFightArea.getName());
	// }
	//
	// @Test
	// public void testPutResult() {
	// List<Result> results = Result.findAll();
	// assertEquals(1, results.size());
	//
	// Result result = results.get(0);
	// assertEquals(Result.Assessment.None, result.getFighterOneAssessment());
	// assertEquals(Result.Condition.OK, result.getFighterOneCondition());
	// assertEquals(Result.Assessment.None, result.getFighterTwoAssessment());
	// assertEquals(Result.Condition.OK, result.getFighterTwoCondition());
	//
	// String body =
	// "[{\"class\":\"models.Result\", \"fighterOneAssessment\": \"Win\", "
	// + "\"fighterOneCondition\": \"OK\", \"fighterTwoAssessment\": \"Loss\", "
	// + "\"fighterTwoCondition\": \"Injury\", }]";
	// Response response = PUT("/api/results/" + result.getId(),
	// "application/json", body);
	//
	// assertIsOk(response);
	// assertContentType("application/json", response);
	// assertCharset(play.Play.defaultWebEncoding, response);
	// String content = response.out.toString();
	//
	// ArrayList<models.Result> updatedResults =
	// controllers.rest.REST.deserialize(content);
	// models.Result updatedResult = updatedResults.get(0);
	//
	// assertNotNull(updatedResult);
	// assertEquals(Result.Assessment.Win,
	// updatedResult.getFighterOneAssessment());
	// assertEquals(Result.Condition.OK, updatedResult.getFighterOneCondition());
	// assertEquals(Result.Assessment.Loss,
	// updatedResult.getFighterTwoAssessment());
	// assertEquals(Result.Condition.Injury,
	// updatedResult.getFighterTwoCondition());
	//
	// Result.em().clear(); // update the db cache
	//
	// assertEquals(1, Result.findAll().size());
	// Result dbResult = Result.findById(updatedResult.getId());
	// assertEquals(updatedResult.getFighterOneAssessment(),
	// dbResult.getFighterOneAssessment());
	// assertEquals(updatedResult.getFighterOneCondition(),
	// dbResult.getFighterOneCondition());
	// assertEquals(updatedResult.getFighterTwoAssessment(),
	// dbResult.getFighterTwoAssessment());
	// assertEquals(updatedResult.getFighterTwoCondition(),
	// dbResult.getFighterTwoCondition());
	// }
}