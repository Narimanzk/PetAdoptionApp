package ca.mcgill.ecse321.petadoptionapp.dto;

public class AdoptionApplicationDTO {
	private Integer id;
	private String applicationDescription;
	private ApplicationStatusDTO applicationStatus;
	private RegularUserDTO user;
	private PetProfileDTO petProfile;

	public AdoptionApplicationDTO() {

	}

	public AdoptionApplicationDTO(int id, String description, ApplicationStatusDTO applicationStatus) {
		this.id = id;
		this.applicationDescription = description;
		this.applicationStatus = applicationStatus;
	}
	
	public AdoptionApplicationDTO(int id, String description, ApplicationStatusDTO applicationStatus,
			RegularUserDTO user, PetProfileDTO petProfile) {
		this.id = id;
		this.applicationDescription = description;
		this.applicationStatus = applicationStatus;
		this.user = user;
		this.petProfile = petProfile;
	}

	public Integer getId() {
		return this.id;
	}

	public String getApplicationDescription() {
		return this.applicationDescription;
	}

	public RegularUserDTO getUser() {
		return this.user;
	}

	public void setUser(RegularUserDTO user) {
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
