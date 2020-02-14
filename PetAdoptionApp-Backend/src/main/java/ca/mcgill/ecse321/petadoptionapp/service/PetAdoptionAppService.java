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
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
