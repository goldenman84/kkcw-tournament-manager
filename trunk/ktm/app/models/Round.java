package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Round extends Model {
	
	@ManyToOne
	public Category category;
	
	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
	public List<Bracket> brackets;
	
	public Round(Category category) {
		this.brackets = new ArrayList<Bracket>();
		this.category = category;
	}
	
	public Round addBracket(String name) {
		Bracket newBracket = new Bracket(this, name);
		this.brackets.add(newBracket);
		this.save();
		return this;
	}
}
