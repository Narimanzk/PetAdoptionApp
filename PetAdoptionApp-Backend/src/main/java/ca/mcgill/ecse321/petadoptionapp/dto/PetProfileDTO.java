package ca.mcgill.ecse321.petadoptionapp.dto;

import java.util.List;

import ca.mcgill.ecse321.petadoptionapp.model.Gender;

public class PetProfileDTO {
	private Integer id;
	private String name;
	private String description;
	private int age;
	private Gender gender;
	private String reason;
	private byte[] profile_pic;
	private String species;
	private RegularUserDTO user;
	private List<AdoptionApplicationDTO> applications;
	
	/**
	 * call this constructor when needs to look up every pet profile
	 * @param name
	 * @param age
	 * @param petGender
	 * @param description
	 * @param species
	 * @param profile
	 * @param reason
	 * @param user
	 */
	public PetProfileDTO(Integer id, String name, int age, Gender petGender, String description, String species, 
			byte[] profile, String reason) {
		this.id = id;
		this.name = name;
		this.description  = description;
		this.age = age;
		this.gender = petGender;
		this.reason = reason;
		this.profile_pic = profile;
		this.species = species;
	}
	
	public Integer getId() {
		return this.id;
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
	
	public List<AdoptionApplicationDTO> getApplications(){
		return this.applications;
	}
	
	public void setApplications(List<AdoptionApplicationDTO> apps) {
		this.applications = apps;
	}
}
