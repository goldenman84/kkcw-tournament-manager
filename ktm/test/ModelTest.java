import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;

import org.junit.Test;


public class ModelTest extends BaseTest {

	@Test
	public void createAndRetrieveTournament() {

		// create and save a new tournament in database
		Date tDate = new Date();
		models.Tournament tournament = new models.Tournament("Winti Cup 2012", tDate);
		tournament.save();

		// find the user in database
		Tournament t = models.Tournament.find.where().eq("name", "Winti Cup 2012").findUnique();

		assertNotNull("tournament is defined", t);
		assertEquals(t.getDate(), tDate);
	}

	@Test
	public void createCategory() {
		// create and save a new tournament in database
		Date tDate = new Date();
		Tournament tournament = new models.Tournament((long) 1, "Winti Cup 2012", tDate);
		tournament.save();

		// create a new category
		Category cat = new models.Category(tournament, "Piccolo", Category.EliminationMode.Single);
		cat.save();

		// test the category
		assertEquals(1, Category.find.all().size());

		// retrieve the posts
		List<Category> categories = models.Category.find.where().eq("tournament", tournament).findList();
		assertEquals(1, categories.size());

		Category category = categories.get(0);
		assertEquals(tournament, category.getTournament());
		assertEquals("Piccolo", category.getName());
		assertEquals(Category.EliminationMode.Single, category.mode);
	}

	@Test
	public void createFighter() {
		Fighter fighter = new Fighter("Mike", "Tyson", 45, 190);
		fighter.save();

		assertEquals(1, Fighter.find.all().size());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("firstname", fighter.getFirstname());
		map.put("lastname", fighter.getLastname());

		Fighter mike = models.Fighter.find.where().allEq(map).findList().get(0);

		assertNotNull(mike);
		assertEquals("Mike", mike.getFirstname());
		assertEquals("Tyson", mike.getLastname());
		assertEquals(45, mike.getAge());
		assertEquals(190, mike.getSize());
	}

	@Test
	public void createRound() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date());
		tournament.save();

		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double);
		piccolo.save();

		Category medium = new Category(tournament, "Medium", Category.EliminationMode.Double);
		medium.save();

		// create new rounds
		new Round(piccolo).save();
		new Round(medium).save();
		new Round(medium).save();

		// retrieve rounds by category
		List<Round> piccoloRounds = models.Round.find.where().eq("category", piccolo).findList();
		assertEquals(1, piccoloRounds.size());

		List<Round> mediumRounds = models.Round.find.where().eq("category", medium).findList();
		assertEquals(2, mediumRounds.size());

		Round piccoloRound = piccoloRounds.get(0);
		assertEquals("Piccolo", piccoloRound.category.getName());

		Round mediumRound = mediumRounds.get(1);
		assertEquals("Medium", mediumRound.category.getName());
	}

	@Test
	public void createBrackets() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date());
		tournament.save();

		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double);
		piccolo.save();

		Round round = new Round(piccolo);
		round.save();

		new Bracket(round, "Winner Bracket").save();
		new Bracket(round, "Loser Bracket").save();

		// retrieve rounds by category
		List<Round> piccoloRounds = models.Round.find.where().eq("category", piccolo).findList();
		assertEquals(1, piccoloRounds.size());

		Round piccoloRound = piccoloRounds.get(0);
		assertEquals("Piccolo", piccoloRound.category.getName());

		List<Bracket> brackets = models.Bracket.find.where().eq("round", piccoloRound).findList();
		assertEquals(2, brackets.size());

		Bracket winnerBracket = brackets.get(0);
		assertEquals("Winner Bracket", winnerBracket.getName());

		Bracket loserBracket = brackets.get(1);
		assertEquals("Loser Bracket", loserBracket.getName());
	}

	@Test
	public void createFights() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date());
		tournament.save();

		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double);
		piccolo.save();

		Round round = new Round(piccolo);
		round.save();

		Bracket bracket = new Bracket(round, "Winner Bracket");
		bracket.save();

		new Fight(bracket).save();
		List<Fight> fights = models.Fight.find.where().eq("bracket", bracket).findList();
		assertEquals(1, fights.size());

		new Fight(bracket).save();
		assertEquals(1, fights.size());
	}

	@Test
	public void createFightAreas() {
		int numOfFightAreas = models.FightArea.find.findList().size();

		FightArea fightarea = new models.FightArea("Tatami-1");
		fightarea.save();
		assertNotNull(fightarea);
		assertEquals("Tatami-1", fightarea.getName());

		int newNumOfFightAreas = models.FightArea.find.findList().size();
		assertEquals(numOfFightAreas + 1 , newNumOfFightAreas);

		Tournament tournament = new Tournament("Winti Cup 2012", new Date());
		tournament.save();

		Category category = new Category(tournament, "Piccolo", Category.EliminationMode.Double);
		category.save();

		category.addFightArea(fightarea);
		assertEquals(fightarea, category.fightareas.get(0));

		category.addFightArea("Tatami-2");
		assertEquals(2, category.fightareas.size());
		assertEquals("Tatami-2", category.fightareas.get(category.fightareas.size() - 1).getName());

		newNumOfFightAreas = models.FightArea.find.findList().size();
		assertEquals(numOfFightAreas + 2, newNumOfFightAreas);
	}

	@Test
	public void assignFightArea() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date());
		tournament.save();

		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double);
		piccolo.save();

		Round round = new Round(piccolo);
		round.save();

		Bracket bracket = new Bracket(round, "Winner Bracket");
		bracket.save();

		Fight fight = new Fight(bracket);
		fight.save();

		FightArea fightarea = new FightArea("Tatami-1");
		fightarea.save();

		fight.assignFightArea(fightarea);

		FightArea assignedFightArea = (models.FightArea) models.Fight.find.byId(fight.getId()).getFightArea();
		assertEquals(fightarea.getId(), assignedFightArea.getId());
		assertEquals(fightarea.getName(), assignedFightArea.getName());
	}

	@Test
	public void createFighters() {
		Tournament tournament = new Tournament("Winti Cup 2012", new Date());
		tournament.save();

		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Double);
		piccolo.save();

		Round round = new Round(piccolo);
		round.save();

		Bracket bracket = new Bracket(round, "Winner Bracket");
		bracket.save();

		// create and add two fighters into a fight

		Fight fightOne = new Fight(bracket);
		fightOne.save();

		Fighter mike = new Fighter("Mike", "Tyson", 45, 190);
		mike.save();

		Fighter evander = new Fighter("Evander", "Holyfield", 38, 188);
		evander.save();

		List<Fighter> allFighters = models.Fighter.find.all();
		assertEquals(2, allFighters.size());

		fightOne.addFighter(mike);
		fightOne.addFighter(evander);

		assertEquals(2, fightOne.fighters.size());

		Fight fightTwo = new Fight(bracket);
		fightTwo.save();

		Fighter vladimir = new Fighter("Vladimir", "Klitchko", 38, 195);
		vladimir.save();

		fightTwo.addFighter(mike);
		fightTwo.addFighter(vladimir);

		assertEquals(2, fightTwo.fighters.size());

		// check the instances in the DB
		List<Fight> fights = models.Fight.find.all();
		assertEquals(2, fights.size());

		Fight firstFight = fights.get(0);
		assertEquals(2, firstFight.fighters.size());

		Fighter firstFighter = firstFight.fighters.get(0);
		assertEquals("Mike", firstFighter.getFirstname());
		assertEquals("Tyson", firstFighter.getLastname());
		assertEquals(45, firstFighter.getAge());
		assertEquals(190, firstFighter.getSize());
	}

	@Test
	public void createResults() {
		Tournament tournament = new Tournament("Winti Cup 2011", new Date());
		tournament.save();

		Category piccolo = new Category(tournament, "Piccolo", Category.EliminationMode.Single);
		piccolo.save();

		Round round = new Round(piccolo);
		round.save();

		Bracket bracket = new Bracket(round, "Winner Bracket");
		bracket.save();

		Fight fight = new Fight(bracket);
		fight.save();

		Fighter mike = new Fighter("Mike", "Tyson", 45, 190);
		mike.save();

		Fighter evander = new Fighter("Evander", "Holyfield", 38, 188);
		evander.save();

		fight.addFighter(mike);
		fight.addFighter(evander);

		Result result = new Result(Result.Assessment.Win, Result.Assessment.Loss);
		result.save();
		fight.assignResult(result);

		assertEquals(1, models.Fight.find.all().size());
		assertEquals(1,models.Result.find.all().size());

		Result r = models.Result.find.all().get(0);
		Fight f = models.Fight.find.all().get(0);

		assertNotNull(r);
		assertNotNull(f);
		assertNotNull(f.getResult());
		assertEquals(f.getResult(), r);

		assertEquals(Result.Assessment.Win,f.getResult().getFighterOneAssessment());
		assertEquals(Result.Assessment.Loss,f.getResult().getFighterTwoAssessment());
	}
}
