package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Round extends Model {
	
	@ManyToOne
	public Category category;
	
	public Round(Category category) {
		this.category = category;
	}
}
