package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Fighter extends Model {

	@Required
	public String firstname;
	
	@Required
	public String lastname;

	public int age;
	public int size;
	

	public Fighter(String firstname, String lastname, int age, int size) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.size = size;
	}

}
