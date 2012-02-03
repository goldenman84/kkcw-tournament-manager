import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.dialect.FirebirdDialect;
import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void createAndRetrieveTournament() {

		// create and save a new tournament in database
		Date tDate = new Date();
		new Tournament("Winti Cup 2012", tDate).save();

		// find the user in database
		Tournament t = Tournament.find("byName", "Winti Cup 2012").first();

		assertNotNull(t);
		assertEquals(t.date, tDate);
	}

	@Test
	public void createCategory() {
		// create and save a new tournament in database
		Date tDate = new Date();
		Tournament tournament = new Tournament("Winti Cup 2012", tDate).save();

		// create a new category
		new Category(tournament, "Piccolo", Category.EliminationMode.Single).save();

		// test the category
		assertEquals(1, Category.count());

		// retrieve the posts
		List<Category> categories = Category.find("byTournament", tournament).fetch();

		assertEquals(1, categories.size());
		Category category = categories.get(0);
		assertEquals(tournament, category.tournament);
		assertEquals("Piccolo", category.name);
		assertEquals(Category.EliminationMode.Single, category.mode);
	}

	@Test
	public void createFighter() {
		Fighter fighter = new Fighter("Mike", "Tyson", 45, 190).save();

		assertEquals(1, Fighter.count());

		Fighter mike = Fighter.find("byFirstnameAndLastname", fighter.firstname, fighter.lastname)
				.first();

		assertNotNull(mike);
		assertEquals("Mike", mike.firstname);
		assertEquals("Tyson", mike.lastname);
		assertEquals(45, mike.age);
		assertEquals(190, mike.size);

	}

	@Test
	public void createRound() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date()).save();
		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double)
				.save();
		Category medium = new Category(tournament, "Medium", Category.EliminationMode.Double)
				.save();

		// create new rounds
		new Round(piccolo).save();
		new Round(medium).save();
		new Round(medium).save();

		// retrieve rounds by category
		List<Round> piccoloRounds = Round.find("byCategory", piccolo).fetch();
		assertEquals(1, piccoloRounds.size());

		List<Round> mediumRounds = Round.find("byCategory", medium).fetch();
		assertEquals(2, mediumRounds.size());

		Round piccoloRound = piccoloRounds.get(0);
		assertEquals("Piccolo", piccoloRound.category.name);

		Round mediumRound = mediumRounds.get(0);
		assertEquals("Medium", mediumRound.category.name);
	}

	@Test
	public void createBrackets() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date()).save();
		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double)
				.save();

		Round round = new Round(piccolo).save();

		new Bracket(round, "Winner Bracket").save();
		new Bracket(round, "Loser Bracket").save();		

		// retrieve rounds by category
		List<Round> piccoloRounds = Round.find("byCategory", piccolo).fetch();
		assertEquals(1, piccoloRounds.size());

		Round piccoloRound = piccoloRounds.get(0);
		assertEquals("Piccolo", piccoloRound.category.name);

		List<Bracket> brackets = Bracket.find("byRound", piccoloRound).fetch();
		assertEquals(2, brackets.size());

		Bracket winnerBracket = brackets.get(0);
		assertEquals("Winner Bracket", winnerBracket.name);

		Bracket loserBracket = brackets.get(1);
		assertEquals("Loser Bracket", loserBracket.name);
	}

	@Test
	public void createFights() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date()).save();
		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double)
				.save();

		Round round = new Round(piccolo).save();
		Bracket bracket = new Bracket(round, "Winner Bracket").save();

		new Fight(bracket).save();
		List<Fight> fights = Fight.find("byBracket", bracket).fetch();
		assertEquals(1, fights.size());

		new Fight(bracket).save();
		assertEquals(1, fights.size());
	}

	@Test
	public void createFighters() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date()).save();
		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double)
				.save();

		Round round = new Round(piccolo).save();
		Bracket bracket = new Bracket(round, "Winner Bracket").save();

		Fight fightOne = new Fight(bracket).save();
		Fighter mike = new Fighter("Mike", "Tyson", 45, 190).save();
		Fighter evander = new Fighter("Evander", "Holyfield", 38, 188).save();

		List<Fighter> allFighters = Fighter.findAll();
		assertEquals(2, allFighters.size());

		fightOne.addFighter(mike);
		fightOne.addFighter(evander);

		assertEquals(2, fightOne.fighters.size());

		Fight fightTwo = new Fight(bracket).save();

		Fighter vladimir = new Fighter("Vladimir", "Klitchko", 38, 195).save();
		fightTwo.addFighter(mike);
		fightTwo.addFighter(vladimir);

		assertEquals(2, fightTwo.fighters.size());

		List<Fight> fights = Fight.findAll();
		assertEquals(2, fights.size());

		Fight firstFight = fights.get(0);
		assertEquals(2, firstFight.fighters.size());

		Fighter firstFighter = firstFight.fighters.get(0);
		assertEquals("Mike", firstFighter.firstname);
		assertEquals("Tyson", firstFighter.lastname);
		assertEquals(45, firstFighter.age);
		assertEquals(190, firstFighter.size);
	}

	@Test
	public void createResults() {
		Tournament tournament = new Tournament("Winti Cup 2011", new Date()).save();
		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Single)
				.save();
		Round round = new Round(piccolo).save();
		Bracket bracket = new Bracket(round, "Winner Bracket").save();
		Fight fight = new Fight(bracket).save();

		Fighter mike = new Fighter("Mike", "Tyson", 45, 190).save();
		Fighter evander = new Fighter("Evander", "Holyfield", 38, 188).save();

		fight.addFighter(mike);
		fight.addFighter(evander);

		Result result = new Result(Result.Assessment.Win, Result.Assessment.Loss).save();
		fight.assignResult(result);
		
		assertEquals(1, Fight.count());
		assertEquals(1, Result.count());
		Result r = (Result) Result.findAll().get(0);
		Fight f = (Fight) Fight.findAll().get(0);
		
		assertNotNull(r);
		assertNotNull(f);
		assertNotNull(f.result);
		//assertNotNull(r.fight);
		//assertEquals(r.fight, f);
		assertEquals(f.result, r);
		
		assertEquals(Result.Assessment.Win,f.result.fighterOneAssessment);
		assertEquals(Result.Assessment.Loss,f.result.fighterTwoAssessment);
	}
}
