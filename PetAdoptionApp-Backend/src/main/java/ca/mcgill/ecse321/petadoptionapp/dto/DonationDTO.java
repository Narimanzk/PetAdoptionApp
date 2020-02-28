package ca.mcgill.ecse321.petadoptionapp.dto;

public class DonationDTO {
	private Integer id;
	private int amount;
	private GeneralUserDTO donatedTo;
	private GeneralUserDTO donatedFrom;


	public DonationDTO() {

	}

	
	public DonationDTO(Integer id, int amount) {
		this.id = id;
		this.amount = amount;
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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
