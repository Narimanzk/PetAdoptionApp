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

import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;
import ca.mcgill.ecse321.petadoptionapp.model.Response;
import ca.mcgill.ecse321.petadoptionapp.model.Donation;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
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
	public PetProfile getPetProfile() {
		return null;
	}
	
	@Transactional PetProfile createPetProfile() {
		return null;
	}
	
	@Transactional 
	public List<PetProfile> getAllPetProfile(){
		return toList(petProfileRespository.findAll());
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
		for(Donation d : donationRepository.findByRegularUser(user)) {
			donationsMadeByRegularUser.add(d);
		}
		return donationsMadeByRegularUser;
	}
	
	@Transactional
	public List<Donation> getDonationsForPetShelter(PetShelter shelter) {
		List<Donation> donationsForPetShelter = new ArrayList<>();
		for(Donation d: donationRepository.findByPetShelter(shelter)) {
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
		for(Response r : responseRepository.findByQuestion(question)) {
			responsesForQuestion.add(r);
		}
		return responsesForQuestion;
	}
	
	@Transactional
	public List<Response> getResponsesForGeneralUser(GeneralUser user) {
		List<Response> responsesForGeneralUser = new ArrayList<>();
		for(Response r : responseRepository.findByGeneralUser(user)) {
			responsesForGeneralUser.add(r);
		}
		return responsesForGeneralUser;
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}