package ca.mcgill.ecse321.petadoptionapp.dto;

public class PetShelterDTO {
	private String username;
	private String password;
	private String name;
	private byte[] profilePicture;
	private String email;
	private String phone;
	private Integer balance;
	
	public PetShelterDTO() {
		
	}
	
	public PetShelterDTO(String username, String password, String name, String email,
			Integer balance) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
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
}
