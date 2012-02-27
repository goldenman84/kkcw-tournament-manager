package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Tournament extends Model {
	public String name;
	public Date date;

	public Tournament() {
		this.name = "Tournament";
		this.date = new Date();
	}
	
	
	public Tournament(String name, Date date) {
		this.name = name;
		this.date = date;
	}

	public Tournament(Long id, String name, Date date) {
		this.id = id;
		this.name = name;
		this.date = date;
	}
	
	public String getName() {
		return this.name;
	}

	public String setName(String name) {
		return this.name = name;
	}

	public Date getDate() {
		return this.date;
	}

	public Date setDate(Date date) {
		return this.date = date;
	}
	
	/**
	 * Merges a given Tournament instance to itself and saves the changes in DB.
	 * @param {models.Tournament} mt The Tournament to merge the properties from.
	 * @param {models.Tournament} The modified and persisted Tournament.
	 */
	public Tournament merge(models.Tournament mt) {
		Date dt = mt.getDate();
		String name = mt.getName();
		if (dt != null) {
			this.setDate(dt);
		}
		if (name != null) {
			this.setName(name);
		}
		this.save();
		
		return this;
	}
}
