package controllers;

import models.*;

public abstract class TournamentSystem {
	
	// advance one round in the category
	public abstract Round appendRound(Round prevRound, Category category);
	
	// assess round and insert fighters into next round
	public abstract Round assessRound(Round prevRound, Round nextRound);
}
