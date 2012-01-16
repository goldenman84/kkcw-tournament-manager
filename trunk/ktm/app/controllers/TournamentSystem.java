package controllers;

import models.*;
import java.math.*;

public abstract class TournamentSystem {
	
	// advance one round in the category
	public abstract Round appendRound(Round prevRound, Category category);
	
	// assess round and insert fighters into next round
	public abstract Round assessRound(Round prevRound, Round nextRound);
	
	// return number of brackets
	public abstract int getNumBrackets();
	
	// return the next bigger power of 2
	// to a maximum of 1024
	public int getCeilPower2(int z) {
		
		if(z <= 0) return 0;
		
		int result=0;
		for(int i=0; i<10 ; i++ ) {
			if(Math.pow(2,i) >= z){
				result = (int)Math.pow(2,i);
				break;
			}
		}		
		return result;
	}
	
	// initialize a category - assign fighters to start fights
	public Category initializeCategory(Category category) {
		
		// clear category?
		// TODO
		
		// insert first round
		Round firstRound = category.addRound();
		
		// insert brackets
		for(int i=0; i<getNumBrackets(); i++) {
			String bName = "Bracket " + i;
			firstRound.addBracket(bName);
		}
		
		// determine number of fights in start bracket
		int numFighters = category.fighters.size();
		int numStartFights = getCeilPower2(numFighters)/2;
		Bracket startBracket = firstRound.brackets.get(0);

		// create fights in start bracket
		startBracket.addFights(numStartFights);
	
		// assign fighters to fights
		for(int i=0; i<numFighters; i++){
			startBracket.fights.get(i%numStartFights).addFighter(category.fighters.get(i));
		}		
		
		return category;
	}	
}
