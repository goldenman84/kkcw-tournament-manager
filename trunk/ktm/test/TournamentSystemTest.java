import java.rmi.activation.ActivationException;
import java.util.List;

import models.Bracket;
import models.Category;
import models.Fight;
import models.Fighter;
import models.Result;
import models.Round;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import controllers.DoubleElimination;
	
public class TournamentSystemTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		
		// Load tournament state data from YML
		Fixtures.loadModels("data.yml");
	}	
	
	@After
	public void teardown() {		
		Fixtures.deleteDatabase();
	}
	
	
	@Test
	public void initializeCategory() {
		
		// new tournament system object
		DoubleElimination deSystem = new DoubleElimination();
		
		// new category object
		Category piccoloCat = Category.find("name", "Piccolo").first();
		assertEquals(Category.EliminationMode.Double, piccoloCat.mode);
		assertEquals(9,piccoloCat.fighters.size());
		assertEquals(1,piccoloCat.rounds.size());
		assertEquals(2,piccoloCat.rounds.get(0).brackets.size());
		assertEquals(4,piccoloCat.rounds.get(0).brackets.get(0).fights.size());
		
			
//		piccoloCat.rounds.get(0).brackets.get(0).fights.get(0).delete();		
//		assertEquals(3,piccoloCat.rounds.get(0).brackets.get(0).fights.size());
//		
//		List<Fight> fights3 = Fight.find("byBracket", piccoloCat.rounds.get(0).brackets.get(0)).fetch();
//		assertEquals(3,fights3.size());
//		
//		piccoloCat.rounds.get(0).brackets.get(0).delete();
//		assertEquals(1,piccoloCat.rounds.get(0).brackets.size());
//		
//		List<Bracket> brackets1 = Bracket.find("byRound", piccoloCat.rounds.get(0)).fetch();
//		assertEquals(1,brackets1.size());
//		List<Fight> fightsall = Fight.findAll();
//		assertEquals(2,fightsall.size());
//		
//		piccoloCat.rounds.get(0).delete();
//		assertEquals(0,piccoloCat.rounds.size());
//		
//		List<Bracket> bracketsall = Bracket.findAll();
//		assertEquals(2,bracketsall.size());
		
		// try to initialize category, although already initialized -> exception!
		boolean check1 = false;
		try{
			deSystem.initializeCategory(piccoloCat);		
		} 
		catch(IllegalStateException e) {
			check1 = true;
		}
		assertTrue(check1);
		
		// remove existing rounds	
		piccoloCat.clearRounds();
		assertEquals(0,piccoloCat.rounds.size());
		List<Round> emptyRounds = Round.find("byCategory",piccoloCat).fetch();
		List<Fight> allFights = Fight.findAll();
		assertEquals(0,emptyRounds.size());
		assertEquals(2,allFights.size());
		assertEquals(9,piccoloCat.fighters.size());
		
		// initialize
		assertEquals(16,deSystem.getCeilPower2(9));

		boolean check2 = false;
		try{
			deSystem.initializeCategory(piccoloCat);		
		} 
		catch(IllegalStateException e) {
			check2 = true;
		}
		assertFalse(check2);
		
		
		// check initialization
		assertEquals(1,piccoloCat.rounds.size());
		assertEquals(2,piccoloCat.rounds.get(0).brackets.size());
		assertEquals(8,piccoloCat.rounds.get(0).brackets.get(0).fights.size());
		List<Round> initRounds = Round.find("byCategory",piccoloCat).fetch();
		List<Fight> initFights = Fight.find("byBracket",piccoloCat.rounds.get(0).brackets.get(0)).fetch();		
		assertEquals(1,initRounds.size());
		assertEquals(8,initFights.size());
		assertEquals(10,Fight.findAll().size());
		
		Bracket firstBracket = piccoloCat.rounds.get(0).brackets.get(0);
		assertEquals(8,firstBracket.fights.size());
		
		assertEquals(2,firstBracket.fights.get(0).fighters.size());
		for(int i=1;i<firstBracket.fights.size(); i++){
			assertEquals(1,firstBracket.fights.get(i).fighters.size());
		}
		
	}	
	
	
	@Test
	public void clearAllCategories() {
		
		assertEquals(6,Fight.findAll().size());
		assertEquals(13,Fighter.findAll().size());
		assertEquals(2,Round.findAll().size());
		assertEquals(4,Bracket.findAll().size());
		
//		Category piccoloCat = Category.find("name", "Piccolo").first();
//		assertEquals(Category.EliminationMode.Double, piccoloCat.mode);
//		assertEquals(9,piccoloCat.fighters.size());
//		assertEquals(1,piccoloCat.rounds.size());
//		assertEquals(2,piccoloCat.rounds.get(0).brackets.size());
//		assertEquals(4,piccoloCat.rounds.get(0).brackets.get(0).fights.size());
		
		//piccoloCat.clearRounds();
		
		List<Category> allCats = Category.all().fetch();		
		assertEquals(2,allCats.size());		
		//allCats.get(0).clearRounds();
		//allCats.
		//assertEquals(2,allCats.get(1).rounds.get(0).brackets.get(0).fights.size());
		
		//allCats.get(1).rounds.get(0).brackets.get(0).delete();
		//assertEquals(1,allCats.get(1).rounds.get(0).brackets.size());
		//allCats.get(1).rounds.get(0).delete();
		//allCats.get(1).save();
		//assertEquals(1,allCats.get(1).rounds.size());
		
		Category cat1 = allCats.get(1);
		//assertEquals(1, cat1.rounds.size());
		//assertEquals(2, cat1.rounds.get(0).brackets.get(0).fights.size());
		assertEquals(2, cat1.rounds.get(0).brackets.get(0).fights.get(0).fighters.size());
		//assertEquals(2, cat1.rounds.get(0).brackets.get(0).fights.get(1).fighters.size());
		
		cat1.clearRounds();
		//assertEquals(0, cat1.rounds.size());
		
		
		Category cat0 = allCats.get(0);
		assertEquals(1, cat0.rounds.size());
		assertEquals(4, cat0.rounds.get(0).brackets.get(0).fights.size());
		
		
		cat0.clearRounds();
		
		
		//allCats.get(0).clearRounds();
		//assertEquals(0,allCats.get(0).rounds.size());
//		assertEquals(0,allCats.get(0).rounds.size());
//		assertEquals(0,allCats.get(1).rounds.size());
		
		//piccoloCat.clearRounds();
		
//		for(Category cat : allCats){
//			cat.clearRounds();
//		}
	
		assertEquals(0,Fight.findAll().size());
		assertEquals(13,Fighter.findAll().size());
		assertEquals(0,Round.findAll().size());
		assertEquals(0,Bracket.findAll().size());	
	}
	
	
	@Test
	public void appendRoundsDoubleElimination() {		
		
		DoubleElimination deSystem = new DoubleElimination();
		
		// get category object
		Category piccoloCat = Category.find("name", "Piccolo").first();
		assertEquals(Category.EliminationMode.Double, piccoloCat.mode);
		
		// get first round of the Piccolo category
		Round firstPiccoloRound = piccoloCat.rounds.get(0);
		assertEquals(2, firstPiccoloRound.brackets.size());
		
		// append round 2
		Round secondPiccoloRound = deSystem.appendRound(firstPiccoloRound, piccoloCat);
		assertEquals(2, secondPiccoloRound.brackets.size());
		Bracket winnerBracket2 = secondPiccoloRound.brackets.get(0);
		Bracket loserBracket2 = secondPiccoloRound.brackets.get(1);		
		assertEquals(2,winnerBracket2.fights.size());
		assertEquals(2,loserBracket2.fights.size());
		
		// append round 3
		Round thirdPiccoloRound = deSystem.appendRound(secondPiccoloRound, piccoloCat);
		assertEquals(2, thirdPiccoloRound.brackets.size());
		Bracket winnerBracket3 = thirdPiccoloRound.brackets.get(0);
		Bracket loserBracket3 = thirdPiccoloRound.brackets.get(1);			
		assertEquals(1,winnerBracket3.fights.size());
		assertEquals(2,loserBracket3.fights.size());
		
		// append round 4
		Round fourthPiccoloRound = deSystem.appendRound(thirdPiccoloRound, piccoloCat);
		assertEquals(2, fourthPiccoloRound.brackets.size());
		Bracket winnerBracket4 = fourthPiccoloRound.brackets.get(0);
		Bracket loserBracket4 = fourthPiccoloRound.brackets.get(1);			
		assertEquals(0,winnerBracket4.fights.size());
		assertEquals(2,loserBracket4.fights.size());		
		
		// append round 5
		Round fifthPiccoloRound = deSystem.appendRound(fourthPiccoloRound, piccoloCat);
		assertEquals(2, fifthPiccoloRound.brackets.size());
		Bracket winnerBracket5 = fifthPiccoloRound.brackets.get(0);
		Bracket loserBracket5 = fifthPiccoloRound.brackets.get(1);			
		assertEquals(0,winnerBracket5.fights.size());
		assertEquals(1,loserBracket5.fights.size());
		
		// append round 6 (final fight)
		Round sixthPiccoloRound = deSystem.appendRound(fifthPiccoloRound, piccoloCat);
		assertEquals(2, sixthPiccoloRound.brackets.size());
		Bracket winnerBracket6 = sixthPiccoloRound.brackets.get(0);
		Bracket loserBracket6 = sixthPiccoloRound.brackets.get(1);			
		assertEquals(1,winnerBracket6.fights.size());
		assertEquals(0,loserBracket6.fights.size());
	}
	
	
	@Test
	public void assessRoundDoubleElimination() {
		
		DoubleElimination deSystem = new DoubleElimination();
		
		// get category object
		Category piccoloCat = Category.find("name", "Piccolo").first();
		assertEquals(Category.EliminationMode.Double, piccoloCat.mode);
		
		// get first round of the Piccolo category
		Round firstPiccoloRound = piccoloCat.rounds.get(0);
		assertEquals(2, firstPiccoloRound.brackets.size());
		assertEquals(4, firstPiccoloRound.brackets.get(0).fights.size());
		
		// append round 2
		Round secondPiccoloRound = deSystem.appendRound(firstPiccoloRound, piccoloCat);
		assertEquals(2, secondPiccoloRound.brackets.size());
		Bracket winnerBracket2 = secondPiccoloRound.brackets.get(0);
		Bracket loserBracket2 = secondPiccoloRound.brackets.get(1);		
		assertEquals(2,winnerBracket2.fights.size());
		assertEquals(2,loserBracket2.fights.size());
		
		// insert results
		setResultsDoubleElimination(firstPiccoloRound);
		
		// run round assessment
		deSystem.assessRound(firstPiccoloRound,secondPiccoloRound);
		
		//
		Bracket secondWinnerBracket = secondPiccoloRound.brackets.get(0);
		Bracket secondLoserBracket = secondPiccoloRound.brackets.get(1);
		assertEquals(2,secondPiccoloRound.brackets.size());		
		
		// Winner bracket check
		assertEquals(2,secondWinnerBracket.fights.size());
		//assertFalse(secondWinnerBracket.fights.get(0).result.fighterTwoAssessment == Result.Assessment.Bye);
		assertEquals(2,secondWinnerBracket.fights.get(0).fighters.size());
		assertEquals(1,secondWinnerBracket.fights.get(1).fighters.size());
		assertEquals("bob",secondWinnerBracket.fights.get(0).fighters.get(0).firstname);
		assertEquals("tom",secondWinnerBracket.fights.get(0).fighters.get(1).firstname);		
		assertEquals("salma",secondWinnerBracket.fights.get(1).fighters.get(0).firstname);
		
		// Loser bracket check
		assertEquals(2,secondLoserBracket.fights.size());
		assertEquals(1,secondLoserBracket.fights.get(0).fighters.size());
		assertEquals(0,secondLoserBracket.fights.get(1).fighters.size());
		assertEquals("han",secondLoserBracket.fights.get(0).fighters.get(0).firstname);
		assertEquals(Result.Assessment.Bye,secondLoserBracket.fights.get(0).result.fighterTwoAssessment);
		
	}

	
	public void setResultsDoubleElimination(Round firstRound) {
		
		// insert results for round 1
		Fight fight1 = firstRound.brackets.get(0).fights.get(0);
		Fight fight2 = firstRound.brackets.get(0).fights.get(1);
		Fight fight3 = firstRound.brackets.get(0).fights.get(2);
		Fight fight4 = firstRound.brackets.get(0).fights.get(3);
		
		fight1.state = Fight.State.Decided;
		fight1.result.fighterOneAssessment = Result.Assessment.Win;
		fight1.result.fighterTwoAssessment = Result.Assessment.Loss;		
		
		Result result2 = new Result().save();
		result2.fighterOneAssessment = Result.Assessment.Disqualification;
		result2.fighterTwoAssessment = Result.Assessment.Win;
		fight2.state = Fight.State.Decided;
		fight2.assignResult(result2);
		
		Result result3 = new Result().save();
		result3.fighterOneAssessment = Result.Assessment.Loss;
		result3.fighterTwoAssessment = Result.Assessment.Win;		
		fight3.assignResult(result3);
		assertEquals(Fight.State.Undecided,fight3.state);
		
		Result result4 = new Result().save();		
		result4.fighterOneAssessment = Result.Assessment.None;
		result4.fighterTwoAssessment = Result.Assessment.Bye;
		fight4.state = Fight.State.Decided;
		fight4.assignResult(result4);
		
		// manually check winners and losers
		// fight 1
		try {
			assertNotNull(fight1.getWinner());
			assertNotNull(fight1.getLoser());
			assertEquals("bob",fight1.getWinner().firstname);
			assertEquals("han",fight1.getLoser().firstname);
		} catch (ActivationException e) {
			assertTrue(false);	// no exception should occur here!
		}

		// fight 2
		try {
			assertNotNull(fight2.getWinner());
			assertNull(fight2.getLoser());
			assertEquals("tom",fight2.getWinner().firstname);
		} catch (ActivationException e) {
			assertTrue(false);
		}

		// fight 3
		boolean check = false;
		try {
			fight3.getWinner();
		} catch (ActivationException e) {
			check = true;
		}
		assertTrue(check);
		check = false;
		
		try {
			fight3.getLoser();
		} catch (ActivationException e) {
			check = true;
		}
		assertTrue(check);
		
		// fight 4
		try {
			assertNotNull(fight4.getWinner());
			assertNull(fight4.getLoser());
			assertEquals("salma",fight4.getWinner().firstname);
		} catch (ActivationException e) {
			assertTrue(false);	// no exception should occur here!
		}		
	}
	
}


