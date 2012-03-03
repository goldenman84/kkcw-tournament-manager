package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Fighter extends Model {

	@Required
	public String firstname;

	@Required
	public String lastname;

	public int age;
	public int size;

	@ManyToOne
	public Category category;

	public Fighter(String firstname, String lastname, int age, int size) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.size = size;
	}

	public Fighter(String firstname, String lastname, int age, int size, Category category) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.size = size;
		this.category = category;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String setFirstname(String fname) {
		return (this.firstname = fname);
	}

	public String getLastname() {
		return this.lastname;
	}

	public String setLastname(String lname) {
		return (this.lastname = lname);
	}

	public int getAge() {
		return this.age;
	}

	public int setAge(int age) {
		return (this.age = age);
	}

	public int getSize() {
		return this.size;
	}

	public int setSize(int size) {
		return (this.size = size);
	}

	/**
	 * Merges a given Fighter instance to itself and saves the changes in DB.
	 * 
	 * @param {models.Fighter} rd The Fighter to merge the properties from.
	 * @param {models.Fighter} The modified and persisted Fighter.
	 */
	public Fighter merge(Fighter f) {
		String firstname = f.getFirstname();
		String lastname = f.getLastname();
		int age = f.getAge();
		int size = f.getSize();

		// TODO: do a more sophisticated user value validations...
		if (firstname != "") {
			this.setFirstname(firstname);
		}
		if (lastname != "") {
			this.setLastname(lastname);
		}
		if (age > 0) {
			this.setAge(age);
		}
		if (size > 0) {
			this.setSize(size);
		}

		this.save();
		return this;
	}
}
