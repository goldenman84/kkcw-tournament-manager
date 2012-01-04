import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.dialect.FirebirdDialect;
import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;
import controllers.*;	
	
public class TournamentSystemTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}
	
	
	@Test
	public void advanceRoundsDoubleElimination() {
		
		// Load tournament state data from YML
		Fixtures.loadModels("data.yml");
		
		DoubleElimination deSystem = new DoubleElimination();
		
		// get category object
		Category piccoloCat = Category.find("name", "Piccolo").first();
		assertEquals(Category.EliminationMode.Double, piccoloCat.mode);
		
		// get first round of the Piccolo category
		Round firstPiccoloRound = piccoloCat.rounds.get(0);
		assertEquals(2, firstPiccoloRound.brackets.size());
		
		// advance to round 2
		Round secondPiccoloRound = deSystem.advanceRound(firstPiccoloRound, piccoloCat);
		assertEquals(2, secondPiccoloRound.brackets.size());
		Bracket winnerBracket2 = secondPiccoloRound.brackets.get(0);
		Bracket loserBracket2 = secondPiccoloRound.brackets.get(1);		
		assertEquals(2,winnerBracket2.fights.size());
		assertEquals(2,loserBracket2.fights.size());
		
		// advance to round 3
		Round thirdPiccoloRound = deSystem.advanceRound(secondPiccoloRound, piccoloCat);
		assertEquals(2, thirdPiccoloRound.brackets.size());
		Bracket winnerBracket3 = thirdPiccoloRound.brackets.get(0);
		Bracket loserBracket3 = thirdPiccoloRound.brackets.get(1);			
		assertEquals(1,winnerBracket3.fights.size());
		assertEquals(2,loserBracket3.fights.size());
		
		// advance to round 4
		Round fourthPiccoloRound = deSystem.advanceRound(thirdPiccoloRound, piccoloCat);
		assertEquals(2, fourthPiccoloRound.brackets.size());
		Bracket winnerBracket4 = fourthPiccoloRound.brackets.get(0);
		Bracket loserBracket4 = fourthPiccoloRound.brackets.get(1);			
		assertEquals(0,winnerBracket4.fights.size());
		assertEquals(2,loserBracket4.fights.size());		
		
		// advance to round 5
		Round fifthPiccoloRound = deSystem.advanceRound(fourthPiccoloRound, piccoloCat);
		assertEquals(2, fifthPiccoloRound.brackets.size());
		Bracket winnerBracket5 = fifthPiccoloRound.brackets.get(0);
		Bracket loserBracket5 = fifthPiccoloRound.brackets.get(1);			
		assertEquals(0,winnerBracket5.fights.size());
		assertEquals(1,loserBracket5.fights.size());
		
		// advance to round 6 (final fight)
		Round sixthPiccoloRound = deSystem.advanceRound(fifthPiccoloRound, piccoloCat);
		assertEquals(2, sixthPiccoloRound.brackets.size());
		Bracket winnerBracket6 = sixthPiccoloRound.brackets.get(0);
		Bracket loserBracket6 = sixthPiccoloRound.brackets.get(1);			
		assertEquals(1,winnerBracket6.fights.size());
		assertEquals(0,loserBracket6.fights.size());
	}
	
}


