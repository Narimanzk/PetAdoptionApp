package ca.mcgill.ecse321.petadoptionapp.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import ca.mcgill.ecse321.petadoptionapp.dao.AdoptionApplicationRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.GeneralUserRepository;
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
import ca.mcgill.ecse321.petadoptionapp.model.ThreadStatus;
import ca.mcgill.ecse321.petadoptionapp.model.UserType;
import ca.mcgill.ecse321.petadoptionapp.service.PetAdoptionAppService;

@CrossOrigin(origins = "*")
@RestController
public class PetAdoptionAppController {
	@Autowired
	private PetAdoptionAppService service;
	@Autowired
	private GeneralUserRepository generalUserRepository;

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
	public GeneralUserDTO createGeneralUser(@RequestBody GeneralUserDTO user) throws IllegalArgumentException {
		GeneralUser domainUser = service.createGeneralUser(user.getUsername(),
				convertToDomainObject(user.getUserType()), user.getEmail(), user.getPassword(), user.getName());
		GeneralUserDTO userDto = convertToDTO(domainUser);
		return userDto;
	}

	@PutMapping(value = { "/users"}, consumes = "application/json", produces = "application/json")
	public GeneralUserDTO updateGeneralUser(@RequestBody GeneralUserDTO user) {
		GeneralUser domainUser = service.updateGeneralUser(user.getUsername(), user.getEmail(), user.getPassword(),
				user.getProfilePicture(), user.getDescription());
		GeneralUserDTO userDto = convertToDTO(domainUser);
		return userDto;
	}

	@DeleteMapping(value = { "/users/{username}" })
	public void deleteGeneralUser(@PathVariable("username") String username) throws IllegalArgumentException{
		service.deleteGeneralUser(username);
	}

	// ~~~~~~~~~~ Rest API for Pet Profile ~~~~~~~~~~~~

	/**
	 * get all pet profiles
	 * 
	 * @return
	 */
	@GetMapping(value = { "/petprofiles", "/petprofile" })
	public List<PetProfileDTO> getAllPetProfiles() {
		return service.getAllPetProfile().stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	/**
	 * create a new pet profile
	 * 
	 * @param username
	 * @param pet
	 * @return
	 * @throws IOException 
	 */
	@PostMapping(value = { "/petprofiles" }, consumes = "application/json", produces = "application/json")
	public PetProfileDTO createPetProfile(@RequestParam(name = "username") String username,
			@RequestBody PetProfileDTO petDto){
		GeneralUser user = service.getGeneralUser(username);
		PetProfile petProfile = service.createOrUpdatePetProfile(petDto.getName(), petDto.getAge(), petDto.getGender(),
				petDto.getDescription(), petDto.getSpecies(), petDto.getProfilePicture(), petDto.getReason(), user, -1);
		return convertToDTO(petProfile);
	}

	/**
	 * get all pet profiles of an user
	 * 
	 * @param username
	 * @return
	 */
	@GetMapping(value = { "/petprofiles/users/{username}" })
	public List<PetProfileDTO> getPetProfileByUser(@PathVariable("username") String username) {
		GeneralUser user = service.getGeneralUser(username);
		return service.getPetProfileByUser(user).stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}
	
	/**
	 * get a pet profile through an application
	 * @param id
	 * @return
	 */
	@GetMapping(value = {"/petprofiles/applications/{appid}"})
	public PetProfileDTO PetProfileByApplication(@PathVariable("appid") Integer id) {
		AdoptionApplication app = service.getApplicaiontById(id);
		PetProfile pet = service.getPetProfileByApplication(app);
		return convertToDTO(pet);
	}

	/**
	 * delete a pet profile
	 * 
	 * @param username
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = { "/petprofiles/{id}" })
	public PetProfileDTO deletePetProfile(@PathVariable("id") Integer id) {
		PetProfile pet = service.deletePetProfile(id);
		return convertToDTO(pet);
	}

	/**
	 * update a pet profile
	 * 
	 * @param username
	 * @param pet
	 * @return
	 */
	@PutMapping(value = { "/petprofiles" }, consumes = "application/json", produces = "application/json")
	public PetProfileDTO updatePetProfile(@RequestParam(name = "username") String username,
			@RequestBody PetProfileDTO petDto) {
		GeneralUser user = service.getGeneralUser(username);
		PetProfile petProfile = service.createOrUpdatePetProfile(petDto.getName(), petDto.getAge(), petDto.getGender(),
				petDto.getDescription(), petDto.getSpecies(), petDto.getProfilePicture(), petDto.getReason(), user,
				petDto.getId());
		return convertToDTO(petProfile);
	}

	// ~~~~~~~~~~~~~ Rest api for Adoption Application ~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * get all applications of an user
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = { "/applications/users/{username}" })
	public List<AdoptionApplicationDTO> getApplicationByUser(@PathVariable("username") String username) {
		GeneralUser user = service.getGeneralUser(username);
		return service.getApplicationByUser(user).stream().map(app -> convertToDTO(app)).collect(Collectors.toList());
	}

	/**
	 * get all applications of a petId
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = { "/applications/petprofiles/{petId}" })
	public List<AdoptionApplicationDTO> getApplicationByPet(@PathVariable("petId") Integer id) {
		PetProfile pet = service.getPetProfileById(id);
		return service.getApplicationByPetProfile(pet).stream().map(app -> convertToAttributeDTO(app))
				.collect(Collectors.toList());
	}

	/**
	 * post an application
	 * 
	 * @param username
	 * @param app
	 * @param id
	 * @return
	 */
	@PostMapping(value = { "/applications" }, consumes = "application/json", produces = "application/json")
	public AdoptionApplicationDTO createApplication(@RequestParam(name = "username") String username,
			@RequestParam(name = "petid") Integer petId,
			@RequestBody AdoptionApplicationDTO applicationDto) {
		GeneralUser user = service.getGeneralUser(username);
		PetProfile pet = service.getPetProfileById(petId);
		AdoptionApplication application = service.createOrUpdateAdoptionApplication(
				applicationDto.getApplicationDescription(), applicationDto.getApplicationStatus(), user, pet, -1);
		return convertToDTO(application);
	}

	/**
	 * update an application
	 * 
	 * @param username
	 * @param app
	 * @param id
	 * @return
	 */
	@PutMapping(value = { "/applications" }, consumes = "application/json", produces = "application/json")
	public AdoptionApplicationDTO updateApplication(@RequestParam(name = "username") String username,
			@RequestParam(name = "petid") Integer petId,
			@RequestBody AdoptionApplicationDTO applicationDto) {
		GeneralUser user = service.getGeneralUser(username);
		PetProfile pet = service.getPetProfileById(petId);
		AdoptionApplication application = service.createOrUpdateAdoptionApplication(
				applicationDto.getApplicationDescription(), applicationDto.getApplicationStatus(), user, pet,
				applicationDto.getId());
		return convertToAttributeDTO(application);
	}

	/**
	 * delete an application
	 * 
	 * @param appId
	 * @return
	 */
	@DeleteMapping(value = { "/applications/{appId}" })
	public AdoptionApplicationDTO deleteApp(@PathVariable("appId") Integer appId) {
		return convertToDTO(service.deleteApplication(appId));
	}

	// ~~~~~~~~~~ Rest API for Questions ~~~~~~~~~~~~

	@GetMapping(value = { "/questions" })
	public List<QuestionDTO> getAllQuestions() {
		return service.getAllQuestions().stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	@PostMapping(value = {
			"/questions" }, consumes = "application/json", produces = "application/json")
	public QuestionDTO createQuestion(@RequestParam(name = "username") String username, @RequestBody QuestionDTO questionDTO) {
		GeneralUser author = service.getGeneralUser(username);
		Question question = service.createQuestion(questionDTO.getID(), questionDTO.getTitle(), questionDTO.getDescription(), questionDTO.getStatus(), author);
		return convertToDTO(question);
	}

	@GetMapping(value = { "/users/{username}/questions" })
	public List<QuestionDTO> getQuestionsByUser(@PathVariable("username") String username) {
		GeneralUser user = service.getGeneralUser(username);
		return service.getQuestionsForGeneralUser(user).stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/response/{id}/question" })
	public QuestionDTO getQuestionFromResponse(@PathVariable("id") int id) {
		Response response = service.getResponse(id);
		return convertToDTO(service.getQuestionForResponse(response));
	}
	
	@PutMapping(value = {"/questions"}, consumes = "application/json", produces = "application/json")
	public QuestionDTO updateQuestions(@RequestParam(name = "username") String username, @RequestBody QuestionDTO questionDTO) {
		GeneralUser user = service.getGeneralUser(username);
		Question question = service.updateQuestion(questionDTO.getID(), questionDTO.getTitle(), questionDTO.getDescription(), questionDTO.getStatus(), user);
		return convertToDTO(question);
	}
	
	@DeleteMapping(value = {"/questions/{id}"})
	public void deleteQuestion(@PathVariable("id") Integer id) {
		service.deleteQuestion(id);
		
	}

	// ~~~~~~~~~ Rest API for Responses ~~~~~~~~~~

	@GetMapping(value = { "/responses" })
	public List<ResponseDTO> getAllResponses() {
		return service.getAllResponses().stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	@PostMapping(value = {
	"/responses" }, consumes = "application/json", produces = "application/json")
	public ResponseDTO createResponse(@RequestParam(name = "username") String username, @RequestParam(name = "questionID") Integer questionID, @RequestBody ResponseDTO responseDTO) {
		GeneralUser author = service.getGeneralUser(username);
		Question question = service.getQuestion(questionID);
		Response response = service.createResponse(responseDTO.getID(),responseDTO.getText(), question, author);
		return convertToDTO(response);
	}

	@GetMapping(value = { "/users/{username}/responses" })
	public List<ResponseDTO> getResponsessByUser(@PathVariable("username") String username) {
		GeneralUser user = service.getGeneralUser(username);
		return service.getResponsesForGeneralUser(user).stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/question/{id}/responses" })
	public List<ResponseDTO> getResponsesToQuestions(@PathVariable("id") int id) {
		Question question = service.getQuestion(id);
		return service.getResponsesForQuestion(question).stream().map(p -> convertToDTO(p))
				.collect(Collectors.toList());
	}
	
	@PutMapping(value = {"/responses"}, consumes = "application/json", produces = "application/json")
	public ResponseDTO updateResponses(@RequestParam(name = "username") String username, @RequestParam(name = "questionID") Integer questionID, @RequestBody ResponseDTO responseDTO) {
		GeneralUser user = service.getGeneralUser(username);
		Question question = service.getQuestion(questionID);
		Response response = service.updateResponse(responseDTO.getID(), responseDTO.getText(), question, user);
		return convertToDTO(response);
	}
	
	@DeleteMapping(value = {"/responses/{id}"})
	public void deleteResponse(@PathVariable("id") Integer id) {
		service.deleteResponse(id);
	}
	
	// ~~~~~~~~~~ Rest API for Address ~~~~~~~~~~~~
	
		@GetMapping(value = { "/addresses" })
		public List<AddressDTO> getAllAddresses() {
			return service.getAllAddresses().stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
		}
		
		@GetMapping(value = { "/addresses/users/{username}" })
		public AddressDTO getAddressByUser(@PathVariable("username") String username) {
			Address address = service.getGeneralUser(username).getAddress();
			return convertToDTO(service.getAddress(address.getId()));
		}
		
		@PostMapping(value = { "/addresses" }, consumes = "application/json", produces = "application/json")
		public AddressDTO createAddress(@RequestParam(name = "username") String username, @RequestBody AddressDTO address) {
			GeneralUser user = service.getGeneralUser(username);
			Address domainAddress = service.createAddress(address.getStreet(), address.getCity(), address.getState(), address.getPostalCode(), address.getCountry());
			user.setAddress(domainAddress);
			generalUserRepository.save(user);
			return convertToDTO(domainAddress);
		}
		
		@PutMapping(value = { "/addresses" }, consumes = "application/json", produces = "application/json")
		public AddressDTO updateAddress(@RequestParam(name = "username") String username, @RequestBody AddressDTO address) {
			Address domainAddress = service.getGeneralUser(username).getAddress();
			domainAddress = service.updateAddress(domainAddress.getId(),address.getStreet() , address.getCity(), address.getState(), address.getPostalCode(), address.getCountry());
			return convertToDTO(domainAddress);
		}
		
		
		@DeleteMapping(value = { "/addresses/{id}" })
		public void deleteAddress(@PathVariable("id") Integer id) {
			service.deleteAddress(id);
		}
		
		// ~~~~~~~~~~ Rest API for Donation ~~~~~~~~~~~~
		
		@GetMapping(value = { "/donations" })
		public List<DonationDTO> getAllDonations() {
			return service.getAllDonations().stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
		}
		
		@GetMapping(value = { "donations/recipients/{username}" })
		public List<DonationDTO> getDonationByDonatedTo(@PathVariable("username") String username) {
			GeneralUser user = service.getGeneralUser(username);
			return service.getDonationsForGeneralUser(user).stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
		}
		
		@GetMapping(value = { "donations/donors/{username}" })
		public List<DonationDTO> getDonationByDonatedFrom(@PathVariable("username") String username) {
			GeneralUser user = service.getGeneralUser(username);
			return service.getDonationsMadeByGeneralUser(user).stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
		}
		
		@PostMapping(value = { "/donations" }, consumes = "application/json", produces = "application/json")
		public DonationDTO createDonation(@RequestParam(name = "donatedFrom") String donatedFrom,
				@RequestParam(name = "donatedTo") String donatedTo, @RequestBody DonationDTO donation) {
			GeneralUser domainDonatedFrom = service.getGeneralUser(donatedFrom);
			GeneralUser domainDonatedTo = service.getGeneralUser(donatedTo);
			Donation domainDonation = service.createOrUpdateDonation(-1,donation.getAmount(), domainDonatedFrom, domainDonatedTo);
			domainDonatedFrom.getDonationsGiven().add(domainDonation);
			domainDonatedTo.getDonationsAccepted().add(domainDonation);
			generalUserRepository.save(domainDonatedFrom);
			generalUserRepository.save(domainDonatedTo);
			DonationDTO donationDto = convertToDTO(domainDonation);
			return donationDto;
		}
		
		@PutMapping(value = { "/donations" }, consumes = "application/json", produces = "application/json")
		public DonationDTO updateDonation(@RequestParam(name = "donatedFrom") String donatedFrom,
				@RequestParam(name = "donatedTo") String donatedTo, @RequestBody DonationDTO donation) {
			GeneralUser domainDonatedFrom = service.getGeneralUser(donatedFrom);
			GeneralUser domainDonatedTo = service.getGeneralUser(donatedTo);
			Donation domainDonation = service.createOrUpdateDonation(donation.getId(),donation.getAmount(), domainDonatedFrom, domainDonatedTo);
			generalUserRepository.save(domainDonatedFrom);
			generalUserRepository.save(domainDonatedTo);
			DonationDTO donationDto = convertToDTO(domainDonation);
			return donationDto;
		}
		
		@DeleteMapping(value = { "/donations/{id}" })
		public void deleteDonationsById(@PathVariable("id") Integer id) {
			service.deleteDonation(id);
		}

	// ~~~~~~~~~~~~ Convert To DTO Methods Below ~~~~~~~~~~~~~~~~~~~~~~~

	private GeneralUserDTO convertToDTO(GeneralUser user) {
	    if(user==null)return null;
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
	
	// ~~~~~~~~Donation to DonationDTO~~~~~~~~~~
	
	/**
	 * convert a donation object to dto object
	 * @param donation
	 * @return DonationDTO
	 */
	private DonationDTO convertToDTO(Donation donation) {
		DonationDTO donationDTO = new DonationDTO(donation.getId(),donation.getAmount());
		if (donation.getDonatedFrom() != null) {
			donationDTO.setDonatedFrom(convertToAttributeDTO(donation.getDonatedFrom()));
		}
		if (donation.getDonatedTo() != null) {
			donationDTO.setDonatedTo(convertToAttributeDTO(donation.getDonatedTo()));
		}
		return donationDTO;
	}

	/**
	 * convert a donation object to dto object with attributes only
	 * @param donation
	 * @return DonationDTO
	 */
	private DonationDTO convertToAttributeDTO(Donation donation) {
		DonationDTO donationDTO = new DonationDTO(donation.getId(),donation.getAmount());
		return donationDTO;
	}

	// ~~~~~~~Question to QuestionDTO ~~~~~~~~

	private QuestionDTO convertToDTO(Question question) {
		QuestionDTO questionDTO = new QuestionDTO(question.getId(), question.getTitle(), question.getDescription(),
				question.getThreadStatus());
		List<ResponseDTO> responses = new ArrayList<ResponseDTO>();
		if (question.getResponses() != null) {
			for (Response res : question.getResponses()) {
				responses.add(convertToAttributeDTO(res));
			}
		}
		questionDTO.setResponses(responses);
		questionDTO.setAuthor(convertToAttributeDTO(question.getUser()));
		return questionDTO;

	}

	private QuestionDTO convertToAttributeDTO(Question question) {
		QuestionDTO questionDTO = new QuestionDTO(question.getId(), question.getTitle(), question.getDescription(),
				question.getThreadStatus());
		return questionDTO;
	}

	/**
	 * convert a pet profile object to dto object
	 * 
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
	 * 
	 * @param p
	 * @return
	 */
	private PetProfileDTO convertToAttributeDTO(PetProfile p) {
		PetProfileDTO pet = new PetProfileDTO(p.getId(), p.getPetName(), p.getAge(), p.getPetGender(),
				p.getDescription(), p.getPetSpecies(), p.getProfilePicture(), p.getReason());
		return pet;
	}

	// ~~~~~~~~Response to ResponseDTO~~~~~~~~~~

	private ResponseDTO convertToDTO(Response res) {
		ResponseDTO responseDTO = new ResponseDTO(res.getId(),res.getText());
		responseDTO.setQuestion(convertToAttributeDTO(res.getQuestion()));
		responseDTO.setUser(convertToAttributeDTO(res.getUser()));
		return responseDTO;

	}

	private ResponseDTO convertToAttributeDTO(Response res) {
		ResponseDTO responseDTO = new ResponseDTO(res.getId(), res.getText());
		return responseDTO;
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
	
	// ~~~~~~~~Address to AddressDTO~~~~~~~~~~
	
	/**
	 * convert an address object to dto object
	 * @param address
	 * @return AddressDTO
	 */
	private AddressDTO convertToDTO(Address address) {
		AddressDTO addressDTO = new AddressDTO(address.getId(),address.getStreet(), address.getCity(), address.getState(), address.getPostalCode(), address.getCountry());
		return addressDTO;
	}

	/**
	 * convert an address object to dto object with attributes only
	 * @param address
	 * @return AddressDTO
	 */
	private AddressDTO convertToAttributeDTO(Address address) {
		AddressDTO addressDTO = new AddressDTO(address.getId(),address.getStreet(), address.getCity(), 
				address.getState(), address.getPostalCode(), address.getCountry());
		return addressDTO;
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
	    if(userType==null)return null;
		if (userType.equalsIgnoreCase("Admin"))
			return UserType.Admin;
		if (userType.equalsIgnoreCase("PetShelter"))
			return UserType.PetShelter;
		return UserType.Owner;
	}

}
