package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class PetProfile {
	private String petName;

	public void setPetName(String value) {
		this.petName = value;
	}

	public String getPetName() {
		return this.petName;
	}

	private String reason;

	public void setReason(String value) {
		this.reason = value;
	}

	@Column(columnDefinition = "TEXT")
	public String getReason() {
		return this.reason;
	}

	private Integer age;

	public void setAge(Integer value) {
		this.age = value;
	}

	public Integer getAge() {
		return this.age;
	}

	private GeneralUser user;

	@ManyToOne(optional = false)
	public GeneralUser getUser() {
		return this.user;
	}

	public void setUser(GeneralUser user) {
		this.user = user;
	}

	private Set<AdoptionApplication> adoptionApplications;

	@OneToMany(mappedBy = "petProfile")
	public Set<AdoptionApplication> getAdoptionApplications() {
		return this.adoptionApplications;
	}

	public void setAdoptionApplications(Set<AdoptionApplication> adoptionApplicationss) {
		this.adoptionApplications = adoptionApplicationss;
	}

	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	private String petSpecies;

	public void setPetSpecies(String value) {
		this.petSpecies = value;
	}

	public String getPetSpecies() {
		return this.petSpecies;
	}
	
	private String description;
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String value) {
		this.description = value;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "gender")
	private Gender petGender;

	public void setPetGender(Gender value) {
		this.petGender = value;
	}

	public Gender getPetGender() {
		return this.petGender;
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
}
