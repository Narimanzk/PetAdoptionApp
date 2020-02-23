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
import ca.mcgill.ecse321.petadoptionapp.dao.PetShelterRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.QuestionRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.RegularUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.ResponseRepository;
import ca.mcgill.ecse321.petadoptionapp.model.Gender;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.Response;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.ApplicationStatus;
import ca.mcgill.ecse321.petadoptionapp.model.Donation;

@Service
public class PetAdoptionAppService {
	@Autowired
	private RegularUserRepository regularUserRepository;
	@Autowired
	private PetProfileRespository petProfileRespository;
	@Autowired
	private AdoptionApplicationRespository adoptionApplicationRespository;
	@Autowired
	private PetShelterRepository petShelterRepository;
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

	@Transactional
	public RegularUser createRegularuser(String username, String password, String email) {
		RegularUser user = new RegularUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		regularUserRepository.save(user);
		return user;
	}

	@Transactional
	public RegularUser getRegularUser(String username) {
		RegularUser user = regularUserRepository.findRegularUserByUsername(username);
		return user;
	}

	@Transactional
	public List<RegularUser> getAllPersons() {
		return null;
		// toList(regularUserRepository.findAll());
	}

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

	@Transactional
	public Donation createDonation(Integer amount) {
		Donation donation = new Donation();
		donation.setAmount(amount);
		donationRepository.save(donation);
		return donation;
	}

	@Transactional
	public Donation getDonation(int id) {
		return donationRepository.findDonationById(id);
	}

	@Transactional
	public List<Donation> getAllDonations() {
		return toList(donationRepository.findAll());
	}

	@Transactional
	public List<Donation> getDonationsMadeByRegularUser(RegularUser user) {
		List<Donation> donationsMadeByRegularUser = new ArrayList<>();
		for (Donation d : donationRepository.findByDonatedFrom(user)) {
			donationsMadeByRegularUser.add(d);
		}
		return donationsMadeByRegularUser;
	}

	@Transactional
	public List<Donation> getDonationsForPetShelter(PetShelter shelter) {
		List<Donation> donationsForPetShelter = new ArrayList<>();
		for (Donation d : donationRepository.findByDonatedTo(shelter)) {
			donationsForPetShelter.add(d);
		}
		return donationsForPetShelter;
	}

	@Transactional
	public Response createResponse(String text) {
		Response response = new Response();
		response.setText(text);
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

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
