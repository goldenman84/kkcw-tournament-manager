package controllers;

import java.util.*;
import models.*;

public class DoubleElimination extends TournamentSystem {
	
	// advance one round in double elimination mode
	public Round advanceRound(Round prevRound, Category category){
		
		// add new round
		Round nextRound = category.addRound();
		
		// get number of brackets and fight for nextRound Winner Bracket		
		int numPrevWinFights = prevRound.brackets.get(0).fights.size();		
		int numNextWinFights = 0;
		if(numPrevWinFights > 1) {
			numNextWinFights = numPrevWinFights/2 + numPrevWinFights%2;	
		} 
		
		// get number of brackets and fight for nextRound Loser Bracket		
		int numPrevLosFights = prevRound.brackets.get(1).fights.size();
		int numNextLosFights = 0;		
		if((numPrevWinFights + numPrevLosFights) > 1){
			numNextLosFights = (numPrevWinFights + numPrevLosFights)/2 + (numPrevWinFights + numPrevLosFights)%2;		
		}
		
		// add new brackets				
		Bracket nextWinBracket = nextRound.addBracket("Winner Bracket");
		Bracket nextLosBracket = nextRound.addBracket("Loser Bracket");
		
		// check if we're in the last round and proceeding to finals
		if(numPrevWinFights == 0 && numPrevLosFights == 1){
			nextWinBracket.addFight();
		}
		// standard case
		else{
			// add empty fights to the winner bracket
			nextWinBracket.addFights(numNextWinFights);
			
			// add empty fights to the loser bracket
			nextLosBracket.addFights(numNextLosFights);
		}
		
		return nextRound;
	}
	
}
