package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Category extends Model {
	public String name;
	
	public static enum EliminationMode {
		Single, Double
	}
	public EliminationMode mode;

	
	@ManyToOne
	public Tournament tournament;
	
	public Category(Tournament tournament, String name, EliminationMode mode) {
		this.tournament = tournament;
		this.name = name;
		this.mode = mode;
	}
}
