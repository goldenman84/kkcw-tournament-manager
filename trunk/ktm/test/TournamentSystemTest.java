import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.dialect.FirebirdDialect;
import org.junit.*;

import java.rmi.activation.ActivationException;
import java.util.*;

import play.test.*;
import models.*;
import controllers.*;	
	
public class TournamentSystemTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		
		// Load tournament state data from YML
		Fixtures.loadModels("data.yml");
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
		assertFalse(secondWinnerBracket.fights.get(0).result.fighterTwoAssessment == Result.Assessment.Bye);
		assertEquals(2,secondWinnerBracket.fights.get(0).fighters.size());
		assertEquals(1,secondWinnerBracket.fights.get(1).fighters.size());
		assertEquals("bob",secondWinnerBracket.fights.get(0).fighters.get(0).firstname);
		assertEquals("tom",secondWinnerBracket.fights.get(0).fighters.get(1).firstname);		
		assertEquals("salma",secondWinnerBracket.fights.get(1).fighters.get(0).firstname);
		
		// Loser bracket check
		assertEquals(2,secondLoserBracket.fights.size());
		assertEquals(1,secondLoserBracket.fights.get(0).fighters.size());
		assertEquals(0,secondLoserBracket.fights.get(0).fighters.size());
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
		fight2.setResult(result2);
		
		Result result3 = new Result().save();
		result3.fighterOneAssessment = Result.Assessment.Loss;
		result3.fighterTwoAssessment = Result.Assessment.Win;		
		fight3.setResult(result3);
		assertEquals(Fight.State.Undecided,fight3.state);
		
		Result result4 = new Result().save();		
		result4.fighterOneAssessment = Result.Assessment.None;
		result4.fighterTwoAssessment = Result.Assessment.Bye;
		fight4.state = Fight.State.Decided;
		fight4.setResult(result4);
		
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


