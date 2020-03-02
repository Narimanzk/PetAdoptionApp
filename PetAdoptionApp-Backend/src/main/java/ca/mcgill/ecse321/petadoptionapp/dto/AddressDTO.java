package ca.mcgill.ecse321.petadoptionapp.dto;

public class AddressDTO {
	private Integer id;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String country;

	/**
	 * empty constructor for Address
	 */
	public AddressDTO() {

	}

	/**
	 * constructor for Address
	 * @param id
	 * @param street
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param country
	 */
	public AddressDTO(Integer id, String street, String city, String state, String postalCode, String country) {
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
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
	 * get street
	 * @return String
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * set street
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * get city
	 * @return String
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * set street
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * get state
	 * @return String
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * set state
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * get postal code
	 * @return String
	 */
	public String getPostalCode() {
		return postalCode;
	}
	
	/**
	 * set postal code
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * get country
	 * @return String
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * set country
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}
