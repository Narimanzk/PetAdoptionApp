package ca.mcgill.ecse321.petadoptionapp.dto;

public class DonationDTO {
	private int amount;
	private PetShelterDTO donatedTo;
	private RegularUserDTO donatedFrom;


	public DonationDTO() {

	}


	public DonationDTO(int amount) {
		super();
		this.amount = amount;
	}


	public DonationDTO(int amount, PetShelterDTO donatedTo, RegularUserDTO donatedFrom) {
		this.amount = amount;
		this.donatedTo = donatedTo;
		this.donatedFrom = donatedFrom;
	}



	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public PetShelterDTO getDonatedTo() {
		return donatedTo;
	}


	public void setDonatedTo(PetShelterDTO donatedTo) {
		this.donatedTo = donatedTo;
	}


	public RegularUserDTO getDonatedFrom() {
		return donatedFrom;
	}


	public void setDonatedFrom(RegularUserDTO donatedFrom) {
		this.donatedFrom = donatedFrom;
	}





}
