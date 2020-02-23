package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.Lob;
import javax.persistence.DiscriminatorType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class GeneralUser {
	private String name;

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	private String phone;

	public void setPhone(String value) {
		this.phone = value;
	}

	public String getPhone() {
		return this.phone;
	}

	private String email;

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}

	private Set<Response> responses;

	@OneToMany(mappedBy = "user")
	public Set<Response> getResponses() {
		return this.responses;
	}

	public void setResponses(Set<Response> responsess) {
		this.responses = responsess;
	}

	private Address address;

	@OneToOne
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	private Set<PetProfile> petProfiles;

	@OneToMany(mappedBy = "user")
	public Set<PetProfile> getPetProfiles() {
		return this.petProfiles;
	}

	public void setPetProfiles(Set<PetProfile> petProfiless) {
		this.petProfiles = petProfiless;
	}

	private Set<AdoptionApplication> adoptionApplications;

	@ManyToMany(mappedBy = "user")
	public Set<AdoptionApplication> getAdoptionApplications() {
		return this.adoptionApplications;
	}

	public void setAdoptionApplications(Set<AdoptionApplication> adoptionApplicationss) {
		this.adoptionApplications = adoptionApplicationss;
	}

  private String username;

	public void setUsername(String value) {
		this.username = value;
	}

	@Id
	public String getUsername() {
		return this.username;
	}

	private String password;

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] profilePicture;

	public void setProfilePicture(byte[] value) {
		this.profilePicture = value;
	}

	public byte[] getProfilePicture() {
		return this.profilePicture;
	}
	
	private String personalDescription;

	public void setPersonalDescription(String value) {
		this.personalDescription = value;
	}

	@Column(columnDefinition = "TEXT")
	public String getPersonalDescription() {
		return this.personalDescription;
	}

	private Set<Donation> donationsGiven;

	@OneToMany(mappedBy = "donatedFrom")
	public Set<Donation> getDonationsGiven() {
		return this.donationsGiven;
	}

	public void setDonationsGiven(Set<Donation> donations) {
		this.donationsGiven = donations;
	}

	private Set<Donation> donationsAccepted;

	@OneToMany(mappedBy = "donatedTo")
	public Set<Donation> getDonationsAccepted() {
		return this.donationsAccepted;
	}

	public void setDonationsAccepted(Set<Donation> donations) {
		this.donationsAccepted = donations;
	}
	
	private Set<Question> questions;

	@OneToMany(mappedBy = "user")
	public Set<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set<Question> questionss) {
		this.questions = questionss;
	}
	private Integer balance;

	public void setBalance(Integer value) {
		this.balance = value;
	}

	public Integer getBalance() {
		return this.balance;
	}

}
