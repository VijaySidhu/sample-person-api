package edu.uw.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "address")
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String line1;

	@Column(nullable = true)
	private String line2;

	private String city;

	private String state;

	private int zip;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "address", cascade = CascadeType.ALL)
	@JoinColumn(name = "PERSON_ID")
	private PersonEntity person;

	public AddressEntity() {
		// TODO Auto-generated constructor stub
	}

	public AddressEntity(String line1, String line2, String city, String state, int zip) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public PersonEntity getPerson() {
		return person;
	}

	public void setPerson(PersonEntity person) {
		this.person = person;
	}

}
