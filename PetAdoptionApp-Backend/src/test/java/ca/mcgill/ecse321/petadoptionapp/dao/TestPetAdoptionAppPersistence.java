package ca.mcgill.ecse321.petadoptionapp.dao;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.petadoptionapp.dao.AddressRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.DonationRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.PetShelterRepository;
import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.Donation;
import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.dao.PetProfileRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.RegularUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.AdoptionApplicationRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.QuestionRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.ResponseRepository;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.Gender;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;
import ca.mcgill.ecse321.petadoptionapp.model.ThreadStatus;
import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.Response;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPetAdoptionAppPersistence {
  
  
  

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


	/**
	 * The tear down process for every test
	 */
	@AfterEach
	public void clearDatabase() {
		// Clear the table to avoid inconsistency
		questionRepository.deleteAll();
		donationRepository.deleteAll();
		addressRepository.deleteAll();
		adoptionApplicationRespository.deleteAll();
		petProfileRespository.deleteAll();
		regularUserRepository.deleteAll();
		petShelterRepository.deleteAll();
		generalUserRepository.deleteAll();
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	// RegularUser Test Starts
	////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Test RegularUser table in create and load. (C/R)
	 * 
	 * @author Shuby Mao
	 */
	@Test
	public void testPersistAndLoadRegularUser() {
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		// make sure user doesn't exist
		RegularUser user = regularUserRepository.findRegularUserByUsername(username);
		assertNull(user);
		//
		user = createAndSaveRegularUser(username, email, password);
		user = null;
		user = regularUserRepository.findRegularUserByUsername(username);
		assertNotNull(user);
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
	}

	/**
	 * Test the regular table in create and update (C/U)
	 * 
	 * @author Shuby Mao
	 */
	@Test
	public void testUpdateRegularUser() {
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		RegularUser user = createAndSaveRegularUser(username, email, password);
		user = null;
		user = regularUserRepository.findRegularUserByUsername(username);
		assertNotNull(user);
		String securepassword = "password";
		user.setPassword(securepassword);
		regularUserRepository.save(user);
		user = null;
		user = regularUserRepository.findRegularUserByUsername(username);
		assertEquals(securepassword, user.getPassword());
	}

	/**
	 * Test the regular table in create and delete (C/D)
	 * 
	 * @author Shuby Mao
	 */
	@Test
	public void testDeleteRegularUser() {
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		RegularUser user = createAndSaveRegularUser(username, email, password);
		user = null;
		user = regularUserRepository.findRegularUserByUsername(username);
		// make sure user exist in database
		assertNotNull(user);
		// delete the user
		regularUserRepository.delete(user);
		user = regularUserRepository.findRegularUserByUsername(username);
		assertNull(user);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	// RegularUser Test Ends
	////////////////////////////////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////////////////////////////////
	// PetProfile Test Starts
	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * perform CREATE and READ test on PetProfile table
	 * 
	 * @author ANDREW TA
	 */
	@Test
	public void testPersistAndReadPetProfile() {
		// create a new pet profile
		PetProfile pet = createPetProfile();
		GeneralUser user = pet.getUser();

		// query that pet profile by id
		PetProfile pet2 = petProfileRespository.findPetProfileById(pet.getId());
		assertNotNull(pet2);
		assertEquals(pet2.getPetName(), pet.getPetName());
		assertEquals(pet2.getDescription(), pet.getDescription());
		assertEquals(pet2.getAge(), pet.getAge());
		assertEquals(pet2.getPetSpecies(), pet.getPetSpecies());
		assertArrayEquals(pet2.getProfilePicture(), pet.getProfilePicture());
		assertEquals(pet2.getPetGender(), pet.getPetGender());
		
		//check if the user of this pet profile is the same
		GeneralUser user2 = pet2.getUser();
		assertNotNull(user2);
		assertEquals(user.getUsername(), user2.getUsername());
	}

	/**
	 * perform CREATE and DELETE test on PetProfile table
	 * 
	 * @author ANDREW TA
	 */
	@Test
	public void testDeletePetProfile() {
		PetProfile pet = createPetProfile(); // create a pet profile
		petProfileRespository.delete(pet); // delete that pet profile
		pet = petProfileRespository.findPetProfileById(pet.getId());
		assertEquals(pet, null);
	}

	/**
	 * Perform CREATE and UPDATE test on PetProfile table.
	 * 
	 * @author ANDREW TA
	 */
	@Test
	public void testUpdatePetProfile() {
		PetProfile pet = createPetProfile(); // create a pet profile

		int id = pet.getId();
		pet = null;
		pet = petProfileRespository.findPetProfileById(id); // find that pet profile by id

		// new information
		int age = 3;
		String pet_name = "new_name";
		String description = "new_description";
		String petSpecies = "new_species";
		byte[] profile_pic = "\u00e0\u005f\u00d4\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d"
				.getBytes();
		Gender gender = Gender.Male;

		// update the entity and save to database
		pet.setAge(age);
		pet.setDescription(description);
		pet.setPetGender(gender);
		pet.setPetName(pet_name);
		pet.setPetSpecies(petSpecies);
		pet.setUser(createAndSaveRegularUser("testusername", "test@gmail.com", "admin"));
		pet.setProfilePicture(profile_pic);
		petProfileRespository.save(pet);

		// check if it is updated
		pet = null;
		pet = petProfileRespository.findPetProfileById(id);
		assertNotNull(pet);
		assertEquals(pet.getPetName(), pet_name);
		assertEquals(pet.getDescription(), description);
		assertEquals(pet.getAge(), age);
		assertEquals(pet.getPetSpecies(), petSpecies);
		assertArrayEquals(pet.getProfilePicture(), profile_pic);
		assertEquals(pet.getPetGender(), gender);
		
		//check if it links to new user
		assertEquals(pet.getUser().getUsername(), "testusername");
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	// PetProfile Test Ends
	////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////
	// Address Test Starts
	////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * perform CREATE and READ test on Address table
	 * 
	 * @author Narry
	 */
	@Test
	public void testPersistAndLoadAddress() {
		String street = "12 Sherbrooke W";
		String city = "Montreal";
		String state = "QC";
		String postalCode = "AAA-111";
		String country = "CA";
		Address address = createAddress("12 Sherbrooke W", "Montreal", "QC", "AAA-111", "CA");
		addressRepository.save(address);
		Integer id = address.getId();

		address = null;
		address = addressRepository.findAddressById(id);
		assertNotNull(address);
		assertEquals(id, address.getId());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(state, address.getState());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(country, address.getCountry());
	}

	/**
	 * perform CREATE and DELETE test on Address table
	 * 
	 * @author Narry
	 */
	@Test
	public void testDeleteAddress() {
		Address address = createAddress("12 Sherbrooke W", "Montreal", "QC", "AAA-111", "CA");
		addressRepository.delete(address);
		address = addressRepository.findAddressById(address.getId());
		assertEquals(address, null);
	}

	/**
	 * Perform CREATE and UPDATE test on Address table.
	 * 
	 * @author Narry
	 */
	@Test
	public void testUpdateAddress() {
		String newStreet = "22 Sherbrooke W";
		Address address = createAddress("12 Sherbrooke W", "Montreal", "QC", "AAA-111", "CA");
		addressRepository.save(address);
		Integer id = address.getId();
		address = null;
		address = addressRepository.findAddressById(id);
		assertNotNull(address);
		address.setStreet(newStreet);
		addressRepository.save(address);
		address = null;
		address = addressRepository.findAddressById(id);
		assertEquals(newStreet, address.getStreet());
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	// Address Test Ends
	////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////
	// Donation Test Starts
	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * perform CREATE and READ test on Donation table
	 * 
	 * @author Narry
	 */
	@Test
	public void testPersistAndLoadDonation() {
		Integer amount = 5;
		RegularUser user = createAndSaveRegularUser("testDon", "TestDon@test.com", "1234");
		PetShelter petShelter = createPetShelter();
		Donation donation = createDonation(5, user, petShelter);
		regularUserRepository.save(user);
		petShelterRepository.save(petShelter);
		donationRepository.save(donation);
		Integer id = donation.getId();

		donation = null;
		donation = donationRepository.findDonationById(id);
		assertNotNull(donation);
		assertEquals(id, donation.getId());
		assertEquals(amount, donation.getAmount());
		assertEquals(user.getUsername(), donation.getDonatedFrom().getUsername());
		assertEquals(petShelter.getUsername(), donation.getDonatedTo().getUsername());

	}

	/**
	 * perform CREATE and DELETE test on Donation table
	 * 
	 * @author Narry
	 */
	@Test
	public void testDeleteDonation() {
		RegularUser user = createAndSaveRegularUser("testDon", "TestDon@test.com", "1234");
		PetShelter petShelter = createPetShelter();
		Donation donation = createDonation(5, user, petShelter);
		donationRepository.delete(donation);
		donation = donationRepository.findDonationById(donation.getId());
		assertEquals(donation, null);
	}

	/**
	 * Perform CREATE and UPDATE test on Donation table.
	 * 
	 * @author Narry
	 */
	@Test
	public void testUpdateDonation() {
		Integer amount = 5;
		Integer newAmount = 100;
		RegularUser user = createAndSaveRegularUser("testDon", "TestDon@test.com", "1234");
		RegularUser newUser = createAndSaveRegularUser("newDon", "newDon@test.com", "1234");
		PetShelter petShelter = createPetShelter();
		Donation donation = createDonation(amount, user, petShelter);
		petShelterRepository.save(petShelter);
		donationRepository.save(donation);
		Integer id = donation.getId();
		
		donation = null;
		donation = donationRepository.findDonationById(id);
		assertNotNull(donation);
		donation.setAmount(newAmount);
		donation.setDonatedFrom(newUser);
		donationRepository.save(donation);
		donation = null;
		donation = donationRepository.findDonationById(id);
		assertEquals(newAmount, donation.getAmount());
		assertEquals(newUser.getUsername(), donation.getDonatedFrom().getUsername());
	}
	////////////////////////////////////////////////////////////////////////////////////////////
	// Donation Test Ends
	////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////
	// AdoptionApplication Test Starts
	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Perform CREATE AND READ test on AdoptionApplication table.
	 * 
	 * @author ANDREW TA
	 */
	@Test

	public void testPersistAndLoadAdoptionApplication() {
		AdoptionApplication application = createAdoptionApplication(); // create a new application
		GeneralUser user = application.getUser();
		PetProfile pet = application.getPetProfile();
		int id = application.getId();

		//check information of application
		AdoptionApplication application2 = adoptionApplicationRespository.findAdoptionApplicationById(id);
		assertNotNull(application2);
		assertEquals(application.getApplicationDescription(), application2.getApplicationDescription());
		assertEquals(application.getApplicationStatus(), application2.getApplicationStatus());
		
		//check user and pet profile
		GeneralUser user2 = application2.getUser();
		PetProfile pet2 = application2.getPetProfile();
		assertEquals(user.getUsername(), user2.getUsername());
		assertEquals(pet.getId(), pet2.getId());
	}

	/**
	 * Perform CREATE AND DELETE test on AdoptionApplication table
	 * 
	 * @author ANDREW TA
	 */
	@Test
	public void testDeleteAdoptionApplication() {
		AdoptionApplication application = createAdoptionApplication();
		int id = application.getId();

		adoptionApplicationRespository.delete(application);
		application = adoptionApplicationRespository.findAdoptionApplicationById(id);
		assertEquals(application, null);
	}

	/**
	 * Perform CREATE AND UPDATE test on AdoptionApplication table
	 * 
	 * @author ANDREW TA
	 */
	@Test
	public void testUpdateAdoptionApplication() {
		AdoptionApplication application = createAdoptionApplication();
		int id = application.getId();

		String new_description = "new_description";
		application.setApplicationDescription(new_description);
		adoptionApplicationRespository.save(application);

		application = adoptionApplicationRespository.findAdoptionApplicationById(id);
		assertEquals(application.getApplicationDescription(), new_description);
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	// AdoptionApplication Test Ends
	////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////
	// PetShelter Test Starts
	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testPersistAndLoadPetShelter() {
		PetShelter shelter = createPetShelter();
		String username = shelter.getUsername();
		String password = shelter.getPassword();
		String name = shelter.getName();
		String phone = shelter.getPhone();
		String email = shelter.getEmail();
		int balance = shelter.getBalance();
		petShelterRepository.save(shelter);

		shelter = null;
		shelter = petShelterRepository.findPetShelterByUsername(username);
		assertNotNull(shelter);
		assertEquals(username, shelter.getUsername());
		assertEquals(password, shelter.getPassword());
		assertEquals(name, shelter.getName());
		assertEquals(phone, shelter.getPhone());
		assertEquals(email, shelter.getEmail());
		assertEquals(balance, shelter.getBalance());
	}

	/**
	 * 
	 */
	@Test
	public void testUpdatePetShelter() {
		PetShelter shelter = createPetShelter();
		String username = shelter.getUsername();
		String name = shelter.getName();
		String email = shelter.getEmail();
		petShelterRepository.save(shelter);

		shelter = null;
		shelter = petShelterRepository.findPetShelterByUsername(username);
		assertNotNull(shelter);
		String newPassword = "newpassword";
		String newPhone = "333-333-4444";
		int newBalance = 234;
		shelter.setPassword(newPassword);
		shelter.setPhone(newPhone);
		shelter.setBalance(newBalance);
		petShelterRepository.save(shelter);

		shelter = null;
		shelter = petShelterRepository.findPetShelterByUsername(username);
		assertNotNull(shelter);
		assertEquals(newPassword, shelter.getPassword());
		assertEquals(newPhone, shelter.getPhone());
		assertEquals(newBalance, shelter.getBalance());
		assertEquals(name, shelter.getName());
		assertEquals(email, shelter.getEmail());

	}

	/**
	 * 
	 */
	@Test
	public void testDeletePetShelter() {
		PetShelter shelter = createPetShelter();
		String username = shelter.getUsername();
		petShelterRepository.save(shelter);

		shelter = null;
		shelter = petShelterRepository.findPetShelterByUsername(username);
		assertNotNull(shelter);

		petShelterRepository.delete(shelter);
		shelter = petShelterRepository.findPetShelterByUsername(username);
		assertNull(shelter);
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	// PetShelter Test Ends
	////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////
	// General User Test Starts
	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testPersistAndLoadGeneralUser() {
		// PetShelter
		PetShelter petShelter = createPetShelter();
		String shelterUsername = petShelter.getUsername();
		String shelterPassword = petShelter.getPassword();
		String shelterName = petShelter.getName();
		String shelterPhone = petShelter.getPhone();
		String shelterEmail = petShelter.getEmail();
		int shelterBalance = petShelter.getBalance();
		GeneralUser shelter = petShelter;
		generalUserRepository.save(shelter);

		// Check the pet shelter still exists, and all the superclass attributes can be
		// accessed
		shelter = null;
		shelter = generalUserRepository.findGeneralUserByUsername(shelterUsername);
		assertNotNull(shelter);
		assertEquals(shelterPassword, shelter.getPassword());
		assertEquals(shelterName, shelter.getName());
		assertEquals(shelterPhone, shelter.getPhone());
		assertEquals(shelterEmail, shelter.getEmail());

		// Check the pet shelter specific items can still be accessed
		petShelter = (PetShelter) shelter;
		assertEquals(shelterBalance, petShelter.getBalance());

		// RegularUser
		String userUsername = "testusername";
		String userEmail = "test@testmail.com";
		String userPassword = "123456789";
		String userPersonalDescription = "Personal description stuff";
		RegularUser user = new RegularUser();
		user.setUsername(userUsername);
		user.setEmail(userEmail);
		user.setPassword(userPassword);
		user.setPersonalDescription(userPersonalDescription);
		GeneralUser genUser = user;
		generalUserRepository.save(genUser);

		// Check the regular user still exists, and all the superclass attributes can be
		// accessed
		genUser = null;
		genUser = generalUserRepository.findGeneralUserByUsername(userUsername);
		assertNotNull(genUser);
		assertEquals(userEmail, genUser.getEmail());
		assertEquals(userPassword, genUser.getPassword());

		// Check the regular user specific items can still be accessed
		user = (RegularUser) genUser;
		assertEquals(userPersonalDescription, user.getPersonalDescription());
	}

	/**
	 * 
	 */
	@Test
	public void testUpdateGeneralUser() {
		// PetShelter
		GeneralUser shelter = createPetShelter();
		String shelterUsername = shelter.getUsername();
		String shelterPassword = shelter.getPassword();
		String shelterName = shelter.getName();
		generalUserRepository.save(shelter);

		shelter = null;
		shelter = generalUserRepository.findGeneralUserByUsername(shelterUsername);
		assertNotNull(shelter);
		String shelterNewEmail = "abcdefg@hijk.lmn";
		String shelterNewPhone = "932-243-23253";
		shelter.setEmail(shelterNewEmail);
		shelter.setPhone(shelterNewPhone);
		generalUserRepository.save(shelter);

		shelter = null;
		shelter = generalUserRepository.findGeneralUserByUsername(shelterUsername);
		assertNotNull(shelter);
		assertEquals(shelterPassword, shelter.getPassword());
		assertEquals(shelterName, shelter.getName());
		assertEquals(shelterNewEmail, shelter.getEmail());
		assertEquals(shelterNewPhone, shelter.getPhone());

		// RegularUser
		String userUsername = "testusername";
		String userEmail = "test@testmail.com";
		String userName = "testname";
		GeneralUser user = new RegularUser();
		user.setUsername(userUsername);
		user.setName(userName);
		user.setEmail(userEmail);
		user.setPassword("123456789");
		user.setPhone("324-12425-231536");
		generalUserRepository.save(user);

		user = null;
		user = generalUserRepository.findGeneralUserByUsername(userUsername);
		assertNotNull(user);
		String userNewPassword = "newuserpassword";
		String userNewPhone = "124-21421-5115-15";
		user.setPassword(userNewPassword);
		user.setPhone(userNewPhone);
		generalUserRepository.save(user);

		user = null;
		user = generalUserRepository.findGeneralUserByUsername(userUsername);
		assertNotNull(user);
		assertEquals(userNewPassword, user.getPassword());
		assertEquals(userNewPhone, user.getPhone());
		assertEquals(userEmail, user.getEmail());
		assertEquals(userName, user.getName());
	}

	/**
	 * 
	 */
	@Test
	public void testDeleteGeneralUser() {
		// PetShelter
		GeneralUser shelter = createPetShelter();
		String shelterUsername = shelter.getUsername();
		generalUserRepository.save(shelter);

		shelter = null;
		shelter = generalUserRepository.findGeneralUserByUsername(shelterUsername);
		assertNotNull(shelter);

		generalUserRepository.delete(shelter);
		shelter = generalUserRepository.findGeneralUserByUsername(shelterUsername);
		assertNull(shelter);

		// PetShelter
		GeneralUser user = createPetShelter();
		String userUsername = user.getUsername();
		generalUserRepository.save(user);

		user = null;
		user = generalUserRepository.findGeneralUserByUsername(userUsername);
		assertNotNull(user);

		generalUserRepository.delete(user);
		user = generalUserRepository.findGeneralUserByUsername(userUsername);
		assertNull(user);
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	// General Test Ends
	////////////////////////////////////////////////////////////////////////////////////////////
	
	//Lenoy - Tests for Question Class
	@Test
	public void testPersistAndLoadQuestion() {
		Integer id = 57515; 
		String title = "Question Title";
		String description = "Question Description";
		ThreadStatus threadStatus = ThreadStatus.Open;
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		RegularUser user = createAndSaveRegularUser(username, email, password);
		Question question = new Question();
		question.setId(id);
		question.setTitle(title);
		question.setDescription(description);
		question.setThreadStatus(threadStatus);
		question.setUser(user);
		questionRepository.save(question);
		
		question = null;
		
		question = questionRepository.findQuestionById(id);
		assertNotNull(question);
		assertNotNull(question.getUser());
		assertEquals(id, question.getId()); 
		assertEquals(title, question.getTitle());
		assertEquals(description, question.getDescription());
		assertEquals(threadStatus, question.getThreadStatus());
		
	}	
	
	@Test 
	public void testDeleteQuestion() {
		Integer id = 57515; 
		String title = "Question Title";
		String description = "Question Description";
		ThreadStatus threadStatus = ThreadStatus.Open;
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		RegularUser user = createAndSaveRegularUser(username, email, password);
		Question question = new Question();
		question.setId(id);
		question.setTitle(title);
		question.setDescription(description);
		question.setThreadStatus(threadStatus);
		question.setUser(user);
		questionRepository.save(question);
		
		question = null; 
		
		question = questionRepository.findQuestionById(id); 
		assertNotNull(question);
		
		questionRepository.delete(question);
		question = questionRepository.findQuestionById(id);
		assertNull(question);
		
	}
	
	public void testUpdateQuestion() {
		Integer id = 57515; 
		String title = "Question Title";
		String description = "Question Description";
		ThreadStatus threadStatus = ThreadStatus.Open;
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		RegularUser user = createAndSaveRegularUser(username, email, password);
		Question question = new Question();
		question.setId(id);
		question.setTitle(title);
		question.setDescription(description);
		question.setThreadStatus(threadStatus);
		question.setUser(user);
		questionRepository.save(question);
		
		question = null; 
		
		question = questionRepository.findQuestionById(id);
		assertNotNull(question);
		
		String newQuestionDescription = "New Question Description";
		ThreadStatus newThreadStatus = ThreadStatus.Closed;
		question.setDescription(newQuestionDescription);
		question.setThreadStatus(newThreadStatus);
		questionRepository.save(question);
		
		question = null; 
		
		question = questionRepository.findQuestionById(id);
		assertNotNull(question);
		assertNotNull(question.getUser());
		assertEquals(id, question.getId()); 
		assertEquals(title, question.getTitle());
		assertEquals(newQuestionDescription, question.getDescription());
		assertEquals(newThreadStatus, question.getThreadStatus());
		
	}
	
	//Lenoy - Tests for Response Class
		@Test
		public void testPersistAndLoadResponse() {
			Integer id = 57515; 
			Integer questionID = 57516;
			String text = "Response";
			String questionTitle = "Question Title";
			String questionDescription = "Question Description";
			ThreadStatus questionThreadStatus = ThreadStatus.Open;
			String username = "testusername";
			String email = "test@testmail.com";
			String password = "123456789";
			RegularUser user = createAndSaveRegularUser(username, email, password);
			Question question = new Question();
			question.setId(questionID);
			question.setTitle(questionTitle);
			question.setDescription(questionDescription);
			question.setThreadStatus(questionThreadStatus);
			question.setUser(user);
			questionRepository.save(question);
			Response response = new Response();
			response.setId(id);
			response.setText(text);
			response.setQuestion(question);
			response.setUser(user);
			responseRepository.save(response);
			
			
			
			response = null;
			
			response = responseRepository.findResponseById(id);
			assertNotNull(response);
			assertEquals(response.getQuestion().getTitle(), questionTitle);
			assertEquals(response.getUser().getUsername(), username);
			assertEquals(id, response.getId()); 
			assertEquals(text, response.getText());
			
		}	
	
		
		@Test 
		public void testDeleteResponse() {
			Integer id = 57515; 
			Integer questionID = 57516;
			String text = "Response";
			String questionTitle = "Question Title";
			String questionDescription = "Question Description";
			ThreadStatus questionThreadStatus = ThreadStatus.Open;
			String username = "testusername";
			String email = "test@testmail.com";
			String password = "123456789";
			RegularUser user = createAndSaveRegularUser(username, email, password);
			Question question = new Question();
			question.setId(questionID);
			question.setTitle(questionTitle);
			question.setDescription(questionDescription);
			question.setThreadStatus(questionThreadStatus);
			question.setUser(user);
			questionRepository.save(question);
			Response response = new Response();
			response.setId(id);
			response.setText(text);
			response.setQuestion(question);
			response.setUser(user);
			responseRepository.save(response);
			
			response = null; 
			
			response = responseRepository.findResponseById(id); 
			assertNotNull(response);
			
			responseRepository.delete(response);
			response = responseRepository.findResponseById(id);
			assertNull(response);
			
		}
		@Test
		public void testUpdateResponse() {
			Integer id = 57515; 
			Integer questionID = 57516;
			String text = "Response";
			String questionTitle = "Question Title";
			String questionDescription = "Question Description";
			ThreadStatus questionThreadStatus = ThreadStatus.Open;
			String username = "testusername";
			String email = "test@testmail.com";
			String password = "123456789";
			RegularUser user = createAndSaveRegularUser(username, email, password);
			Question question = new Question();
			question.setId(questionID);
			question.setTitle(questionTitle);
			question.setDescription(questionDescription);
			question.setThreadStatus(questionThreadStatus);
			question.setUser(user);
			questionRepository.save(question);
			Response response = new Response();
			response.setId(id);
			response.setText(text);
			response.setQuestion(question);
			response.setUser(user);
			responseRepository.save(response);
			
			response = null; 
			
			response = responseRepository.findResponseById(id);
			assertNotNull(response);
			
			String newResponseText = "New Response";
			response.setText(newResponseText);
			responseRepository.save(response);
			
			response = null; 
			
			response = responseRepository.findResponseById(id);
			assertNotNull(response);
			assertEquals(response.getQuestion().getTitle(), questionTitle);
			assertEquals(response.getUser().getUsername(), username);
			assertEquals(id, response.getId()); 
			assertEquals(newResponseText, response.getText());
			
			
		}
	
		
		
		

	////////////////////////////////////////////////////////////////////////////////////////////
	// Helper Methods Starts
	////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Helper method to create sample donation for testing
	 * @param id
	 * @param amount
	 * @param from
	 * @param to
	 * @return a sample donation for testing
	 */
	private Donation createDonation(Integer amount, RegularUser from, PetShelter to) {
		Donation donation = new Donation();
		donation.setAmount(amount);
		donation.setDonatedFrom(from);
		donation.setDonatedTo(to);
		return donation;

	}

	/**
	 * Create sample application for testing
	 * 
	 * @author ANDREW TA
	 * @return AdoptionApplication a new application
	 */
	private AdoptionApplication createAdoptionApplication() {
		String description = "description";
		PetProfile pet = createPetProfile();
		GeneralUser user = createAndSaveRegularUser("test", "test@gmail.com", "test");

		AdoptionApplication application = new AdoptionApplication();
		application.setApplicationDescription(description);
		application.setPetProfile(pet);
		application.setUser(user);
		application = adoptionApplicationRespository.save(application);

		return application;
	}

	/**
	 * Create sample pet profile for testing
	 * 
	 * @author ANDREW TA
	 * @return PetProfile a new petProfile
	 */
	private PetProfile createPetProfile() {

		int age = 3;
		String pet_name = "test_name";
		String description = "description";
		String petSpecies = "species";
		byte[] profile_pic = "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d"
				.getBytes();
		String reason = "reason";
		PetProfile pet = new PetProfile();
		//pet.setId(pet_id);
		pet.setAge(age);
		pet.setDescription(description);
		pet.setPetGender(Gender.Female);
		pet.setPetName(pet_name);
		pet.setPetSpecies(petSpecies);
		pet.setUser(createAndSaveRegularUser("testusername", "test@gmail.com", "admin"));
		pet.setProfilePicture(profile_pic);
		pet.setReason(reason);
		pet = petProfileRespository.save(pet);
		return pet;
	}

	/**
	 * @return a sample pet shelter for testing
	 */
	private PetShelter createPetShelter() {
		PetShelter shelter = new PetShelter();
		shelter.setUsername("testshelter");
		shelter.setPassword("testshelterpassword");
		shelter.setName("Test Shelter");
		shelter.setPhone("123-456-7890");
		shelter.setEmail("testshelter@adopt.com");
		shelter.setBalance(100);
		return shelter;
	}

	/**
	 * Helper method to create sample user for testing
	 * 
	 * @author Shuby, Andrew
	 * @param username
	 * @param email
	 * @param password
	 * @return a sample regular user
	 */
	private RegularUser createAndSaveRegularUser(String username, String email, String password) {
		RegularUser user = new RegularUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		regularUserRepository.save(user);
		return user;
	}

	/**
	 *Helper method to create sample address for testing
	 *
	 * @param street
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param country
	 * @return A sample address for testing
	 */
	private Address createAddress(String street, String city, String state, String postalCode,
			String country) {
		Address address = new Address();
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setPostalCode(postalCode);
		address.setCountry(country);
		return address;
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	// Helper Methods Ends
	////////////////////////////////////////////////////////////////////////////////////////////

}

