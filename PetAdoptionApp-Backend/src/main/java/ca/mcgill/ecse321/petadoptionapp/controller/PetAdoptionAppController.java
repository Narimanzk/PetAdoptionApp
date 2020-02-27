package ca.mcgill.ecse321.petadoptionapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.petadoptionapp.dao.AdoptionApplicationRespository;
import ca.mcgill.ecse321.petadoptionapp.dto.AddressDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.AdoptionApplicationDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.DonationDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.GeneralUserDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.PetProfileDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.QuestionDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.ResponseDTO;
import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.Donation;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.Response;
import ca.mcgill.ecse321.petadoptionapp.model.UserType;
import ca.mcgill.ecse321.petadoptionapp.service.PetAdoptionAppService;

@CrossOrigin(origins = "*")
@RestController
public class PetAdoptionAppController {
	@Autowired
	private PetAdoptionAppService service;

	// ~~~~~~~~~~ Rest API for General User ~~~~~~~~~~~~
	
	@GetMapping(value = { "/users" })
	public List<GeneralUserDTO> getAllGeneralUsers() {
		return service.getAllGeneralUsers().stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/users/{username}" })
	public GeneralUserDTO getGeneralUser(@PathVariable("username") String username) {
		return convertToDTO(service.getGeneralUser(username));
	}

	@PostMapping(value = { "/users" }, consumes = "application/json", produces = "application/json")
	public GeneralUserDTO createGeneralUser(@RequestBody GeneralUserDTO user) {
		// TODO Null checking in the essential parameter
		GeneralUser domainUser = service.createGeneralUser(user.getUsername(),
				convertToDomainObject(user.getUserType()), user.getEmail(), user.getPassword(), user.getName());
		GeneralUserDTO userDto = convertToDTO(domainUser);
		return userDto;
	}

	@PutMapping(value = { "/users" }, consumes = "application/json", produces = "application/json")
	public GeneralUserDTO updateGeneralUser(@RequestBody GeneralUserDTO user) {
		// TODO Null checking in the essential parameter
		GeneralUser domainUser = service.updateGeneralUser(user.getUsername(), user.getEmail(), user.getPassword(),
				user.getProfilePicture(), user.getDescription());
		GeneralUserDTO userDto = convertToDTO(domainUser);
		return userDto;
	}
	
	@DeleteMapping(value = { "/users/{username}" })
	public void deleteGeneralUser(@PathVariable("username") String username) {
		service.deleteGeneralUser(username);
	}

	// ~~~~~~~~~~ Rest API for Pet Profile ~~~~~~~~~~~~
	
	/**
	 * get all pet profiles
	 * @return
	 */
	@GetMapping(value = { "/petprofiles", "petprofile"})
	public List<PetProfileDTO> getAllPetProfiles() {
		return service.getAllPetProfile().stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	/**
	 * create a new pet profile
	 * @param username
	 * @param pet
	 * @return
	 */
	@PostMapping(value = {"/{username}/petprofiles", "/{username}/petprofile" }, consumes = "application/json", produces = "application/json")
	public PetProfileDTO createPetProfile(@PathVariable("username") String username, @RequestBody PetProfileDTO pet) {
		GeneralUser user = service.getGeneralUser(username);
		PetProfile petProfile = service.createOrUpdatePetProfile(pet.getName(), pet.getAge(), pet.getGender(),
				pet.getDescription(), pet.getSpecies(), pet.getProfilePic(), pet.getReason(), user, -1);
		return convertToDTO(petProfile);
	}

	/**
	 * get all pet profiles of an user
	 * @param username
	 * @return
	 */
	@GetMapping(value = { "{username}/petprofiles", "{username}/petprofile" })
	public List<PetProfileDTO> getPetProfileByUser(@PathVariable("username") String username) {
		GeneralUser user = service.getGeneralUser(username);
		return service.getPetProfileByUser(user).stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}
	
	/**
	 * delete a pet profile
	 * @param username
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = {"{username}/petprofile/{id}"})
	public PetProfileDTO deletePetProfile(@PathVariable("username") String username, @PathVariable("id") Integer id) {
		PetProfile pet = service.deletePetProfile(id);
		return convertToDTO(pet);
	}
	
	/**
	 * update a pet profile
	 * @param username
	 * @param pet
	 * @return
	 */
	@PutMapping(value = {"{username}/petprofile"})
	public PetProfileDTO updatePetProfile(@PathVariable("username") String username, @RequestBody PetProfileDTO pet) {
		GeneralUser user = service.getGeneralUser(username);
		PetProfile petProfile = service.createOrUpdatePetProfile(pet.getName(), pet.getAge(), pet.getGender(),
				pet.getDescription(), pet.getSpecies(), pet.getProfilePic(), pet.getReason(), user, pet.getId());
		return convertToDTO(petProfile);
	}
	
	//~~~~~~~~~~~~~ Rest api for Adoption Application ~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * get all applications of an user
	 * @param id
	 * @return
	 */
	@GetMapping(value = {"/{username}/applications"})
	public List<AdoptionApplicationDTO> getApplicationByUser(@PathVariable("username") String username) {
		GeneralUser user = service.getGeneralUser(username);
		return service.getApplicationByUser(user).stream().map(app -> convertToDTO(app)).collect(Collectors.toList());
	}
	
	/**
	 * get all applications of a petId
	 * @param id
	 * @return
	 */
	@GetMapping(value = {"/{username}/applications/{petId}"})
	public List<AdoptionApplicationDTO> getApplicationByPet(@PathVariable("petId") Integer id){
		PetProfile pet = service.getPetProfileById(id);
		return service.getApplicationByPetProfile(pet).stream().map(app -> convertToAttributeDTO(app)).collect(Collectors.toList());
	}
	
	/**
	 * post an application
	 * @param username
	 * @param app
	 * @param id
	 * @return
	 */
	@PostMapping(value = {"/{username}/applications/{petId}"}, consumes = "application/json", produces = "application/json")
	public AdoptionApplicationDTO createApplication(@PathVariable("username") String username, @RequestBody AdoptionApplicationDTO app, 
			@PathVariable("petId") Integer id) {
		GeneralUser user = service.getGeneralUser(username);
		PetProfile pet = service.getPetProfileById(id);
		AdoptionApplication application = service.createOrUpdateAdoptionApplication(app.getApplicationDescription(), app.getApplicationStatus(), 
				user, pet, -1);
		return convertToDTO(application);
	}
	
	/**
	 * update an application
	 * @param username
	 * @param app
	 * @param id
	 * @return
	 */
	@PutMapping(value = {"/{username}/applications/{petId}"}, consumes = "application/json", produces = "application/json")
	public AdoptionApplicationDTO updateApplication(@PathVariable("username") String username, @RequestBody AdoptionApplicationDTO app, 
			@PathVariable("petId") Integer id) {
		GeneralUser user = service.getGeneralUser(username);
		PetProfile pet = service.getPetProfileById(id);
		AdoptionApplication application = service.createOrUpdateAdoptionApplication(app.getApplicationDescription(), app.getApplicationStatus(), 
				user, pet, app.getId());
		return convertToAttributeDTO(application);
	}
	
	/**
	 * delete an application
	 * @param appId
	 * @return
	 */
	@DeleteMapping(value = {"/{username}/applications/{appId}"})
	public AdoptionApplicationDTO deleteApp(@PathVariable("appId") Integer appId) {
		return convertToDTO(service.deleteApplication(appId));
	}

	// ~~~~~~~~~~~~ Convert To DTO Methods Below ~~~~~~~~~~~~~~~~~~~~~~~

	private GeneralUserDTO convertToDTO(GeneralUser user) {
		GeneralUserDTO userDTO = new GeneralUserDTO(user.getUsername(), convertToDTO(user.getUserType()),
				user.getEmail(), null, user.getName(), user.getProfilePicture(), user.getPhone(), user.getBalance(),
				user.getDescription());
		if (user.getAddress() != null) {
			userDTO.setAddress(convertToAttributeDTO(user.getAddress()));
		}

		List<AdoptionApplicationDTO> applications = new ArrayList<AdoptionApplicationDTO>();
		if (user.getAdoptionApplications() != null) {
			for (AdoptionApplication app : user.getAdoptionApplications()) {
				applications.add(convertToAttributeDTO(app));
			}
		}
		userDTO.setAdoptionApplications(applications);

		List<PetProfileDTO> profiles = new ArrayList<PetProfileDTO>();
		if (user.getPetProfiles() != null) {
			for (PetProfile prof : user.getPetProfiles()) {
				profiles.add(convertToAttributeDTO(prof));
			}
		}
		userDTO.setPetProfiles(profiles);

		List<ResponseDTO> responses = new ArrayList<ResponseDTO>();
		if (user.getResponses() != null) {
			for (Response res : user.getResponses()) {
				responses.add(convertToAttributeDTO(res));
			}
		}
		userDTO.setResponses(responses);

		List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
		if (user.getQuestions() != null) {
			for (Question res : user.getQuestions()) {
				questions.add(convertToAttributeDTO(res));
			}
		}
		userDTO.setQuestions(questions);

		List<DonationDTO> donationGiven = new ArrayList<DonationDTO>();
		if (user.getDonationsGiven() != null) {
			for (Donation donation : user.getDonationsGiven()) {
				donationGiven.add(convertToAttributeDTO(donation));
			}
		}
		userDTO.setDonationGiven(donationGiven);

		List<DonationDTO> donationAccepted = new ArrayList<DonationDTO>();
		if (user.getDonationsAccepted() != null) {
			for (Donation donation : user.getDonationsAccepted()) {
				donationAccepted.add(convertToAttributeDTO(donation));
			}
		}
		userDTO.setDonationAccepted(donationAccepted);
		return userDTO;
	}

	private GeneralUserDTO convertToAttributeDTO(GeneralUser user) {
		GeneralUserDTO userDTO = new GeneralUserDTO(user.getUsername(), convertToDTO(user.getUserType()),
				user.getEmail(), null, user.getName(), user.getProfilePicture(), user.getPhone(), user.getBalance(),
				user.getDescription());
		return userDTO;
	}

	private DonationDTO convertToAttributeDTO(Donation donation) {
		// TODO Auto-generated method stub
		return null;
	}

	private QuestionDTO convertToAttributeDTO(Question res) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * convert a pet profile object to dto object
	 * @param p
	 * @return
	 */
	private PetProfileDTO convertToDTO(PetProfile p) {
		PetProfileDTO pet = new PetProfileDTO(p.getId(), p.getPetName(), p.getAge(), p.getPetGender(),
				p.getDescription(), p.getPetSpecies(), p.getProfilePicture(), p.getReason());
		pet.setUser(convertToAttributeDTO(p.getUser()));
		return pet;
	}

	/**
	 * convert a pet profile object to dto object with attributes only
	 * @param p
	 * @return
	 */
	private PetProfileDTO convertToAttributeDTO(PetProfile p) {
		PetProfileDTO pet = new PetProfileDTO(p.getId(), p.getPetName(), p.getAge(), p.getPetGender(),
				p.getDescription(), p.getPetSpecies(), p.getProfilePicture(), p.getReason());
		return pet;
	}

	private ResponseDTO convertToAttributeDTO(Response res) {
		// TODO Auto-generated method stub
		return null;
	}

	private AdoptionApplicationDTO convertToDTO(AdoptionApplication app) {
		AdoptionApplicationDTO application = new AdoptionApplicationDTO(app.getId(), app.getApplicationDescription(),
				app.getApplicationStatus());
		application.setPetProfile(convertToAttributeDTO(app.getPetProfile()));
		application.setUser(convertToAttributeDTO(app.getUser()));
		return application;
	}
	
	private AdoptionApplicationDTO convertToAttributeDTO(AdoptionApplication app) {
		AdoptionApplicationDTO application = new AdoptionApplicationDTO(app.getId(), app.getApplicationDescription(),
				app.getApplicationStatus());
		return application;
	}

	private AddressDTO convertToAttributeDTO(Address address) {
		// TODO Auto-generated method stub
		return null;
	}

	private String convertToDTO(UserType userType) {
		if (userType == UserType.Admin)
			return "Admin";
		if (userType == UserType.PetShelter)
			return "PetShelter";
		return "Owner";
	}

	// ~~~~~~~~~~~~ Convert To Domain Model ~~~~~~~~~~~~~~~~~~~~~~~

	private UserType convertToDomainObject(String userType) {
		if (userType.equalsIgnoreCase("Admin"))
			return UserType.Admin;
		if (userType.equalsIgnoreCase("PetShelter"))
			return UserType.PetShelter;
		return UserType.Owner;
	}

}
