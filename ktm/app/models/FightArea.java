package models;

import javax.persistence.*;
import com.avaje.ebean.*;

import play.data.validation.*;
import play.db.ebean.Model;

@Entity
public class FightArea extends Model {
	
	@Id
	Long id;
	
	@Constraints.Required
	public String name;
	
	public FightArea() {
		this.name = "Unknown";
	}
	
	public FightArea(String name) {
		this.name = name;
	}
	
    // ebean finder class
    public static Finder<Long,FightArea> find = new Finder<Long,FightArea>(
            Long.class, FightArea.class
            ); 
	
	
	public String getName() {
		return this.name;
	}
	
	public String setName(String name) {
		this.name = name;
		return name;
	}
	
	/**
	 * Merges a given FightArea instance to itself and saves the changes in DB.
	 * 
	 * @param {models.FightArea} fightarea The FightArea to merge the properties from.
	 * @param {models.FightArea} The modified and persisted FightArea.
	 */
	public FightArea merge(FightArea fightarea) {
		String name = fightarea.getName();
		
		if (name != null) {
			this.setName(name);
		}

		this.save();
		return this;
	}
}
