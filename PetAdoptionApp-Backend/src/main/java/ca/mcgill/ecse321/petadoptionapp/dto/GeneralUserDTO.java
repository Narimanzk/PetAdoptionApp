package ca.mcgill.ecse321.petadoptionapp.dto;

import java.util.List;

public class GeneralUserDTO {
	private AddressDTO address;
	private List<AdoptionApplicationDTO> adoptionApplications;
	private Integer balance;
	private String description;
	private List<DonationDTO> donationAccepted;
	private List<DonationDTO> donationGiven;
	private String email;
	private String name;
	private String password;
	private List<PetProfileDTO> petProfiles;
	private String phone;
	private byte[] profilePicture;
	private List<QuestionDTO> questions;
	private List<ResponseDTO> responses;
	private String username;
	private String userType;
	
	public GeneralUserDTO() {
		
	}
	
	/**
	 * Essential Constructor.
	 * @param username
	 * @param userType
	 * @param email
	 * @param password
	 * @param name
	 */
	public GeneralUserDTO(String username, String userType, String email, String password, String name) {
		this.username=username;
		this.userType= userType;
		this.email = email;
		this.password = password;
		this.name = name;
	}
	/**
	 * Full Constructor for attribute only.
	 * 
	 * @param username
	 * @param userType
	 * @param email
	 * @param password
	 * @param name
	 * @param profilePicture
	 * @param phone
	 * @param balance
	 * @param description
	 */
	public GeneralUserDTO(String username, String userType, String email, String password, String name,
			byte[] profilePicture, String phone, Integer balance, String description) {
		this.username = username;
		this.userType = userType;
		this.password = password;
		this.name = name;
		this.profilePicture = profilePicture;
		this.email = email;
		this.phone = phone;
		this.balance = balance;
		this.description = description;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public List<AdoptionApplicationDTO> getAdoptionApplications() {
		return adoptionApplications;
	}

	public Integer getBalance() {
		return balance;
	}

	public String getDescription() {
		return description;
	}

	public List<DonationDTO> getDonationAccepted() {
		return donationAccepted;
	}

	public List<DonationDTO> getDonationGiven() {
		return donationGiven;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public List<PetProfileDTO> getPetProfiles() {
		return petProfiles;
	}

	public String getPhone() {
		return phone;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public List<ResponseDTO> getResponses() {
		return responses;
	}

	public String getUsername() {
		return username;
	}

	public String getUserType() {
		return userType;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public void setAdoptionApplications(List<AdoptionApplicationDTO> adoptionApplications) {
		this.adoptionApplications = adoptionApplications;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDonationAccepted(List<DonationDTO> donationAccepted) {
		this.donationAccepted = donationAccepted;
	}

	public void setDonationGiven(List<DonationDTO> donationGiven) {
		this.donationGiven = donationGiven;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPetProfiles(List<PetProfileDTO> petProfiles) {
		this.petProfiles = petProfiles;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	public void setResponses(List<ResponseDTO> responses) {
		this.responses = responses;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
