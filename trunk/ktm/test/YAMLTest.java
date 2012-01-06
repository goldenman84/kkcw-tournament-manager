import org.hibernate.dialect.FirebirdDialect;
import org.junit.*;
import org.junit.experimental.categories.Categories;

import java.util.*;

import play.test.*;
import models.*;


public class YAMLTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		// Load tournament state data from YML
		Fixtures.loadModels("data.yml");
	}
	
	@Test
	public void loadAndVerifyYAMLData() {
		// Count things
		assertEquals(12,Fighter.count());
		assertEquals(6,Fight.count());
		assertEquals(4,Bracket.count());
		assertEquals(2,Round.count());
		assertEquals(2,Category.count());
		assertEquals(1,Tournament.count());
		
		// Get all rounds of the Piccolo category
		List<Round> piccoloRounds = Round.find("category.name", "Piccolo").fetch();		
		assertEquals(1,piccoloRounds.size());
		Round firstPiccoloRound = piccoloRounds.get(0);
		
		// Get brackets of the first Piccolo round
		List<Bracket> firstRoundBrackets = Bracket.find("byRound", firstPiccoloRound).fetch();
		assertEquals(2,firstRoundBrackets.size());
		assertEquals("Start Bracket",firstRoundBrackets.get(0).name);		
		
		// Get fights of the first piccolo bracket
		List<Fight> startFights = Fight.find("byBracket", firstRoundBrackets.get(0)).fetch();
		assertEquals(4,startFights.size());
		
		// Check Results of first fight
		Fight firstFight = startFights.get(0);
		Fight lastFight = startFights.get(startFights.size()-1);
		Result resultFirstFight = firstFight.result;
		Result resultLastFight = lastFight.result;
		assertEquals(Result.Assessment.None,resultFirstFight.fighterOneAssessment);
		assertEquals(Result.Assessment.None,resultFirstFight.fighterTwoAssessment);		
		
		// Get fighters of first and last fight
		List<Fighter> firstFighters = firstFight.fighters;
		List<Fighter> lastFighters = lastFight.fighters;
		
		assertEquals(2,firstFighters.size());
		assertEquals(2,lastFighters.size());
		assertEquals("bob",firstFighters.get(0).firstname);
		assertEquals("han",firstFighters.get(1).firstname);
		assertEquals("salma",lastFighters.get(0).firstname);
		assertEquals("ernesto",lastFighters.get(1).firstname);
		
		List<Category> categories = Category.findAll();
		assertEquals(2, categories.size());
		
		Category piccolo = categories.get(0);
		assertNotNull(piccolo);
		assertEquals(8, piccolo.fighters.size());
		
		Category medium = categories.get(1);
		assertNotNull(medium);
		assertEquals(4, medium.fighters.size());
	}
	
}
