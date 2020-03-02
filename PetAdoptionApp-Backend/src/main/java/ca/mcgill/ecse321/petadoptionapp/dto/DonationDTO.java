package ca.mcgill.ecse321.petadoptionapp.dto;

public class DonationDTO {
	private Integer id;
	private int amount;
	private GeneralUserDTO donatedTo;
	private GeneralUserDTO donatedFrom;


	/**
	 * empty donationDTO constructor
	 */
	public DonationDTO() {

	}

	
	/**
	 * donationDTO constructor
	 * @param id
	 * @param amount
	 */
	public DonationDTO(Integer id, int amount) {
		this.id = id;
		this.amount = amount;
	}
	
	/**
	 * get id
	 * @return Integer
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * set id
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * get amount
	 * @return int
	 */
	public int getAmount() {
		return amount;
	}


	/**
	 * set amount
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}


	/**
	 * get recipient
	 * @return GeneralUserDTO
	 */
	public GeneralUserDTO getDonatedTo() {
		return donatedTo;
	}


	/**
	 * set recipient
	 * @param donatedTo
	 */
	public void setDonatedTo(GeneralUserDTO donatedTo) {
		this.donatedTo = donatedTo;
	}


	/**
	 * get donor
	 * @return GeneralUserDTO
	 */
	public GeneralUserDTO getDonatedFrom() {
		return donatedFrom;
	}


	/**
	 * set donor
	 * @param donatedFrom
	 */
	public void setDonatedFrom(GeneralUserDTO donatedFrom) {
		this.donatedFrom = donatedFrom;
	}





}
