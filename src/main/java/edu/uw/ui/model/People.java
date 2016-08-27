package edu.uw.ui.model;

import java.util.List;

public class People {

	private Long personId;

	private String firstName;

	private String lastName;

	private List<String> affiliations;

	private String birthDate;

	private String lastEnrolledTerm;

	private int lastEnrolledYear;

	private String hireDate;

	private String separationDate;

	private Address address;

	public People() {
		// TODO Auto-generated constructor stub
	}

	public People(Long personId, String firstName, String lastName, List<String> affiliations, String birthDate,
			String lastEnrolledTerm, int lastEnrolledYear, String hireDate, String separationDate, Address address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.affiliations = affiliations;
		this.personId = personId;
		this.birthDate = birthDate;
		this.lastEnrolledTerm = lastEnrolledTerm;
		this.lastEnrolledYear = lastEnrolledYear;
		this.hireDate = hireDate;
		this.separationDate = separationDate;
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getAffiliations() {
		return affiliations;
	}

	public void setAffiliations(List<String> affiliations) {
		this.affiliations = affiliations;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getLastEnrolledTerm() {
		return lastEnrolledTerm;
	}

	public void setLastEnrolledTerm(String lastEnrolledTerm) {
		this.lastEnrolledTerm = lastEnrolledTerm;
	}

	public int getLastEnrolledYear() {
		return lastEnrolledYear;
	}

	public void setLastEnrolledYear(int lastEnrolledYear) {
		this.lastEnrolledYear = lastEnrolledYear;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getSeparationDate() {
		return separationDate;
	}

	public void setSeparationDate(String separationDate) {
		this.separationDate = separationDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
