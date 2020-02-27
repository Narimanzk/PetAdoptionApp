package ca.mcgill.ecse321.petadoptionapp.dto;

public class DonationDTO {
	private int amount;
	private GeneralUserDTO donatedTo;
	private GeneralUserDTO donatedFrom;


	public DonationDTO() {

	}

	
	public DonationDTO(int amount) {
		this.amount = amount;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public GeneralUserDTO getDonatedTo() {
		return donatedTo;
	}


	public void setDonatedTo(GeneralUserDTO donatedTo) {
		this.donatedTo = donatedTo;
	}


	public GeneralUserDTO getDonatedFrom() {
		return donatedFrom;
	}


	public void setDonatedFrom(GeneralUserDTO donatedFrom) {
		this.donatedFrom = donatedFrom;
	}





}
