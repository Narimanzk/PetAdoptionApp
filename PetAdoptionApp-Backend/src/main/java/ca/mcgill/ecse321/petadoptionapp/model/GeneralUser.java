package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class GeneralUser {
	
	@OneToOne
	private Address address;
	
	@ManyToMany(mappedBy = "user")
	private Set<AdoptionApplication> adoptionApplications;
	
	private Integer balance;

	@Column(columnDefinition = "TEXT")
	private String description;

	@OneToMany(mappedBy = "donatedTo")
	private Set<Donation> donationsAccepted;

	@OneToMany(mappedBy = "donatedFrom")
	private Set<Donation> donationsGiven;

	private String email;

	private String name;

	private String password;

	@OneToMany(mappedBy = "user")
	private Set<PetProfile> petProfiles;

	private String phone;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] profilePicture;

	@OneToMany(mappedBy = "user")
	private Set<Question> questions;

	@OneToMany(mappedBy = "user")
	private Set<Response> responses;

	@Id
	private String username;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "userType")
	private UserType userType;

	public Address getAddress() {
		return this.address;
	}

	public Set<AdoptionApplication> getAdoptionApplications() {
		return this.adoptionApplications;
	}

	public Integer getBalance() {
		return this.balance;
	}

	public String getDescription() {
		return this.description;
	}

	public Set<Donation> getDonationsAccepted() {
		return this.donationsAccepted;
	}

	public Set<Donation> getDonationsGiven() {
		return this.donationsGiven;
	}

	public String getEmail() {
		return this.email;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public Set<PetProfile> getPetProfiles() {
		return this.petProfiles;
	}

	public String getPhone() {
		return this.phone;
	}

	public byte[] getProfilePicture() {
		return this.profilePicture;
	}

	public Set<Question> getQuestions() {
		return this.questions;
	}

	public Set<Response> getResponses() {
		return this.responses;
	}

	public String getUsername() {
		return this.username;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setAdoptionApplications(Set<AdoptionApplication> adoptionApplicationss) {
		this.adoptionApplications = adoptionApplicationss;
	}

	public void setBalance(Integer value) {
		this.balance = value;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public void setDonationsAccepted(Set<Donation> donations) {
		this.donationsAccepted = donations;
	}

	public void setDonationsGiven(Set<Donation> donations) {
		this.donationsGiven = donations;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public void setPetProfiles(Set<PetProfile> petProfiless) {
		this.petProfiles = petProfiless;
	}

	public void setPhone(String value) {
		this.phone = value;
	}

	public void setProfilePicture(byte[] value) {
		this.profilePicture = value;
	}

	public void setQuestions(Set<Question> questionss) {
		this.questions = questionss;
	}

	public void setResponses(Set<Response> responsess) {
		this.responses = responsess;
	}

	public void setUsername(String value) {
		this.username = value;
	}

	public void setUserType(UserType value) {
		this.userType = value;
	}

}
