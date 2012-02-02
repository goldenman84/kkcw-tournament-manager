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
	
	public Bracket addBracket(String name) {
		Bracket newBracket = new Bracket(this, name).save();
		this.brackets.add(newBracket);
		this.save();
		return this.brackets.get(this.brackets.size()-1);
	}
	
	public void clearResults() {
		for(Bracket br : brackets){
			br.clearResults();
		}			
	}
	
	@PreRemove
	public void PreRemove(){
		//brackets.clear();
		category.rounds.remove(this);
		
		for(Bracket bracket : brackets) {
			bracket.round = null;
		}
		brackets.clear();
		this.save();
	}
	
}
