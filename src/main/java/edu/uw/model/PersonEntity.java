package edu.uw.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity(name = "person")
public class PersonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long personId;

	private String firstName;

	private String lastName;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	private Set<AffiliationEntity> affiliatons;

	private Date birthDate;

	@Column(nullable = true)
	private String lastEnrolledTerm;

	@Column(nullable = true)
	private int lastEnrolledYear;

	@Column(nullable = true)
	private Date hireDate;

	@Column(nullable = true)
	private Date separationDate;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID")
	private AddressEntity address;

	public PersonEntity() {
	}

	public PersonEntity(Long personId, String firstName, String lastName, Date birthDate, String lastEnrolledTerm,
			int lastEnrolledYear, Date hireDate, Date separationDate) {
		super();
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.lastEnrolledTerm = lastEnrolledTerm;
		this.lastEnrolledYear = lastEnrolledYear;
		this.hireDate = hireDate;
		this.separationDate = separationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<AffiliationEntity> getAffiliatons() {
		return affiliatons;
	}

	public void setAffiliatons(Set<AffiliationEntity> affiliatons) {
		this.affiliatons = affiliatons;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getSeparationDate() {
		return separationDate;
	}

	public void setSeparationDate(Date separationDate) {
		this.separationDate = separationDate;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

}
