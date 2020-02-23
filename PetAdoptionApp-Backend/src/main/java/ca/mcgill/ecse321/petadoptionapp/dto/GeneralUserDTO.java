package ca.mcgill.ecse321.petadoptionapp.dto;

import java.util.List;

public class GeneralUserDTO {
	private String username;
	private String password;
	private String name;
	private byte[] profilePicture;
	private String email;
	private String phone;
	private Integer balance;
	private String description;
	private AddressDTO address;
	private List<ResponseDTO> responses;
	private List<PetProfileDTO> petProfiles;
	private List<AdoptionApplicationDTO> adoptionApplications;
	private List<DonationDTO> donationAccepted;
	private List<DonationDTO> donationGiven;

	public GeneralUserDTO() {

	}

	public GeneralUserDTO(String username, String password, String name, byte[] profilePicture, String email,
			String phone, Integer balance) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.profilePicture = profilePicture;
		this.email = email;
		this.phone = phone;
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public List<ResponseDTO> getResponses() {
		return responses;
	}

	public void setResponses(List<ResponseDTO> responses) {
		this.responses = responses;
	}

	public List<PetProfileDTO> getPetProfiles() {
		return petProfiles;
	}

	public void setPetProfiles(List<PetProfileDTO> petProfiles) {
		this.petProfiles = petProfiles;
	}

	public List<AdoptionApplicationDTO> getAdoptionApplications() {
		return adoptionApplications;
	}

	public void setAdoptionApplications(List<AdoptionApplicationDTO> adoptionApplications) {
		this.adoptionApplications = adoptionApplications;
	}

	public List<DonationDTO> getDonationAccepted() {
		return donationAccepted;
	}

	public void setDonationAccepted(List<DonationDTO> donationAccepted) {
		this.donationAccepted = donationAccepted;
	}

	public List<DonationDTO> getDonationGiven() {
		return donationGiven;
	}

	public void setDonationGiven(List<DonationDTO> donationGiven) {
		this.donationGiven = donationGiven;
	}

}
