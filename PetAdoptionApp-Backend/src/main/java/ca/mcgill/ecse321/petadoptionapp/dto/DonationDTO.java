package ca.mcgill.ecse321.petadoptionapp.dto;

public class DonationDTO {
	
	private Integer amount;
	private PetShelterDTO donatedTo;
	private RegularUserDTO donatedFrom;
	
	public DonationDTO() {
		
	}
	
	public DonationDTO(Integer amount, PetShelterDTO shelter, RegularUserDTO user) {
		this.amount = amount;
		this.donatedTo = shelter;
		this.donatedFrom = user;
	}
	
	public Integer getAmount() {
		return amount;
	}
	
	public PetShelterDTO getShelter() {
		return donatedTo;
	}
	
	public RegularUserDTO getUser() {
		return donatedFrom;
	}
	
	public void setShelter(PetShelterDTO shelter) {
		this.donatedTo = shelter;
	}
	
	public void setUser(RegularUserDTO user) {
		this.donatedFrom = user;
	}
}
