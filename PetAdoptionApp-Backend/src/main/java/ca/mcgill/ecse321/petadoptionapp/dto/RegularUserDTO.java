package ca.mcgill.ecse321.petadoptionapp.dto;

public class RegularUserDTO {
  private String personalDescription;
  private String name;
  private String phone;
  private String email;
  private String username;
  private String password;
  private byte[] profilePicture;

	public String getPersonalDescription() {
		return this.personalDescription;
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

  public RegularUserDTO(){
    
  }
  public RegularUserDTO(String username,String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
  }
  public RegularUserDTO(String username,String email, String password,String phone, String personalDescription, byte[] profilePicture){
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.personalDescription = personalDescription;
    this.profilePicture = profilePicture;
  }
}
