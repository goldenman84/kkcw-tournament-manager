package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class FightArea extends Model {
	
	@Required
	public String name;
	
	public FightArea() {
		this.name = "Unknown";
	}
	
	public FightArea(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String setName(String name) {
		this.name = name;
		return name;
	}
	
/*	public Long getId() {
		return this.id;
	}
	public Long setId(Long id) {
		return this.id = id;
	}*/
}
