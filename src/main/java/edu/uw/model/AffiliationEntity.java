package edu.uw.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "affiliaton")
public class AffiliationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PERSON_ID")
	private PersonEntity person;

	private String name;

	public AffiliationEntity() {
		// TODO Auto-generated constructor stub
	}

	public AffiliationEntity(String name,PersonEntity person) {
		super();
		this.name = name;
		this.person = person;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonEntity getPerson() {
		return person;
	}

	public void setPerson(PersonEntity person) {
		this.person = person;
	}

}
