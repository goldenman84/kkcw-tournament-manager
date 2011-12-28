package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Bracket extends Model {
	
	public String name;
	
	@ManyToOne
	public Round round;
	
	public Bracket(Round round, String name) {
		this.round = round;
		this.name = name;
	}
}
