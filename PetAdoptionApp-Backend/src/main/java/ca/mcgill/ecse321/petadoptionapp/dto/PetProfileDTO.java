package ca.mcgill.ecse321.petadoptionapp.dto;

import ca.mcgill.ecse321.petadoptionapp.model.Gender;///This should have it's own DTO object TODO

public class PetProfileDTO {
	private String name;
	private String description;
	private int age;
	private Gender gender;
	private String reason;
	private byte[] profile_pic;
	private String species;
	private RegularUserDTO user;
	
	public PetProfileDTO(String name, int age, Gender petGender, String description, String species, 
			byte[] profile, String reason) {
		this.name = name;
		this.description  = description;
		this.age = age;
		this.gender = petGender;
		this.reason = reason;
		this.profile_pic = profile;
		this.species = species;
	}
	
	public PetProfileDTO(String name, int age, Gender petGender, String description, String species, 
			byte[] profile, String reason, RegularUserDTO user) {
		this.name = name;
		this.description  = description;
		this.age = age;
		this.gender = petGender;
		this.reason = reason;
		this.profile_pic = profile;
		this.species = species;
		this.user = user;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Gender getGender() {
		return this.gender;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	public byte[] getProfilePic() {
		return this.profile_pic;
	}
	
	public String getSpecies() {
		return this.species;
	}
	
	public RegularUserDTO getUser() {
		return this.user;
	}
	
	public void setUser(RegularUserDTO user) {
		this.user = user;
	}
}
