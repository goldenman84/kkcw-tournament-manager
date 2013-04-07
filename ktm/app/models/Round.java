package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import com.avaje.ebean.*;

import play.db.ebean.Model;

@Entity
public class Round extends Model {
	
    @Id
    public Long id;

	@ManyToOne
	public Category category;
	
	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
	public List<Bracket> brackets;
	
	public Round(Category category) {
		this.brackets = new ArrayList<Bracket>();
		this.category = category;
	}
	
    // ebean finder class
    public static Finder<Long,Round> find = new Finder<Long,Round>(
            Long.class, Round.class
            );
	
	public Category setCategory(Category cat) {
		return (this.category = cat);
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public List<Bracket> getBrackets() {
		return this.brackets;
	}
	
	public Bracket addBracket(String name) {
		Bracket newBracket = new Bracket(this, name);
    newBracket.save();
		this.brackets.add(newBracket);
		this.save();
		return this.brackets.get(this.brackets.size()-1);
	}
	
	public void clearResults() {
		for(Bracket br : brackets){
			br.clearResults();
		}			
	}
	
	/**
	 * Merges a given Round instance to itself and saves the changes in DB.
	 * @param {models.Round} rd The Round to merge the properties from.
	 * @param {models.Round} The modified and persisted Round.
	 */
	public Round merge(Round rd) {
		Category cat     = rd.getCategory();
		
		if (cat != null) { this.setCategory(cat); }
		
		this.save();
		return this;
	}
}
