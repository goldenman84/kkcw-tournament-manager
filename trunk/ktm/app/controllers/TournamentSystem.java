package controllers;

import models.*;

public abstract class TournamentSystem {
	
	// advance one round in the category
	public abstract Round advanceRound(Round prevRound, Category category);

}
