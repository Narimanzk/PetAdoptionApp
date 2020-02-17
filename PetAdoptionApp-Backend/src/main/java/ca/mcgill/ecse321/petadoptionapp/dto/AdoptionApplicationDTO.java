package ca.mcgill.ecse321.petadoptionapp.dto;

public class AdoptionApplicationDTO {
	private Integer id;
	private String applicationDescription;
	private GeneralUserDTO user;
	private PetProfileDTO petProfile;
	private ApplicationStatusDTO applicationStatus;

	public AdoptionApplicationDTO() {

	}

	public AdoptionApplicationDTO(int id, String description, GeneralUserDTO user, PetProfileDTO petProfile, ApplicationStatusDTO applicationStatus) {
		this.id = id;
		this.applicationDescription = description;
		this.user = user;
		this.petProfile = petProfile;
		this.applicationStatus = applicationStatus;
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

	public ApplicationStatusDTO getApplicationStatus() {
		return this.applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatusDTO applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
