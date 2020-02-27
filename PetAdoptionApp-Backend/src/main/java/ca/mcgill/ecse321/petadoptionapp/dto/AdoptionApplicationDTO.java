package ca.mcgill.ecse321.petadoptionapp.dto;

import ca.mcgill.ecse321.petadoptionapp.model.ApplicationStatus;

public class AdoptionApplicationDTO {
	private Integer id;
	private String applicationDescription;
	private ApplicationStatus applicationStatus;
	private GeneralUserDTO user;
	private PetProfileDTO petProfile;
	
	
	public AdoptionApplicationDTO() {
		
	}
	/**
	 * call this constructor when an owner of adopter wants to see all applications
	 * set reference later (depends on user's role)
	 * @param id
	 * @param description
	 * @param applicationStatus2
	 * @param user
	 */
	public AdoptionApplicationDTO(Integer id, String description, ApplicationStatus applicationStatus2) {
		this.id = id;
		this.applicationDescription = description;
		this.applicationStatus = applicationStatus2;
	}

	public Integer getId() {
		return this.id;
	}

	public String getApplicationDescription() {
		return this.applicationDescription;
	}

	public GeneralUserDTO getUser() {
		return this.user;
	}

	public void setUser(GeneralUserDTO user) {
		this.user = user;
	}

	public PetProfileDTO getPetProfile() {
		return this.petProfile;
	}

	public void setPetProfile(PetProfileDTO petProfile) {
		this.petProfile = petProfile;
	}

	public ApplicationStatus getApplicationStatus() {
		return this.applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
