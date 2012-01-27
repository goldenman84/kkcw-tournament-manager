package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import controllers.rest.factories.DateFactory;
import controllers.rest.factories.NumberFactory;
import flexjson.JSONDeserializer;

import java.util.*;

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
}
