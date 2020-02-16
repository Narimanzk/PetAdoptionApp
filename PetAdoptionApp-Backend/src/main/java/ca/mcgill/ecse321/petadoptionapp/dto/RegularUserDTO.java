package ca.mcgill.ecse321.petadoptionapp.dto;

import java.util.List;

public class RegularUserDTO {
	private String personalDescription;
	private String name;
	private String phone;
	private String email;
	private String username;
	private String password;
	private byte[] profilePicture;
	private List<PetProfileDTO> petProfiles;
	private List<AdoptionApplicationDTO> adoptionApplications;
	private List<ResponseDTO> responses;
	private AddressDTO address;
	private List<DonationDTO> donations;
	private List<QuestionDTO> questions;

	public RegularUserDTO() {

	}

	public RegularUserDTO(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public RegularUserDTO(String username, String email, String password, String phone, String personalDescription,
			byte[] profilePicture, List<AdoptionApplicationDTO> adoptionApplications, List<PetProfileDTO> petProfiles,
			AddressDTO address, List<ResponseDTO> responses,List<DonationDTO> donations,List<QuestionDTO> questions) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.personalDescription = personalDescription;
		this.profilePicture = profilePicture;
		this.adoptionApplications = adoptionApplications;
		this.petProfiles = petProfiles;
		this.address = address;
		this.responses = responses;
		this.donations = donations;
		this.questions = questions;
	}

	public String getName() {
		return this.name;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getEmail() {
		return this.email;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public byte[] getProfilePicture() {
		return this.profilePicture;
	}

	public List<PetProfileDTO> getPetProfiles() {
		return this.petProfiles;
	}

	public void setPetProfiles(List<PetProfileDTO> petProfiles) {
		this.petProfiles = petProfiles;
	}

	public List<AdoptionApplicationDTO> getAdoptionApplications() {
		return this.adoptionApplications;
	}

	public void setAdoptionApplications(List<AdoptionApplicationDTO> adoptionApplications) {
		this.adoptionApplications = adoptionApplications;
	}

	public List<ResponseDTO> getResponses() {
		return this.responses;
	}

	public void setResponses(List<ResponseDTO> responses) {
		this.responses = responses;
	}

	public AddressDTO getAddress() {
		return this.address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public List<DonationDTO> getDonations() {
		return this.donations;
	}

	public void setDonations(List<DonationDTO> donations) {
		this.donations = donations;
	}

	public List<QuestionDTO> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	public String getPersonalDescription() {
		return this.personalDescription;
	}
}
