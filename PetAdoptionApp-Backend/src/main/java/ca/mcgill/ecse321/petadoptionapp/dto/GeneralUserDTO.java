package ca.mcgill.ecse321.petadoptionapp.dto;

import java.util.Set;

public class GeneralUserDTO {
	private String username;
	private String password;
	private String name;
	private byte[] profilePicture;
	private String email;
	private String phone;
	private AddressDTO address;
	private Set<ResponseDTO> responses;
	private Set<PetProfileDTO> petProfiles;
	private Set<AdoptionApplicationDTO> adoptionApplications;
	
	public GeneralUserDTO() {
		
	}
	
	public GeneralUserDTO(String username, String password, String name, byte[] profilePicture,
			String email, String phone) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.profilePicture = profilePicture;
		this.email = email;
		this.phone = phone;
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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public Set<ResponseDTO> getResponses() {
		return responses;
	}

	public void setResponses(Set<ResponseDTO> responses) {
		this.responses = responses;
	}

	public Set<PetProfileDTO> getPetProfiles() {
		return petProfiles;
	}

	public void setPetProfiles(Set<PetProfileDTO> petProfiles) {
		this.petProfiles = petProfiles;
	}

	public Set<AdoptionApplicationDTO> getAdoptionApplications() {
		return adoptionApplications;
	}

	public void setAdoptionApplications(Set<AdoptionApplicationDTO> adoptionApplications) {
		this.adoptionApplications = adoptionApplications;
	}
	
	
}
