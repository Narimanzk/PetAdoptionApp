package ca.mcgill.ecse321.petadoptionapp.dto;

public class SigninDTO {
	private String password;
	private String username;
	
	public SigninDTO(){
		
	}
	
	public SigninDTO(String username, String password) {
		this.password = password;
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUsername() {
		return this.username;
	}
}
