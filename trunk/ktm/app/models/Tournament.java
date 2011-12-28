package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Tournament extends Model {
	public String name;
	public Date date;
	
	public Tournament(String name, Date date) {
		this.name = name;
		this.date = date;
	}
}
