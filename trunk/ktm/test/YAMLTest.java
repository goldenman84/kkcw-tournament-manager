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
		assertEquals(8,Fighter.count());
		assertEquals(6,Fight.count());
		assertEquals(4,Bracket.count());
		assertEquals(2,Round.count());
		assertEquals(2,Category.count());
		assertEquals(1,Tournament.count());
		
//		// Assert first fight / fighters
//		Fighter bobFighter = Fighter.all().first();
//		Fight bobFight = Fight.all().first();
//		assertEquals("bob",bobFighter.firstname);
//		assertEquals(2,bobFight.fighters.size());
//		assertEquals("bob",bobFight.fighters.get(0).firstname);
		
		// Get all rounds of the Piccolo category
		List<Round> piccoloRounds = Round.find("category.name", "Piccolo").fetch();		
		assertEquals(1,piccoloRounds.size());
		Round firstPiccoloRound = piccoloRounds.get(0);
		
		// Get brackets of the first Piccolo round
		List<Bracket> firstRoundBrackets = Bracket.find("byRound", firstPiccoloRound).fetch();
		assertEquals(2,firstRoundBrackets.size());
		assertEquals("Start Bracket",firstRoundBrackets.get(0).name);		
		
		// Get fights of the first bracket
		List<Fight> startFights = Fight.find("byBracket", firstRoundBrackets.get(0)).fetch();
		assertEquals(2,startFights.size());
		
		// Check Results of first fight
		Fight firstFight = startFights.get(0);
		Fight lastFight = startFights.get(startFights.size()-1);
		Result resultFirstFight = firstFight.result;
		Result resultLastFight = lastFight.result;
		assertEquals(Result.State.Undecided,resultFirstFight.fighterOneState);
		assertEquals(Result.State.Undecided,resultFirstFight.fighterTwoState);		
		
		// Get fighters of first and last fight
		List<Fighter> firstFighters = firstFight.fighters;
		List<Fighter> lastFighters = lastFight.fighters;
		
		assertEquals(2,firstFighters.size());
		assertEquals(2,lastFighters.size());
		assertEquals("bob",firstFighters.get(0).firstname);
		assertEquals("han",firstFighters.get(1).firstname);
		assertEquals("garten",lastFighters.get(0).firstname);
		assertEquals("tom",lastFighters.get(1).firstname);
		
		List<Category> categories = Category.findAll();
		assertEquals(2, categories.size());
		
		Category piccolo = categories.get(0);
		assertNotNull(piccolo);
		assertEquals(4, piccolo.fighters.size());
		
		Category medium = categories.get(1);
		assertNotNull(medium);
		assertEquals(4, medium.fighters.size());
	}
	
}
