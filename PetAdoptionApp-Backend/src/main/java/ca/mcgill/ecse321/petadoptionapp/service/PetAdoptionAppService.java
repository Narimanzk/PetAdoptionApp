package ca.mcgill.ecse321.petadoptionapp.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petadoptionapp.dao.AddressRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.AdoptionApplicationRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.DonationRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.GeneralUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.PetProfileRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.QuestionRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.ResponseRepository;
import ca.mcgill.ecse321.petadoptionapp.model.Gender;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.Response;
import ca.mcgill.ecse321.petadoptionapp.model.UserType;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.ApplicationStatus;
import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.Donation;

@Service
public class PetAdoptionAppService {
	@Autowired
	private PetProfileRespository petProfileRespository;
	@Autowired
	private AdoptionApplicationRespository adoptionApplicationRespository;
	@Autowired
	private GeneralUserRepository generalUserRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private DonationRepository donationRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ResponseRepository responseRepository;

	/**
	 * get all pet profiles of an user
	 * @param user
	 * @return
	 */
	@Transactional
	public List<PetProfile> getPetProfileByUser(GeneralUser user) {
		List<PetProfile> profiles = new ArrayList<>();
		for (PetProfile p : petProfileRespository.findByUser(user)) {
			profiles.add(p);
		}
		return profiles;
	}

	/**
	 * create a new pet profile
	 * @param name
	 * @param age
	 * @param petGender
	 * @param description
	 * @param species
	 * @param profile
	 * @param reason
	 * @param user
	 * @return
	 */
	@Transactional
	public PetProfile createPetProfile(String name, int age, Gender petGender, String description, String species,
			byte[] profile, String reason, GeneralUser user) {
		PetProfile pet = new PetProfile();
		pet.setAge(age);
		pet.setPetName(name);
		pet.setPetGender(petGender);
		pet.setPetSpecies(species);
		pet.setProfilePicture(profile);
		pet.setReason(reason);
		pet.setUser(user);
		pet.setDescription(description);
		petProfileRespository.save(pet);
		return pet;
	}

	/**
	 * get all pet profiles
	 * @return
	 */
	@Transactional
	public List<PetProfile> getAllPetProfile() {
		return toList(petProfileRespository.findAll());
	}
	/**
	 * create an application
	 * @param description
	 * @param status
	 * @param user
	 * @param profile
	 * @return
	 */
	@Transactional
	public AdoptionApplication createAdoptionApplication(String description, ApplicationStatus status, GeneralUser user,
			PetProfile profile) {
		AdoptionApplication application = new AdoptionApplication();
		application.setApplicationDescription(description);
		application.setApplicationStatus(status);
		application.setPetProfile(profile);
		application.setUser(user);
		adoptionApplicationRespository.save(application);
		return application;
	}
	
	/**
	 * get all application of an adopter
	 * @param user
	 * @return
	 */
	@Transactional
	public List<AdoptionApplication> getApplicationByUser(GeneralUser user){
		List<AdoptionApplication> applications = new ArrayList<>();
		for (AdoptionApplication app : adoptionApplicationRespository.findByUser(user)) {
			applications.add(app);
		}
		return applications;
	}
	
	/**
	 * get all application of a pet profile
	 * @param profile
	 * @return
	 */
	@Transactional
	public List<AdoptionApplication> getApplicationByPetProfile(PetProfile profile){
		List<AdoptionApplication> applications = new ArrayList<>();
		for (AdoptionApplication app : adoptionApplicationRespository.findByPetProfile(profile)) {
			applications.add(app);
		}
		return applications;
	}
	
	//~~~~~~~~~~ DONATION SERVICES ~~~~~~~~~~~~
	
	/**
	 * Create a new donation object
	 * @param amount
	 * @param shelter
	 * @param user
	 * @return A newly created Donation object
	 */
	@Transactional
	public Donation createDonation(Integer amount, GeneralUser shelter, GeneralUser user) {
		Donation donation = new Donation();
		donation.setAmount(amount);
		donation.setDonatedTo(shelter);
		donation.setDonatedFrom(user);
		donationRepository.save(donation);
		return donation;
	}

	/**
	 * Get Donation object by id
	 * @param id
	 * @return Donation object with given id
	 */
	@Transactional
	public Donation getDonation(int id) {
		return donationRepository.findDonationById(id);
	}

	/**
	 * @return all donations in a list
	 */
	@Transactional
	public List<Donation> getAllDonations() {
		return toList(donationRepository.findAll());
	}

	/**
	 * get the list of donations made by a user
	 * @param generalUser
	 * @return list of donations
	 */
	@Transactional
	public List<Donation> getDonationsMadeByGeneralUser(GeneralUser generalUser) {
		List<Donation> donationsMadeByGeneralUser = new ArrayList<>();
		for (Donation d : donationRepository.findByDonatedFrom(generalUser)) {
			donationsMadeByGeneralUser.add(d);
		}
		return donationsMadeByGeneralUser;
	}

	/**
	 * get the list of donations donated to a shelter
	 * @param generalUser
	 * @return list of donations
	 */
	@Transactional
	public List<Donation> getDonationsForGeneralUser(GeneralUser generalUser) {
		List<Donation> donationsForGeneralUser = new ArrayList<>();
		for (Donation d : donationRepository.findByDonatedTo(generalUser)) {
			donationsForGeneralUser.add(d);
		}
		return donationsForGeneralUser;
	}
	
	@Transactional
	public Response createResponse(String text, Question question, GeneralUser author) {
		Response response = new Response();
		response.setText(text);
		response.setQuestion(question);
		response.setUser(author);
		responseRepository.save(response);
		return response;
	}

	@Transactional
	public Response getResponse(int id) {
		return responseRepository.findResponseById(id);
	}

	@Transactional
	public List<Response> getAllResponses() {
		return toList(responseRepository.findAll());
	}

	@Transactional
	public List<Response> getResponsesForQuestion(Question question) {
		List<Response> responsesForQuestion = new ArrayList<>();
		for (Response r : responseRepository.findByQuestion(question)) {
			responsesForQuestion.add(r);
		}
		return responsesForQuestion;
	}

	@Transactional
	public List<Response> getResponsesForGeneralUser(GeneralUser user) {
		List<Response> responsesForGeneralUser = new ArrayList<>();
		for (Response r : responseRepository.findByUser(user)) {
			responsesForGeneralUser.add(r);
		}
		return responsesForGeneralUser;
	}

	//~~~~~~~~~~ GENERAL USER SERVICES ~~~~~~~~~~~~
	
	/**
	 * Create a new general user.
	 * @param username
	 * @param userType
	 * @param email
	 * @param password
	 * @param name
	 * @return A newly created general user object.
	 */
	@Transactional
	public GeneralUser createGeneralUser(String username, UserType userType, String email, String password, String name) {
		GeneralUser user = new GeneralUser();
		user.setUsername(username);
		user.setUserType(userType);
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);
		generalUserRepository.save(user);
		return user;
	}
	
	/**
	 * Update the general user information.
	 * @param username
	 * @param email
	 * @param password
	 * @param profilePicture
	 * @param description
	 * @return the updated general user.
	 */
	@Transactional
	public GeneralUser updateGeneralUser(String username, String email, String password, byte[] profilePicture, String description) {
		GeneralUser user = generalUserRepository.findGeneralUserByUsername(username);
		if(email!=null)user.setEmail(email);
		if(password!=null)user.setPassword(password);
		if(profilePicture!=null)user.setProfilePicture(profilePicture);
		if(description!=null)user.setDescription(description);
		generalUserRepository.save(user);
		return user;
	}
	
	/**
	 * Delete the user given. 
	 * @param username
	 */
	@Transactional
	public void deleteGeneralUser(String username) {
		generalUserRepository.deleteById(username);
	}
	
	/**
	 * @param username
	 * @return general user with the given username
	 */
	@Transactional
	public GeneralUser getGeneralUser(String username) {
		return generalUserRepository.findGeneralUserByUsername(username);
	}
	
	/**
	 * @return All general user in a list.
	 */
	@Transactional
	public List<GeneralUser> getAllGeneralUsers() {
		return toList(generalUserRepository.findAll());
	}
	
	//~~~~~~~~~~ ADDRESS SERVICES ~~~~~~~~~~~~
	
	/**
	 * Create a new address
	 * @param street
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param country
	 * @return a newly created Address object
	 */
	@Transactional
	public Address createAddress(String street, String city, String state, String postalCode, String country) {
		Address address = new Address();
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setPostalCode(postalCode);
		address.setCountry(country);
		addressRepository.save(address);
		return address;
	}
	
	/**
	 * update existed address information
	 * @param id
	 * @param street
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param country
	 * @return The updated address object
	 */
	@Transactional
	public Address updateAddress(Integer id, String street, String city, String state, String postalCode, String country) {
		Address address = addressRepository.findAddressById(id);
		if(street!=null)address.setStreet(street);
		if(city!=null)address.setCity(city);
		if(state!=null)address.setState(state);
		if(postalCode!=null)address.setPostalCode(postalCode);
		if(country!=null)address.setCountry(country);
		addressRepository.save(address);
		return address;
	}
	
	/**
	 * Delete the given address. 
	 * @param id
	 */
	@Transactional
	public void deleteAddress(Integer id) {
		addressRepository.deleteById(id);
	}
	
	/**
	 * @return All Addresses in a list.
	 */
	@Transactional
	public List<Address> getAllAddresses() {
		return toList(addressRepository.findAll());
	}
	
	// ~~~~~~~~~~ Helper methods ~~~~~~~~~~
	
	/**
	 * @param <T>
	 * @param iterable
	 * @return list made from the iterable given.
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
