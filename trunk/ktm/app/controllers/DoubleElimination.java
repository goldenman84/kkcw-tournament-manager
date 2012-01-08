package controllers;

import java.rmi.activation.ActivationException;
import java.util.*;
import models.*;

public class DoubleElimination extends TournamentSystem {
	
	// advance one round in double elimination mode
	public Round appendRound(Round prevRound, Category category){
		
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

	
	/**
	 * @Precondition: nextRound is built correctly from 'prevRound', with appendRound()
	 */
	public Round assessRound(Round prevRound, Round nextRound){

		// bracket references
		Bracket prevWinnerBracket = prevRound.brackets.get(0);
		Bracket nextWinnerBracket = nextRound.brackets.get(0);
		Bracket prevLoserBracket = prevRound.brackets.get(1);
		Bracket nextLoserBracket = nextRound.brackets.get(1);
		int numPrevWinFights = prevWinnerBracket.fights.size();

		// previous winner bracket -> next winner, next loser Bracket
		for(int i=0; i<prevWinnerBracket.fights.size(); i++){
			
			Fight prevWinFight = prevWinnerBracket.fights.get(i);
			Fight nextWinFight = nextWinnerBracket.fights.get(i/2);
			Fight nextLosFight = nextLoserBracket.fights.get(i/2);
			
			if(prevWinFight.equals(Fight.State.Decided)) {

				try {
					Fighter prevWinner = prevWinFight.getWinner();	// winner	

					if(!prevWinner.equals(null)){
						nextWinFight.addFighter(prevWinner);
					}
					else { // no fighter -> bye
						nextWinFight.setBye();
					}

					Fighter prevLoser = prevWinFight.getLoser(); // loser
					if(!prevWinner.equals(null)){
						nextLosFight.addFighter(prevLoser);
					}
					else {	// no fighter -> bye
						nextLosFight.setBye();
					}

				} catch (ActivationException e) {
					
				}
			}
		}		
		
		// previous loser Bracket -> next loser bracket
		for(int i=0; i<prevLoserBracket.fights.size(); i++){
			
			Fight prevLosFight = prevLoserBracket.fights.get(i);
			Fight nextLosFight = nextLoserBracket.fights.get((i+numPrevWinFights)/2);
			
			if(prevLosFight.equals(Fight.State.Decided)){

				try {
					Fighter prevWinner = prevLosFight.getWinner();

					if(!prevWinner.equals(null)){
						nextLosFight.addFighter(prevWinner);
					}
					else { // no fighter -> bye
						nextLosFight.setBye();
					}
				} catch (ActivationException e) {

				}				
			}
		}
		
		
		return nextRound;
	}
	
}
