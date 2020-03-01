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
import ca.mcgill.ecse321.petadoptionapp.model.ThreadStatus;
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
	 * 
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
	
	@Transactional
	public PetProfile getPetProfileByApplication(AdoptionApplication app) {
		PetProfile pet = petProfileRespository.findByAdoptionApplications(app);
		return pet;
	}
	/**
	 * get a pet profile by id
	 * @param id
	 * @return
	 */
	@Transactional
	public PetProfile getPetProfileById(int id) {
		return petProfileRespository.findPetProfileById(id);
	}

	/**
	 * create a new pet profile
	 * 
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
	public PetProfile createOrUpdatePetProfile(String name, int age, Gender petGender, String description, String species,
			byte[] profile, String reason, GeneralUser user, int id) {
		String error = "";
		if (name == null || name.trim().length() == 0) {
			error += "Pet needs a name. ";
		}
		if (description == null || description.trim().length() == 0) {
			error += "Pet needs a description. ";
		}
		if (species == null || species.trim().length() == 0) {
			error += "Pet needs a species. ";
		}
		if (reason == null || reason.trim().length() == 0) {
			error += "Pet needs a reason. ";
		}
		
		PetProfile pet;
		if(id == -1) {
			pet = new PetProfile();
		}else {
			pet = petProfileRespository.findPetProfileById(id);
		}
		
		error = error.trim();
		if(error != "") {
			throw new IllegalArgumentException(error);
		}
		
		if(pet != null) {
			pet.setAge(age);
			pet.setPetName(name);
			pet.setPetGender(petGender);
			pet.setPetSpecies(species);
			pet.setProfilePicture(profile);
			pet.setReason(reason);
			pet.setUser(user);
			pet.setDescription(description);
			petProfileRespository.save(pet);
		}
		return pet;
	}

	/**
	 * get all pet profiles
	 * 
	 * @return
	 */
	@Transactional
	public List<PetProfile> getAllPetProfile() {
		return toList(petProfileRespository.findAll());
	}
	
	/**
	 * delete a pet profile by id
	 * @param id
	 * @return
	 */
	@Transactional
	public PetProfile deletePetProfile(int id) {
		PetProfile pet = petProfileRespository.findPetProfileById(id);
		if(pet != null) {
			petProfileRespository.deleteById(id);
		}
		return pet;
	}

	/**
	 * create an application
	 * 
	 * @param description
	 * @param status
	 * @param user
	 * @param profile
	 * @return
	 */
	@Transactional
	public AdoptionApplication createOrUpdateAdoptionApplication(String description, ApplicationStatus status, GeneralUser user,
			PetProfile profile, int id) {
		String error = "";
		if (description == null || description.trim().length() == 0) {
			error += "Application needs a description. ";
		}
		
		AdoptionApplication application;
		if(id == -1) {
			application = new AdoptionApplication();
		}else {
			application = adoptionApplicationRespository.findAdoptionApplicationById(id);
		}
		
		error = error.trim();
		if(error != "") {
			throw new IllegalArgumentException(error);
		}
		if(application != null) {
			application.setApplicationDescription(description);
			application.setApplicationStatus(status);
			application.setPetProfile(profile);
			application.setUser(user);
			adoptionApplicationRespository.save(application);
		}
		return application;
	}

	/**
	 * get all application of an adopter
	 * 
	 * @param user
	 * @return
	 */
	@Transactional
	public List<AdoptionApplication> getApplicationByUser(GeneralUser user) {
		List<AdoptionApplication> applications = new ArrayList<>();
		for (AdoptionApplication app : adoptionApplicationRespository.findByUser(user)) {
			applications.add(app);
		}
		return applications;
	}
	
	@Transactional
	public AdoptionApplication getApplicaiontById(int id) {
		AdoptionApplication application = adoptionApplicationRespository.findAdoptionApplicationById(id);
		return application;
	}

	/**
	 * get all application of a pet profile
	 * 
	 * @param profile
	 * @return
	 */
	@Transactional
	public List<AdoptionApplication> getApplicationByPetProfile(PetProfile profile) {
		List<AdoptionApplication> applications = new ArrayList<>();
		for (AdoptionApplication app : adoptionApplicationRespository.findByPetProfile(profile)) {
			applications.add(app);
		}
		return applications;
	}

	/**
	 * delete an application
	 * @param id
	 * @return
	 */
	@Transactional
	public AdoptionApplication deleteApplication(int id) {
		AdoptionApplication application = adoptionApplicationRespository.findAdoptionApplicationById(id);
		if(application != null) {
			adoptionApplicationRespository.deleteById(id);
		}
		return application;
	}
	//~~~~~~~~~~ DONATION SERVICES ~~~~~~~~~~~~

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
	/**
	 * create or update a donation
	 * @param id
	 * @param amount
	 * @param donatedFrom
	 * @param donatedTo
	 * @return updated donation object
	 */
	@Transactional
	public Donation createOrUpdateDonation(Integer id, Integer amount, GeneralUser donatedFrom, GeneralUser donatedTo) {
		String error = "";
		if (id == null || id < -1 || id == 0) {
			error += "Donation needs a valid id. ";
		}
		if (amount == null || amount <= 0) {
			error += "Donation needs a positive amount. ";
		}
		if (donatedFrom == null || donatedFrom.getUsername().trim().length() == 0) {
			error += "Donation needs a donor. ";
		}
		if (donatedTo == null || donatedTo.getUsername().trim().length() == 0) {
			error += "Donation needs a recipient. ";
		}
		error = error.trim();
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		Donation donation;
		if(id == -1) {
			donation = new Donation();
		}else {
			donation = donationRepository.findDonationById(id);
		}
		donation.setAmount(amount);
		donation.setDonatedFrom(donatedFrom);
		donation.setDonatedTo(donatedTo);
		donationRepository.save(donation);
		return donation;
	}
	
	/**
	 * Delete the donation. 
	 * @param id
	 */
	@Transactional
	public void deleteDonation(int id) throws IllegalArgumentException {
			donationRepository.deleteById(id);
	}
	

	//~~~~~~~~~~ RESPONSE SERVICES ~~~~~~~~~~~~
	


	@Transactional
	public Response createResponse(Integer Id, String text, Question question, GeneralUser author) {
		String error = "";
		if (text == null || text.trim().length() == 0) {
			error += "Response needs a text. ";
		}
		if (question == null) {
			error += "Response needs a question. ";
		}
		if (author == null) {
			error += "Response needs a user.";
		}
		
		error = error.trim();
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		Response response = new Response();
		response.setId(Id);
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
	
	@Transactional
	public Response updateResponse(Integer id, String text, Question question, GeneralUser user) {
		Response response = responseRepository.findResponseById(id);
		if (response != null) {
			if (text != null && text.trim().length() > 0) response.setText(text);
			if (question!= null) response.setQuestion(question);
			if (user != null) response.setUser(user);
			responseRepository.save(response);
		}
		return response;
	}
	@Transactional
	public void deleteResponse(Integer id) {
		responseRepository.deleteById(id);
	}
	// ~~~~~~~~~~ GENERAL USER SERVICES ~~~~~~~~~~~~

	/**
	 * Create a new general user.
	 * 
	 * @param username
	 * @param userType
	 * @param email
	 * @param password
	 * @param name
	 * @return A newly created general user object.
	 */
	@Transactional
	public GeneralUser createGeneralUser(String username, UserType userType, String email, String password, String name) {
		String error = "";
		if (username == null || username.trim().length() == 0) {
			error += "User needs a username. ";
		}
		if (userType == null) {
			error += "User needs a user type. ";
		}
		if (email == null || email.trim().length() == 0) {
			error += "User needs an email. ";
		}
		if (password == null || password.trim().length() == 0) {
			error += "User needs a password. ";
		}
		if (name == null || name.trim().length() == 0) {
			error += "User needs a name.";
		}
		error = error.trim();
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
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
	 * 
	 * @param username
	 * @param email
	 * @param password
	 * @param profilePicture
	 * @param description
	 * @return the updated general user.
	 */
	@Transactional
	public GeneralUser updateGeneralUser(String username, String email, String password, byte[] profilePicture,
			String description) {
		GeneralUser user = generalUserRepository.findGeneralUserByUsername(username);
		if (user != null) {
			if (email != null && email.trim().length() > 0) user.setEmail(email);
			if (password != null && password.trim().length() > 0) user.setPassword(password);
			if (profilePicture != null) user.setProfilePicture(profilePicture);
			if (description != null) user.setDescription(description);
			generalUserRepository.save(user);
		}
		return user;
	}

	/**
	 * Delete the user given.
	 * 
	 * @param username
	 */
	@Transactional
	public void deleteGeneralUser(String username) throws IllegalArgumentException{
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
		String error = "";
		if (street == null || street.trim().length() == 0) {
			error += "Address needs a street. ";
		}
		if (city == null || city.trim().length() == 0) {
			error += "Address needs a city. ";
		}
		if (state == null || state.trim().length() == 0) {
			error += "Address needs a state. ";
		}
		if (postalCode == null || postalCode.trim().length() == 0) {
			error += "Address needs a postalCode. ";
		}
		if (country == null || country.trim().length() == 0) {
			error += "Address needs a country.";
		}
		error = error.trim();
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
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
		if(address != null) {
		if(street!=null && street.trim().length() > 0)address.setStreet(street);
		if(city!=null && city.trim().length() > 0)address.setCity(city);
		if(state!=null && state.trim().length() > 0)address.setState(state);
		if(postalCode!=null && postalCode.trim().length() > 0)address.setPostalCode(postalCode);
		if(country!=null && country.trim().length() > 0)address.setCountry(country);
		addressRepository.save(address);
		}
		return address;
		
	}
	
	/**
	 * Delete the given address. 
	 * @param id
	 */
	@Transactional
	public void deleteAddress(Integer id) throws IllegalArgumentException{
		addressRepository.deleteById(id);
	}

	
	/**
	 * @param id
	 * @return address with given id
	 */
	@Transactional
	public Address getAddress(Integer id) {
		return addressRepository.findAddressById(id);
	}
	
	/**
	 * @return All Addresses in a list.
	 */
	@Transactional
	public List<Address> getAllAddresses() {
		return toList(addressRepository.findAll());
	}
  
  
	// ~~~~~~QUESTION SERVICES~~~~~~~~~
	
	@Transactional
	public Question createQuestion(Integer id, String title, String description, ThreadStatus status, GeneralUser author) {
		String error = "";
		if (title == null || title.trim().length() == 0) {
			error += "Question needs a title. ";
		}
		if (description == null || description.trim().length() == 0) {
			error += "Question needs a description. ";
		}
		if (status == null) {
			error += "Question needs a status. ";
		}
		if (author == null) {
			error += "Question needs a user. ";
		}
		error = error.trim();
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		Question question = new Question();
		question.setId(id);
		question.setTitle(title);
		question.setDescription(description);
		question.setThreadStatus(status);
		question.setUser(author);
		questionRepository.save(question);
		return question;
	}

	@Transactional
	public Question getQuestion(int id) {
		return questionRepository.findQuestionById(id);
	}

	@Transactional
	public List<Question> getAllQuestions() {
		return toList(questionRepository.findAll());
	}
	
	@Transactional
	public Question getQuestionForResponse(Response response) {
		Question questionOfResponse = new Question();
		questionOfResponse = questionRepository.findQuestionByResponses(response);
		return questionOfResponse;
	}

	@Transactional
	public List<Question> getQuestionsForGeneralUser(GeneralUser user) {
		List<Question> questionsForGeneralUser = new ArrayList<>();
		for (Question q : questionRepository.findQuestionsByUser(user)) {
			questionsForGeneralUser.add(q);
		}
		return questionsForGeneralUser;
	}
	
	@Transactional
	public Question updateQuestion(Integer id, String title, String description, ThreadStatus status, GeneralUser user) {
		Question question = questionRepository.findQuestionById(id);
		if (question != null) {
			if (title != null && title.trim().length() > 0) question.setTitle(title);
			if (description != null) question.setDescription(description);
			if (status != null) question.setThreadStatus(status);;
			if (user != null) question.setUser(user);
			questionRepository.save(question);
		}
		return question;
	}
	
	@Transactional
	public void deleteQuestion(Integer id) throws IllegalArgumentException{
		questionRepository.deleteById(id);
	}
	
  
	// ~~~~~~~~~~ Helper methods ~~~~~~~~~~

	/**
	 * @param <T>
	 * @param iterable
	 * @return list made from the iterable given.
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
