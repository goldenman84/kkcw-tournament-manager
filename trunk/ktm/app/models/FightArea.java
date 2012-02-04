package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import play.db.jpa.GenericModel;

@Entity
public class FightArea extends GenericModel {
	
	@Id
	public String name;
	
	@Id
	@GeneratedValue(generator="REGION_SEQ",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="REGION_SEQ", sequenceName="REGION_SEQ")
	@Column(name = "id", nullable = false)
	public Long id;
	
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
	
	public Long getId() {
		return this.id;
	}
	public Long setId(Long id) {
		return this.id = id;
	}
}
