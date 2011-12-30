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
	public void loadAndVerifyYAMLData() {
		
		// Load tournament state data from YML
		Fixtures.loadModels("data.yml");
		
		// Count things
		assertEquals(8,Fighter.count());
		assertEquals(4,Fight.count());
		assertEquals(4,Bracket.count());
		assertEquals(3,Round.count());
		assertEquals(2,Category.count());
		assertEquals(1,Tournament.count());
		
		// Assert first fight / fighters
		Fighter bobFighter = Fighter.all().first();
		Fight bobFight = Fight.all().first();
		assertEquals("bob",bobFighter.firstname);
		assertEquals(2,bobFight.fighters.size());
		assertEquals("bob",bobFight.fighters.get(0).firstname);
		
		// Get all rounds of the Piccolo category
		List<Round> piccoloRounds = Round.find("category.name", "Piccolo").fetch();		
		assertEquals(2,piccoloRounds.size());
		Round firstPiccoloRound = piccoloRounds.get(0);
		
		// Get brackets of the first Piccolo round
		List<Bracket> firstRoundBrackets = Bracket.find("byRound", firstPiccoloRound).fetch();
		assertEquals(1,firstRoundBrackets.size());
		assertEquals("Start Bracket",firstRoundBrackets.get(0).name);		
		
		// Get fights of the first bracket
		List<Fight> firstFights = Fight.find("byBracket", firstRoundBrackets.get(0)).fetch();
		assertEquals(4,firstFights.size());
		
		// Get fighters of first and last fight
		List<Fighter> firstFighters = firstFights.get(0).fighters;
		List<Fighter> lastFighters = firstFights.get(firstFights.size()-1).fighters;
		
		assertEquals(2,firstFighters.size());
		assertEquals(2,lastFighters.size());
		assertEquals("bob",firstFighters.get(0).firstname);
		assertEquals("han",firstFighters.get(1).firstname);
		assertEquals("bill",lastFighters.get(0).firstname);
		assertEquals("alice",lastFighters.get(1).firstname);		
	}
	
}
