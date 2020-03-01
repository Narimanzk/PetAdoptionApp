package ca.mcgill.ecse321.petadoptionapp.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.Response;
import ca.mcgill.ecse321.petadoptionapp.model.Gender;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.UserType;
import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.Donation;
import ca.mcgill.ecse321.petadoptionapp.model.ThreadStatus;

import ca.mcgill.ecse321.petadoptionapp.dao.AdoptionApplicationRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.GeneralUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.QuestionRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.ResponseRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.PetProfileRespository;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.ApplicationStatus;
import ca.mcgill.ecse321.petadoptionapp.dao.AddressRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.DonationRepository;

@ExtendWith(MockitoExtension.class)
public class TestPetAdoptionAppService {
	@Mock
	private GeneralUserRepository generalUserDao;
	@Mock 
	private QuestionRepository questionDao;
	@Mock
	private ResponseRepository responseDao;
	@Mock
	private PetProfileRespository petProfileDao;
	@Mock
	private AdoptionApplicationRespository adoptionApplicationDao;
	@Mock
	private AddressRepository addressDao;
	@Mock
	private DonationRepository donationDao;

	@InjectMocks
	private PetAdoptionAppService service;

    //Map for simulating a database.
    private static HashMap<String, GeneralUser> hmp = new HashMap<>();
	// Map for simulating an adoption application
    private static HashMap<Integer, AdoptionApplication> app_map = new HashMap<Integer, AdoptionApplication>();
    // Map for simulating a petprofile table
    private static HashMap<Integer, PetProfile> pet_map = new HashMap<Integer, PetProfile>();
    // Map for simulating a address table
    private static HashMap<Integer, Address> addressMap = new HashMap<>();
    // Map for simulating a donation table
    private static HashMap<Integer, Donation> donationMap = new HashMap<>();
    //Map for simulating response databse
    private static HashMap<Integer, Response> responseMap = new HashMap<>();
    //Map for simulating a question database
    private static HashMap<Integer, Question> questionMap = new HashMap<>();
    
    // Param for existing user.
    private static final String USER_KEY = "TestUser";
    private static final UserType USER_USERTYPE = UserType.Owner;
    private static final String USER_EMAIL = "cooluser@email.com";
    private static final String USER_PASSWORD = "abcdef1234!";
    private static final String USER_NAME = "Steve";
    private static final byte[] USER_PROFILEPICTURE = new byte[] { (byte) 0xf5 };
    private static final String USER_DESCRIPTION = "Test Description";
    private static final String NONEXISTING_KEY = "NotAUser";
    
    
    //Param for existing question
    private static final Integer QUESTION_ID = 2552;
    private static final String QUESTION_TITLE = "QuestionTitle";
    private static final String QUESTION_DESCRIPTION = "QuestionDescription";
    private static final ThreadStatus QUESTION_STATUS = ThreadStatus.Open;
    private static final Integer NONEXISTING_QUESTION_ID = 2755;
    private static final List<Response> QUESTION_RESPONSES = new ArrayList<Response>();
    
    //Param for existing response
    private static final Integer RESPONSE_ID = 3552;
    private static final String RESPONSE_TEXT = "Response Text";
    
    
    // Parameters for existing pet profile
	private static final Integer PET_ID = 5;
	private static final String PET_NAME = "TestPet";
	private static final int PET_AGE = 2;
	private static final String PET_DESCRIPTION = "desciption";
	private static final String PET_REASON = "reason";
	private static final Gender PET_GENDER = Gender.Female;
	private static final byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
	private static final String PET_SPECIES = "species";

	// Parameters for existing adoption application
	private static final Integer APP_ID = 1;
	private static final String APP_DESCRIPTION = "description";
	private static final ApplicationStatus APP_STATUS = ApplicationStatus.Accepted;

	//Param for existing address.
	private static final Integer ADDRESS_KEY = 7777;
	private static final String ADDRESS_STREET = "testStreet";
	private static final String ADDRESS_CITY = "testCity";
	private static final String ADDRESS_STATE = "testState";
	private static final String ADDRESS_POSTALCODE = "testPostalCode";
	private static final String ADDRESS_COUNTRY = "testCountry";
	private static final Integer NONEXISTING_ADDRESS_KEY = 0000;
	
	//Param for existing donation
	private static final Integer DONATION_KEY = 7777;
	private static final Integer DONATION_AMOUNT = 1234;
	private static final GeneralUser DONATION_DONOR = new GeneralUser();
	private static final GeneralUser DONATION_RECIPIENT = new GeneralUser();
	private static final Integer NONEXISTING_DONATION_KEY = 0000;
	
	@BeforeEach
	public void setMockOutput() {
	// Create a existing user
      GeneralUser user = new GeneralUser();
      user.setUsername(USER_KEY);
      user.setUserType(USER_USERTYPE);
      user.setEmail(USER_EMAIL);
      user.setName(USER_NAME);
      user.setProfilePicture(USER_PROFILEPICTURE);
      user.setDescription(USER_DESCRIPTION);
      user.setPassword(USER_PASSWORD);
      hmp.put(USER_KEY, user);

      // Create a new pet profile
      PetProfile pet = new PetProfile();
      pet.setId(PET_ID);
      pet.setAge(PET_AGE);
      pet.setDescription(PET_DESCRIPTION);
      pet.setPetGender(PET_GENDER);
      pet.setPetName(PET_NAME);
      pet.setPetSpecies(PET_SPECIES);
      pet.setProfilePicture(PET_PROFILEPIC);
      pet.setReason(PET_REASON);
      pet.setUser(user);
      pet_map.put(PET_ID, pet);

      // Create a new adoption application
      AdoptionApplication app = new AdoptionApplication();
      app.setApplicationDescription(APP_DESCRIPTION);
      app.setApplicationStatus(APP_STATUS);
      app.setUser(user);
      app.setPetProfile(pet);
      app_map.put(APP_ID, app);
      
      // Create an existing address
      Address address =  new Address();
      address.setId(ADDRESS_KEY);
      address.setStreet(ADDRESS_STREET);
      address.setCity(ADDRESS_CITY);
      address.setState(ADDRESS_STATE);
      address.setPostalCode(ADDRESS_POSTALCODE);
      address.setCountry(ADDRESS_COUNTRY);
      addressMap.put(ADDRESS_KEY,address);
  
      // Create an existing donation
      Donation donation = new Donation();
      donation.setId(DONATION_KEY);
      donation.setAmount(DONATION_AMOUNT);
      donation.setDonatedFrom(DONATION_DONOR);
      donation.setDonatedTo(DONATION_RECIPIENT);
      donationMap.put(DONATION_KEY, donation);

      //Create an existing question
      Question question = new Question();
      question.setId(QUESTION_ID);
      question.setTitle(QUESTION_TITLE);
      question.setDescription(QUESTION_DESCRIPTION);
      question.setThreadStatus(QUESTION_STATUS);
      question.setUser(hmp.get(USER_KEY));
      questionMap.put(QUESTION_ID, question);
      
      // Create an existing response 
      Response response = new Response();
      response.setId(RESPONSE_ID);
      response.setText(RESPONSE_TEXT);
      response.setQuestion(questionMap.get(QUESTION_ID));
      response.setUser(hmp.get(USER_KEY));
      responseMap.put(RESPONSE_ID, response);
      
      // *********** Setup mock data for GeneralUser ************************
      // Save General User
      lenient().when(generalUserDao.save(any(GeneralUser.class))).thenAnswer((InvocationOnMock invocation) -> {
          hmp.put(((GeneralUser) invocation.getArgument(0)).getUsername(), invocation.getArgument(0));
          return invocation.getArgument(0);
      });
	  //Save General User
			lenient().when(generalUserDao.save(any(GeneralUser.class))).thenAnswer((InvocationOnMock invocation) -> {
				hmp.put(((GeneralUser)invocation.getArgument(0)).getUsername(),invocation.getArgument(0));
				return invocation.getArgument(0);
			});
		//Save Address
			lenient().when(addressDao.save(any(Address.class))).thenAnswer((InvocationOnMock invocation) -> {
				addressMap.put(((Address)invocation.getArgument(0)).getId(),invocation.getArgument(0));
				return invocation.getArgument(0);
			});
		//Save Donation
			lenient().when(donationDao.save(any(Donation.class))).thenAnswer((InvocationOnMock invocation) -> {
				donationMap.put(((Donation)invocation.getArgument(0)).getId(),invocation.getArgument(0));
				return invocation.getArgument(0);
			});	
		// Find General User by username
		lenient().when(generalUserDao.findGeneralUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			return hmp.get(invocation.getArgument(0));
		});

		// ************* Setup mock data for PetProfile *****************************
		// Save PetProfile
		lenient().when(petProfileDao.save(any(PetProfile.class))).thenAnswer((InvocationOnMock invocation) -> {
			pet_map.put(((PetProfile) invocation.getArgument(0)).getId(), invocation.getArgument(0));
			return invocation.getArgument(0);
		});

		// find pet profile by id
		lenient().when(petProfileDao.findPetProfileById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			return pet_map.get(invocation.getArgument(0));
		});

		// find pet profile by user
		lenient().when(petProfileDao.findByUser(any(GeneralUser.class))).thenAnswer((InvocationOnMock invocation) -> {
			ArrayList<PetProfile> pets = new ArrayList<>();
			pets.add(pet_map.get(PET_ID));
			return pets;
		});
		
		// find pet profile by application
		lenient().when(petProfileDao.findByAdoptionApplications(any(AdoptionApplication.class))).thenAnswer((InvocationOnMock invocation) -> {
			return pet_map.get(PET_ID);
		});
		
		// ***************** Setup mock data for adoption application// *******************
		// Save Application
		lenient().when(adoptionApplicationDao.save(any(AdoptionApplication.class))).thenAnswer((InvocationOnMock invocation) -> {
			app_map.put(((AdoptionApplication) invocation.getArgument(0)).getId(), invocation.getArgument(0));
			return invocation.getArgument(0);
		});
		
		// Get Application by id
		lenient().when(adoptionApplicationDao.findAdoptionApplicationById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			return app_map.get(invocation.getArgument(0));
		});
		
		//get application by user
		lenient().when(adoptionApplicationDao.findByUser(any(GeneralUser.class))).thenAnswer((InvocationOnMock invocation) -> {
			ArrayList<AdoptionApplication> apps = new ArrayList<>();
			apps.add(app_map.get(APP_ID));
			return apps;
		});

		//Save Question 
			lenient().when(questionDao.save(any(Question.class))).thenAnswer((InvocationOnMock invocation) -> {
				questionMap.put(((Question)invocation.getArgument(0)).getId(), invocation.getArgument(0));
				return invocation.getArgument(0);
			});
		//Find Question by ID
			lenient().when(questionDao.findQuestionById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
				return questionMap.get(invocation.getArgument(0));
			});

		//Save Response
			lenient().when(responseDao.save(any(Response.class))).thenAnswer((InvocationOnMock invocation) -> {
				responseMap.put(((Response)invocation.getArgument(0)).getId(), invocation.getArgument(0));
				return invocation.getArgument(0);
			});
		//Find Response by ID
			lenient().when(responseDao.findResponseById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
				return responseMap.get(invocation.getArgument(0));
			});

		//get application by pet profile
		lenient().when(adoptionApplicationDao.findByPetProfile(any(PetProfile.class))).thenAnswer((InvocationOnMock invocation) -> {
			ArrayList<AdoptionApplication> apps = new ArrayList<>();
			apps.add(app_map.get(APP_ID));
			return apps;
		});
		// Find Address by id
			lenient().when(addressDao.findAddressById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
				return addressMap.get(invocation.getArgument(0));
					});	
		//Find Donation by id
			lenient().when(donationDao.findDonationById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
				return donationMap.get(invocation.getArgument(0));
					});
		//Find Donation by DonatedFrom
			lenient().when(donationDao.findByDonatedFrom(any(GeneralUser.class))).thenAnswer((InvocationOnMock invocation) -> {
				ArrayList<Donation> donations = new ArrayList<>();
				donations.add(donationMap.get(DONATION_KEY));
				return donations;
			});
		//Find Donation by DonatedTo
			lenient().when(donationDao.findByDonatedTo(any(GeneralUser.class))).thenAnswer((InvocationOnMock invocation) -> {
				ArrayList<Donation> donations = new ArrayList<>();
				donations.add(donationMap.get(DONATION_KEY));
				return donations;
			});	
	}

	// ************* Test GeneralUser service ***********************
	@Test
	public void testCreateGeneralUser() {
		assertEquals(0, service.getAllGeneralUsers().size());

		String username = "CoolUser123";
		UserType userType = UserType.Owner;
		String email = "cooluser@email.com";
		String password = "abcdef1234!";
		String name = "Steve";
		GeneralUser user = null;

		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(userType, user.getUserType());
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(name, user.getName());
	}

	@Test
	public void testCreateGeneralUserNull() {
		String username = null;
		UserType userType = null;
		String email = null;
		String password = null;
		String name = null;
		String error = null;
		GeneralUser user = null;

		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals(
				"User needs a username. User needs a user type. User needs an email. User needs a password. User needs a name.",
				error);
	}

	@Test
	public void testCreateGeneralUserEmpty() {
		String username = "";
		UserType userType = UserType.Admin;
		String email = "";
		String password = "";
		String name = "";
		String error = null;
		GeneralUser user = null;

		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals("User needs a username. User needs an email. User needs a password. User needs a name.", error);
	}

	@Test
	public void testCreateGeneralUserSpaces() {
		String username = "  ";
		UserType userType = UserType.Admin;
		String email = "   ";
		String password = " ";
		String name = "    ";
		String error = "";
		GeneralUser user = null;

		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals("User needs a username. User needs an email. User needs a password. User needs a name.", error);
	}

	@Test
	public void testUpdateGeneralUser() {
		String username = USER_KEY;
		String newEmail = "differentuser@website.ca";
		String newPassword = "zyxvut9876$";
		byte[] newProfilePicture = new byte[] { (byte) 0xe0 };
		String newDescription = "Some stuff about me";

		GeneralUser user = null;
		user = service.updateGeneralUser(username, newEmail, newPassword, newProfilePicture, newDescription);

		assertNotNull(user);
		assertEquals(newPassword, user.getPassword());
		assertEquals(newEmail, user.getEmail());

		assertEquals(newProfilePicture, user.getProfilePicture());
		assertEquals(newDescription, user.getDescription());
	}

	@Test
	public void testUpdateGeneralUserNull() {
		String username = USER_KEY;
		GeneralUser user = null;

		user = service.updateGeneralUser(username, null, null, null, null);

		assertNotNull(user);
		assertEquals(USER_EMAIL, user.getEmail());
		assertEquals(USER_PASSWORD, user.getPassword());
		assertEquals(USER_PROFILEPICTURE, user.getProfilePicture());
		assertEquals(USER_DESCRIPTION, user.getDescription());
	}

	@Test
	public void testUpdateGeneralUserEmpty() {
		String username = USER_KEY;
		GeneralUser user = null;
		String emptyDescription = "";

		user = service.updateGeneralUser(username, USER_EMAIL, USER_PASSWORD, USER_PROFILEPICTURE, emptyDescription);

		assertNotNull(user);
		assertEquals(USER_EMAIL, user.getEmail());
		assertEquals(USER_PASSWORD, user.getPassword());
		assertEquals(USER_PROFILEPICTURE, user.getProfilePicture());
		assertEquals(emptyDescription, user.getDescription());
	}

	@Test
	public void testUpdateGeneralUserSpaces() {
		String username = USER_KEY;
		GeneralUser user = null;
		String newDescription = "   ";

		user = service.updateGeneralUser(username, USER_EMAIL, USER_PASSWORD, USER_PROFILEPICTURE, newDescription);

		assertNotNull(user);
		assertEquals(USER_EMAIL, user.getEmail());
		assertEquals(USER_PASSWORD, user.getPassword());
		assertEquals(newDescription, user.getDescription());
	}

	@Test
	public void testGetExistingPerson() {
		assertEquals(USER_KEY, service.getGeneralUser(USER_KEY).getUsername());
	}

	@Test
	public void testGetNonExistingPerson() {
		assertNull(service.getGeneralUser(NONEXISTING_KEY));
	}

	@Test
	public void testDeleteGeneralUser() {
		// Delete general user by id
		doAnswer((i) -> {
			hmp.remove(i.getArgument(0));
			return null;
		}).when(generalUserDao).deleteById(anyString());
		String username = "CoolUser123";
		UserType userType = UserType.Owner;
		String email = "cooluser@email.com";
		String password = "abcdef1234!";
		String name = "Steve";
		GeneralUser user;
		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			service.deleteGeneralUser(username);
		} catch (IllegalArgumentException e) {
			fail();
		}
		user = service.getGeneralUser(username);
		assertNull(user);
	}

	@Test
	public void testDeleteGeneralUserNull() {
		doThrow(IllegalArgumentException.class).when(generalUserDao).deleteById(isNull());
		String username = null;
		try {
			service.deleteGeneralUser(username);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}

	// ************************ Test PetProfile service *******************
	@Test
	public void testCreatePetProfile() {
		assertEquals(0, service.getAllPetProfile().size());
		Integer PET_ID = -1;
		String PET_NAME = "TestPet";
		int PET_AGE = 2;
		String PET_DESCRIPTION = "desciption";
		String PET_REASON = "reason";
		Gender PET_GENDER = Gender.Female;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
		String PET_SPECIES = "species";
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		try {
			pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, PET_SPECIES, 
					PET_PROFILEPIC, PET_REASON, user, PET_ID);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(pet);
		assertEquals(PET_NAME, pet.getPetName());
		assertEquals(PET_AGE, pet.getAge());
		assertEquals(PET_DESCRIPTION, pet.getDescription());
		assertEquals(PET_REASON, pet.getReason());
		assertEquals(PET_GENDER, pet.getPetGender());
		assertArrayEquals(PET_PROFILEPIC, pet.getProfilePicture());
		assertEquals(PET_SPECIES, pet.getPetSpecies());
		assertEquals(USER_KEY, pet.getUser().getUsername());
	}
	
	@Test
	public void testCreatePetProfileNull() {
		Integer PET_ID = -1;
		String PET_NAME = null;
		int PET_AGE = 0;
		String PET_DESCRIPTION = null;
		String PET_REASON = null;
		Gender PET_GENDER = Gender.Female;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
		String PET_SPECIES = null;
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		String error = "";
		try {
			pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, PET_SPECIES, 
					PET_PROFILEPIC, PET_REASON, user, PET_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(pet);
		assertEquals(
				"Pet needs a name. Pet needs a description. Pet needs a species. Pet needs a reason.",
				error);
	}

	@Test
	public void testCreatePetProfileEmpty() {
		Integer PET_ID = -1;
		String PET_NAME = "";
		int PET_AGE = 0;
		String PET_DESCRIPTION = "";
		String PET_REASON = "";
		Gender PET_GENDER = Gender.Female;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
		String PET_SPECIES = "";
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		String error = "";
		try {
			pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, PET_SPECIES, 
					PET_PROFILEPIC, PET_REASON, user, PET_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(pet);
		assertEquals(
				"Pet needs a name. Pet needs a description. Pet needs a species. Pet needs a reason.",
				error);
	}

	@Test
	public void testCreatePetProfileSpaces() {
		Integer PET_ID = -1;
		String PET_NAME = "  ";
		int PET_AGE = 0;
		String PET_DESCRIPTION = "   ";
		String PET_REASON = "   ";
		Gender PET_GENDER = Gender.Female;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
		String PET_SPECIES = "   ";
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		String error = "";
		try {
			pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, PET_SPECIES, 
					PET_PROFILEPIC, PET_REASON, user, PET_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(pet);
		assertEquals(
				"Pet needs a name. Pet needs a description. Pet needs a species. Pet needs a reason.",
				error);
	}
	
	@Test 
	public void testUpdatePetProfile() {
		String PET_NAME = "TestPet2";
		int PET_AGE = 3;
		String PET_DESCRIPTION = "new desciption";
		String PET_REASON = "new reason";
		Gender PET_GENDER = Gender.Male;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xff };
		String PET_SPECIES = "new species";
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, PET_SPECIES, 
				PET_PROFILEPIC, PET_REASON, user, PET_ID);
		
		assertNotNull(pet);
		assertEquals(PET_ID, pet.getId());
		assertEquals(PET_NAME, pet.getPetName());
		assertEquals(PET_AGE, pet.getAge());
		assertEquals(PET_DESCRIPTION, pet.getDescription());
		assertEquals(PET_REASON, pet.getReason());
		assertEquals(PET_GENDER, pet.getPetGender());
		assertArrayEquals(PET_PROFILEPIC, pet.getProfilePicture());
		assertEquals(PET_SPECIES, pet.getPetSpecies());
		assertEquals(USER_KEY, pet.getUser().getUsername());
	}
	
	@Test
	public void testUpdatePetProfileNull() {
		String PET_NAME = null;
		int PET_AGE = 0;
		String PET_DESCRIPTION = null;
		String PET_REASON = null;
		Gender PET_GENDER = Gender.Female;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
		String PET_SPECIES = null;
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		String error = "";
		try {
			pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, PET_SPECIES, 
					PET_PROFILEPIC, PET_REASON, user, PET_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(pet);
		assertEquals(
				"Pet needs a name. Pet needs a description. Pet needs a species. Pet needs a reason.",
				error);
	}

	@Test
	public void testUpdatePetProfileEmpty() {
		String PET_NAME = "";
		int PET_AGE = 0;
		String PET_DESCRIPTION = "";
		String PET_REASON = "";
		Gender PET_GENDER = Gender.Female;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
		String PET_SPECIES = "";
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		String error = "";
		try {
			pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, PET_SPECIES, 
					PET_PROFILEPIC, PET_REASON, user, PET_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(pet);
		assertEquals(
				"Pet needs a name. Pet needs a description. Pet needs a species. Pet needs a reason.",
				error);
	}

	@Test
	public void testUpdatePetProfileSpaces() {
		String PET_NAME = "  ";
		int PET_AGE = 0;
		String PET_DESCRIPTION = "   ";
		String PET_REASON = "   ";
		Gender PET_GENDER = Gender.Female;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
		String PET_SPECIES = "   ";
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		String error = "";
		try {
			pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, PET_SPECIES, 
					PET_PROFILEPIC, PET_REASON, user, PET_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(pet);
		assertEquals(
				"Pet needs a name. Pet needs a description. Pet needs a species. Pet needs a reason.",
				error);
	}
	
	@Test
	public void testGetPetProfileById() {
		PetProfile pet = service.getPetProfileById(PET_ID);
		assertNotNull(pet);
		assertEquals(PET_ID, pet.getId());
	}
	
	@Test
	public void testGetPetProfileDoestNotExist() {
		assertNull(service.getPetProfileById(1234));
	}
	
	@Test 
	public void testGetPetProfileByUser() {
		GeneralUser user = createMockUser();
		List<PetProfile> pets = service.getPetProfileByUser(user);
		assertNotEquals(0, pets.size());
	}
	
	@Test
	public void testGetPetProfileByApplication() {
		AdoptionApplication app = createMockApp();
		PetProfile pet = service.getPetProfileByApplication(app);
		assertEquals(PET_ID, pet.getId());
	}
	
	@Test
	public void testDeletePetProfileById() {
		doAnswer((i) -> {
			pet_map.remove(i.getArgument(0));
			return null;
		}).when(petProfileDao).deleteById(anyInt());
		
		String PET_NAME = "TestPet2";
		int PET_AGE = 3;
		String PET_DESCRIPTION = "new desciption";
		String PET_REASON = "new reason";
		Gender PET_GENDER = Gender.Male;
		byte[] PET_PROFILEPIC = new byte[] { (byte) 0xff };
		String PET_SPECIES = "new species";
		
		PetProfile pet = null;
		GeneralUser user = createMockUser();
		try {
			pet = service.createOrUpdatePetProfile(PET_NAME, PET_AGE, PET_GENDER, PET_DESCRIPTION, 
					PET_SPECIES, PET_PROFILEPIC, PET_REASON, user, PET_ID);
	         service.deletePetProfile(PET_ID);
	        } catch (IllegalArgumentException e) {
	            fail();
	        }
	        pet = service.getPetProfileById(PET_ID);
	        assertNull(pet);
	    }
	    
	    @Test
	    public void testDeltePetProfileDoesNotExist() {
	        assertNull(service.deletePetProfile(1234));
	    }
	    
	    
	    //*****************************  Test for Adoption Application
	    
	    @Test
	    public void testCreateApplication() {
	        Integer APP_ID = -1;
	        String desc = "desc";
	        ApplicationStatus status = ApplicationStatus.Accepted;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	        } catch (IllegalArgumentException e) {
	            fail();
	        }
	        assertNotNull(app);
	        assertEquals(desc, app.getApplicationDescription());
	        assertEquals(status, app.getApplicationStatus());
	        assertEquals(pet.getId(), app.getPetProfile().getId());
	        assertEquals(user.getUsername(), app.getUser().getUsername());
	    }
	    
	    @Test
	    public void testCreateNullApplication() {
	        Integer APP_ID = -1;
	        String desc = null;
	        ApplicationStatus status = ApplicationStatus.Accepted;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        String error = "";
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	        } catch (IllegalArgumentException e) {
	            error = e.getMessage();
	        }
	        assertNull(app);
	        assertEquals("Application needs a description.", error);
	    }
	    
	    @Test
	    public void testCreateEmptyApplication() {
	        Integer APP_ID = -1;
	        String desc = "";
	        ApplicationStatus status = ApplicationStatus.Accepted;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        String error = "";
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	        } catch (IllegalArgumentException e) {
	            error = e.getMessage();
	        }
	        assertNull(app);
	        assertEquals("Application needs a description.", error);
	    }
	    
	    @Test
	    public void testCreateSpaceApplication() {
	        Integer APP_ID = -1;
	        String desc = "   ";
	        ApplicationStatus status = ApplicationStatus.Accepted;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        String error = "";
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	        } catch (IllegalArgumentException e) {
	            error = e.getMessage();
	        }
	        assertNull(app);
	        assertEquals("Application needs a description.", error);
	    }
	    
	    @Test
	    public void testUpdateApplication() {
	        String desc = "new_desc";
	        ApplicationStatus status = ApplicationStatus.InReview;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	        } catch (IllegalArgumentException e) {
	            fail();
	        }
	        assertNotNull(app);
	        assertEquals(desc, app.getApplicationDescription());
	        assertEquals(status, app.getApplicationStatus());
	        assertEquals(pet.getId(), app.getPetProfile().getId());
	        assertEquals(user.getUsername(), app.getUser().getUsername());
	    }
	    
	    @Test
	    public void testUpdateNullApplication() {
	        String desc = null;
	        ApplicationStatus status = ApplicationStatus.Accepted;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        String error = "";
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	        } catch (IllegalArgumentException e) {
	            error = e.getMessage();
	        }
	        assertNull(app);
	        assertEquals("Application needs a description.", error);
	    }
	    
	    @Test
	    public void testUpdateEmptyApplication() {
	        String desc = "";
	        ApplicationStatus status = ApplicationStatus.Accepted;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        String error = "";
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	        } catch (IllegalArgumentException e) {
	            error = e.getMessage();
	        }
	        assertNull(app);
	        assertEquals("Application needs a description.", error);
	    }
	    
	    @Test
	    public void testUpdateSpaceApplication() {
	        String desc = "   ";
	        ApplicationStatus status = ApplicationStatus.Accepted;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        String error = "";
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	        } catch (IllegalArgumentException e) {
	            error = e.getMessage();
	        }
	        assertNull(app);
	        assertEquals("Application needs a description.", error);
	    }
	    
	    @Test
	    public void testGetApplicationById() {
	        AdoptionApplication app = null;
	        app = service.getApplicaiontById(APP_ID);
	        assertNotNull(app);
	    }
	    
	    @Test
	    public void testGetApplicationByUser() {
	        GeneralUser user = createMockUser();
	        List<AdoptionApplication> apps = service.getApplicationByUser(user);
	        assertNotEquals(0, apps.size());
	    }
	    
	    @Test
	    public void testGetApplicationByPet() {
	        PetProfile pet = createMockPetProfile();
	        List<AdoptionApplication> apps = service.getApplicationByPetProfile(pet);
	        assertNotEquals(0, apps.size());
	    }
	    
	    @Test
	    public void testApplicationNotExist() {
	        assertNull(service.getApplicaiontById(1234));
	    }
	    
	    @Test 
	    public void testDeleteApplication() {
	        doAnswer((i) -> {
	            app_map.remove(i.getArgument(0));
	            return null;
	        }).when(adoptionApplicationDao).deleteById(anyInt());
	        
	        String desc = "new desc";
	        ApplicationStatus status = ApplicationStatus.Rejected;
	        
	        PetProfile pet = createMockPetProfile();
	        GeneralUser user = createMockUser();
	        AdoptionApplication app = null;
	        
	        try {
	            app = service.createOrUpdateAdoptionApplication(desc, status, user, pet, APP_ID);
	            service.deleteApplication(APP_ID);
	        } catch (IllegalArgumentException e) {
	            fail();
	        }
	        app = service.getApplicaiontById(APP_ID);
	        assertNull(app);
	    }
	    
	    @Test
	    public void testDeleteApplicationNotExist() {
	        assertNull(service.getApplicaiontById(1234));
	    }
	    
	    public PetProfile createMockPetProfile() {
	        PetProfile pet = new PetProfile();
	        pet.setId(PET_ID);
	        return pet;
	    }
	    
	    public GeneralUser createMockUser() {
	        GeneralUser user = new GeneralUser();
	        user.setUsername(USER_KEY);
	        return user;
	    }
	    
	    public AdoptionApplication createMockApp() {
	        AdoptionApplication app = new AdoptionApplication();
	        app.setId(APP_ID);
	        return app;
	    }
	@Test
	public void testCreateAddress() {
		assertEquals(0, service.getAllAddresses().size());
		
		String street = "mcgillSt";
		String city = "montreal";
		String state = "QC";
		String postalCode = "A1A1A1";
		String country = "CA";
		Address address = null;
		
		try {
			address = service.createAddress(street, city, state, postalCode, country);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(address);
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(state, address.getState());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(country, address.getCountry());
	}
	
	@Test
	public void testCreateAddressNull() {
		String street = null;
		String city = null;
		String state = null;
		String postalCode = null;
		String country = null;
		String error = null;
		Address address = null;
		
		try {
			address = service.createAddress(street, city, state, postalCode, country);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(address);
		assertEquals("Address needs a street. Address needs a city. Address needs a state. Address needs a postalCode. Address needs a country.",
				error);
	}
	
	@Test
	public void testCreateAddressEmpty() {
		String street = "";
		String city = "";
		String state = "";
		String postalCode = "";
		String country = "";
		String error = null;
		Address address = null;

		
		try {
			address = service.createAddress(street, city, state, postalCode, country);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(address);
		assertEquals("Address needs a street. Address needs a city. Address needs a state. Address needs a postalCode. Address needs a country.",
				error);
	}
	
	@Test
	public void testCreateAddressSpaces() {
		String street = "  ";
		String city = "  ";
		String state = "  ";
		String postalCode = "  ";
		String country = "  ";
		String error = "";
		Address address = null;
		
		try {
			address = service.createAddress(street, city, state, postalCode, country);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(address);
		assertEquals("Address needs a street. Address needs a city. Address needs a state. Address needs a postalCode. Address needs a country.",
				error);
	}
	
	@Test
	public void testUpdateAddress() {
		Integer id = ADDRESS_KEY;
		String newStreet = "newcgillSt";
		String newCity = "newmontreal";
		String newState = "newQC";
		String newPostalCode = "newA1A1A1";
		String newCountry = "newCA";
		Address address = null;
		address = service.updateAddress(id, newStreet, newCity, newState, newPostalCode, newCountry);
		
		assertNotNull(id);
		assertEquals(newStreet, address.getStreet());
		assertEquals(newCity, address.getCity());
		assertEquals(newState, address.getState());
		assertEquals(newPostalCode, address.getPostalCode());
		assertEquals(newCountry, address.getCountry());
	}
	
	@Test
	public void testUpdateAddressNull() {
		Integer id = ADDRESS_KEY;
		Address address = null;
		
		address = service.updateAddress(id, null, null, null, null, null);
		
		assertNotNull(id);
		assertEquals(ADDRESS_STREET, address.getStreet());
		assertEquals(ADDRESS_CITY, address.getCity());
		assertEquals(ADDRESS_STATE, address.getState());
		assertEquals(ADDRESS_POSTALCODE, address.getPostalCode());
		assertEquals(ADDRESS_COUNTRY, address.getCountry());
	}
	
	@Test
	public void testUpdateAddressEmpty() {
		Integer id = ADDRESS_KEY;
		Address address = null;
		String emptyCountry = "";

		address = service.updateAddress(id, ADDRESS_STREET, ADDRESS_CITY, ADDRESS_STATE, ADDRESS_POSTALCODE, emptyCountry);
		
		assertNotNull(id);
		assertEquals(ADDRESS_STREET, address.getStreet());
		assertEquals(ADDRESS_CITY, address.getCity());
		assertEquals(ADDRESS_STATE, address.getState());
		assertEquals(ADDRESS_POSTALCODE, address.getPostalCode());
		assertEquals(ADDRESS_COUNTRY, address.getCountry());
	}
	
	@Test
	public void testUpdateAddressSpaces() {
		Integer id = ADDRESS_KEY;
		Address address = null;
		String spacedCountry = "  ";

		address = service.updateAddress(id, ADDRESS_STREET, ADDRESS_CITY, ADDRESS_STATE, ADDRESS_POSTALCODE, spacedCountry);
		
		assertNotNull(id);
		assertEquals(ADDRESS_STREET, address.getStreet());
		assertEquals(ADDRESS_CITY, address.getCity());
		assertEquals(ADDRESS_STATE, address.getState());
		assertEquals(ADDRESS_POSTALCODE, address.getPostalCode());
		assertEquals(ADDRESS_COUNTRY, address.getCountry());
	}
	
	@Test
	public void testGetExistingAddress() {
		assertEquals(ADDRESS_KEY, service.getAddress(ADDRESS_KEY).getId());
	}
	
	@Test
	public void testGetNonExistingAddress() {
		assertNull(service.getAddress(NONEXISTING_ADDRESS_KEY));
	}
	
	@Test
	public void testDeleteAddress() {
		// Delete address by id
		doAnswer((i) -> {addressMap.remove(i.getArgument(0));
				return null;}).when(addressDao).deleteById(isNull());
		String street = "mcgillSt";
		String city = "montreal";
		String state = "QC";
		String postalCode = "A1A1A1";
		String country = "CA";
		Address address = new Address();
		try {
			address = service.createAddress(street, city, state, postalCode, country);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			service.deleteAddress(address.getId());
		} catch (IllegalArgumentException e) {
			fail();
		}
		address = service.getAddress(address.getId());
		assertNull(address);
	}
	
	@Test
	public void testDeleteAddressNull() {
		doThrow(IllegalArgumentException.class).when(addressDao).deleteById(isNull());
		Integer id = null;
		try {
			service.deleteAddress(id);
		} catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }
//~~~~~~~~~~~~ QUESTION TESTS ~~~~~~~~~~~~~~~~~~~

@Test 
public void testCreateQuestion() {
    assertEquals(0, service.getAllQuestions().size());
    Integer id = 2553;
    String title = "QuestionTitle";
    String description = "QuestionDescription";
    ThreadStatus status = ThreadStatus.Open;
    GeneralUser author = service.getGeneralUser(USER_KEY);
    Question question = null;
    
    try {
        question = service.createQuestion(id,title, description, status, author);
    } catch(IllegalArgumentException e) {
        fail();
    }
    assertNotNull(question);
    assertEquals(title, question.getTitle());
    assertEquals(description, question.getDescription());
    assertEquals(status, question.getThreadStatus());
    assertEquals(author, question.getUser());
}

@Test
public void testCreateQuestionNull() {
    Integer id = 2535;
    String title = null;
    String description = null;
    ThreadStatus status = null;
    GeneralUser author = null;
    String error = null;
    Question question = null;
    
    try {
        question = service.createQuestion(id, title, description, status, author);
    } catch(IllegalArgumentException e) {
        error = e.getMessage();
    }
    
    assertNull(question);
    assertEquals("Question needs a title. Question needs a description. Question needs a status. Question needs a user.", error);
    
}

@Test
public void testCreateQuestionEmpty() {
    Integer id = 2553;
    String title = "";
    String description = "";
    ThreadStatus status = null;
    GeneralUser author = null;
    String error = null;
    Question question = null;
    

    try {
        question = service.createQuestion(id, title, description, status, author);
    } catch(IllegalArgumentException e) {
        error = e.getMessage();
    }
    
    assertNull(question);
    assertEquals("Question needs a title. Question needs a description. Question needs a status. Question needs a user.", error);
}

@Test
public void testCreateQuestionSpaces() {
    Integer id = 2553;
    String title = "  ";
    String description = "  ";
    ThreadStatus status = null;
    GeneralUser author = null;
    String error = null;
    Question question = null;
    

    try {
        question = service.createQuestion(id, title, description, status, author);
    } catch(IllegalArgumentException e) {
        error = e.getMessage();
    }
    
    assertNull(question);
    assertEquals("Question needs a title. Question needs a description. Question needs a status. Question needs a user.", error);
}

@Test
public void testUpdateQuestion() {
    Integer id = QUESTION_ID;
    String newTitle = "newTitle";
    String newDescription = "new description";
    ThreadStatus newStatus = ThreadStatus.Closed;
    GeneralUser author = new GeneralUser();
    Question question = null;
    question = service.updateQuestion(id, newTitle, newDescription, newStatus, author);
    
    assertNotNull(question);
    assertEquals(newTitle, question.getTitle());
    assertEquals(newDescription, question.getDescription());
    assertEquals(newStatus, question.getThreadStatus());
    assertEquals(author, question.getUser());
}

@Test
public void testUpdateQuestionNull() {
    Integer ID = QUESTION_ID;
    Question question = null;
    
    question = service.updateQuestion(ID, null, null, null, null);
    
    assertNotNull(question);
    assertEquals(QUESTION_TITLE, question.getTitle());
    assertEquals(QUESTION_DESCRIPTION, question.getDescription());
    assertEquals(QUESTION_STATUS, question.getThreadStatus());
    assertEquals(service.getGeneralUser(USER_KEY), question.getUser());
}

@Test
public void testUpdateQuestionEmpty() {
    Integer id = QUESTION_ID;
    Question question = null; 
    String emptyDescription = "";
    question = service.updateQuestion(QUESTION_ID, QUESTION_TITLE, emptyDescription, QUESTION_STATUS, service.getGeneralUser(USER_KEY));
    assertNotNull(question);
    assertEquals(QUESTION_TITLE, question.getTitle());
    assertEquals(emptyDescription, question.getDescription());
    assertEquals(QUESTION_STATUS, question.getThreadStatus());
    assertEquals(service.getGeneralUser(USER_KEY), question.getUser());
}

@Test 
public void testGetExistingQuestion() {
    assertEquals(QUESTION_TITLE, service.getQuestion(QUESTION_ID).getTitle());
    
}

@Test
public void testGetNonExistingQuestion() {
    assertNull(service.getQuestion(NONEXISTING_QUESTION_ID));
}

@Test
public void testDeleteQuestion() {
    doAnswer((i) -> {questionMap.remove(i.getArgument(0));
    return null;}).when(questionDao).deleteById(anyInt());
    Integer id = 1234;
    String title = "New Title";
    String description = "New Description";
    ThreadStatus status = ThreadStatus.Open;
    GeneralUser author = service.getGeneralUser(USER_KEY);
    Question question;
    
    try {
        question = service.createQuestion(id, title, description, status, author);
    }catch (IllegalArgumentException e) {
        fail();
    }
    try {
        service.deleteQuestion(id);
    }catch (IllegalArgumentException e) {
        fail();
    }
    question = service.getQuestion(id);
    assertNull(question);
}

@Test
public void testDeleteQuestionNull() {
    doThrow(IllegalArgumentException.class).when(questionDao).deleteById(isNull());
    Integer id = null;
    try {
        service.deleteQuestion(id);
    }catch(IllegalArgumentException e) {
			return;
		}
		fail();
	}
//~~~~~~~~~~~~RESPONSE TESTS ~~~~~~~~~~~~~~~~~

@Test 
public void testCreateResponse() {
    assertEquals(0, service.getAllResponses().size());
    Integer id = 3554;
    String text = "Response Text";
    GeneralUser author = service.getGeneralUser(USER_KEY);
    Question question = service.getQuestion(QUESTION_ID);
    Response response = null;
    
    try {
        response = service.createResponse(id,text, question, author);
    } catch(IllegalArgumentException e) {
        fail();
    }
    assertNotNull(response);
    assertEquals(text, response.getText());
    assertEquals(question, response.getQuestion());
    assertEquals(author, response.getUser());
}

@Test
public void testCreateResponseNull() {
    Integer id = 3554;
    String text = null;
    GeneralUser author = null;
    Question question = null;
    String error = null;
    Response response = null;
    
    try {
        response = service.createResponse(id, text, question, author);
    } catch(IllegalArgumentException e) {
        error = e.getMessage();
    }
    
    assertNull(response);
    assertEquals("Response needs a text. Response needs a question. Response needs a user.", error);
    
}

@Test
public void testCreateResponseEmpty() {
    Integer id = 3554;
    String text = "";
    GeneralUser author = null;
    Question question = null;
    String error = null;
    Response response = null;
    

    try {
        response = service.createResponse(id, text, question, author);
    } catch(IllegalArgumentException e) {
        error = e.getMessage();
    }
    
    assertNull(response);
    assertEquals("Response needs a text. Response needs a question. Response needs a user.", error);
}

@Test
public void testCreateResponseSpaces() {
    Integer id = 3554;
    String text = "  ";
    GeneralUser author = null;
    Question question = null;
    String error = null;
    Response response = null;
    

    try {
        response = service.createResponse(id, text, question, author);
    } catch(IllegalArgumentException e) {
        error = e.getMessage();
    }
    
    assertNull(response);
    assertEquals("Response needs a text. Response needs a question. Response needs a user.", error);
}

@Test
public void testUpdateResponse() {
    Integer id = RESPONSE_ID;
    String newText = "newText";
    GeneralUser author = service.getGeneralUser(USER_KEY);
    Question question = service.getQuestion(QUESTION_ID);
    Response response = null;
    response = service.updateResponse(id, newText, question, author);
    
    assertNotNull(response);
    assertEquals(newText, response.getText());;
    assertEquals(question, response.getQuestion());
    assertEquals(author, response.getUser());
}

@Test
public void testUpdateResponseNull() {
    Integer ID = RESPONSE_ID;
    Response response = null;
    
    response = service.updateResponse(ID, null, null, null);
    
    assertNotNull(response);
    assertEquals(RESPONSE_TEXT, response.getText());
    assertEquals(service.getQuestion(QUESTION_ID), response.getQuestion());
    assertEquals(service.getGeneralUser(USER_KEY), response.getUser());
}

@Test
public void testUpdateResponseEmpty() {
    Integer id = RESPONSE_ID;
    Response response = null; 
    String emptyText = "";
    response = service.updateResponse(id, emptyText, service.getQuestion(QUESTION_ID), service.getGeneralUser(USER_KEY));
    assertNotNull(response);
    assertEquals(RESPONSE_TEXT, response.getText());
    assertEquals(service.getQuestion(QUESTION_ID), response.getQuestion());
    assertEquals(service.getGeneralUser(USER_KEY), response.getUser());
}

@Test 
public void testGetExistingResponse() {
    assertEquals(RESPONSE_TEXT, service.getResponse(RESPONSE_ID).getText());
    
}

@Test
public void testGetNonExistingResponse() {
    assertNull(service.getResponse(NONEXISTING_QUESTION_ID));
}

@Test
public void testDeleteResponse() {
    doAnswer((i) -> {responseMap.remove(i.getArgument(0));
    return null;}).when(responseDao).deleteById(anyInt());
    Integer id = 3554;
    String text = "New Text";
    Question question = service.getQuestion(QUESTION_ID);
    GeneralUser author = service.getGeneralUser(USER_KEY);
    Response response;
    
    try {
        response = service.createResponse(id, text, question, author);
    }catch (IllegalArgumentException e) {
        fail();
    }
    try {
        service.deleteResponse(id);
    }catch (IllegalArgumentException e) {
        fail();
    }
    response = service.getResponse(id);
    assertNull(response);
}

@Test
public void testDeleteResponseNull() {
    doThrow(IllegalArgumentException.class).when(responseDao).deleteById(isNull());
    Integer id = null;
    try {
        service.deleteResponse(id);
    }catch(IllegalArgumentException e) {
      return;
    }
    fail();
}

	@Test
	public void testCreateDonation() {
		assertEquals(0, service.getAllDonations().size());
		Integer id = -1;
		Integer amount = 7777;
		Donation donation = null;
		GeneralUser donatedFrom = new GeneralUser();
		GeneralUser donatedTo = new GeneralUser();
		donatedFrom.setUsername("donor");
		donatedTo.setUsername("recipient");
		
		
		try {
			donation = service.createOrUpdateDonation(id, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(donation);
		assertEquals(amount, donation.getAmount());
		assertEquals("donor", donation.getDonatedFrom().getUsername());
		assertEquals("recipient", donation.getDonatedTo().getUsername());
	}
	
	@Test
	public void testCreateDonationNull() {
		Integer id = null;
		Integer amount = null;
		Donation donation = null;
		GeneralUser donatedFrom = null;
		GeneralUser donatedTo = null;
		String error = null;
		
		try {
			donation = service.createOrUpdateDonation(id, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(donation);
		assertEquals("Donation needs a valid id. Donation needs a positive amount. Donation needs a donor. Donation needs a recipient.",
				error);
	}
	
	@Test
	public void testCreateDonationEmpty() {
		Integer id = -1234;
		Integer amount = 0;
		Donation donation = null;
		GeneralUser donatedFrom = new GeneralUser();
		GeneralUser donatedTo = new GeneralUser();
		donatedFrom.setUsername("");
		donatedTo.setUsername("");
		String error = null;

		
		try {
			donation = service.createOrUpdateDonation(id, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(donation);
		assertEquals("Donation needs a valid id. Donation needs a positive amount. Donation needs a donor. Donation needs a recipient.",
				error);
	}
	
	@Test
	public void testCreateDonationSpaces() {
		Integer id = -123;
		Integer amount = 0;
		Donation donation = null;
		GeneralUser donatedFrom = new GeneralUser();
		GeneralUser donatedTo = new GeneralUser();
		donatedFrom.setUsername("  ");
		donatedTo.setUsername("  ");
		String error = null;
		
		try {
			donation = service.createOrUpdateDonation(id, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(donation);
		assertEquals("Donation needs a valid id. Donation needs a positive amount. Donation needs a donor. Donation needs a recipient.",
				error);
	}
	
	@Test
	public void testUpdateDonation() {
		Integer id = DONATION_KEY;
		Integer newAmount = 4321;
		Donation donation = null;
		GeneralUser newDonatedFrom = new GeneralUser();
		GeneralUser newDonatedTo = new GeneralUser();
		newDonatedFrom.setUsername("newDonor");
		newDonatedTo.setUsername("newRecipient");
		donation = service.createOrUpdateDonation(id, newAmount, newDonatedFrom, newDonatedTo);
		
		assertNotNull(id);
		assertEquals(newAmount, donation.getAmount());
		assertEquals(newDonatedFrom, donation.getDonatedFrom());
		assertEquals(newDonatedTo, donation.getDonatedTo());
	}
	
	@Test
	public void testUpdateDonationNull() {
		Integer id = DONATION_KEY;
		Integer amount = null;
		GeneralUser donatedFrom = null;
		GeneralUser donatedTo = null;
		Donation donation = null;
		String error = null;
		
		try {
			donation = service.createOrUpdateDonation(id, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(donation);
		assertEquals("Donation needs a positive amount. Donation needs a donor. Donation needs a recipient.",
				error);
	}
	
	@Test
	public void testUpdateDonationEmpty() {
		Integer id = DONATION_KEY;
		Integer amount = 4321;
		Donation donation = null;
		GeneralUser donatedFrom = new GeneralUser();
		GeneralUser donatedTo = new GeneralUser();
		String error = null;
		donatedFrom.setUsername("");
		donatedTo.setUsername("");
		try {
			donation = service.createOrUpdateDonation(id, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(donation);
		assertEquals("Donation needs a donor. Donation needs a recipient.",
				error);
	}
	
	@Test
	public void testUpdateDonationSpaces() {
		Integer id = DONATION_KEY;
		Integer amount = 4321;
		Donation donation = null;
		GeneralUser donatedFrom = new GeneralUser();
		GeneralUser donatedTo = new GeneralUser();
		String error = null;
		donatedFrom.setUsername(" ");
		donatedTo.setUsername(" ");
		try {
			donation = service.createOrUpdateDonation(id, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(donation);
		assertEquals("Donation needs a donor. Donation needs a recipient.",
				error);
	}

	@Test
	public void testGetExistingDonation() {
		assertEquals(DONATION_KEY, service.getDonation(ADDRESS_KEY).getId());
	}
	
	@Test
	public void testGetNonExistingDonation() {
		assertNull(service.getDonation(NONEXISTING_DONATION_KEY));
	}
	
	@Test 
	public void testGetDonationsMadeByGeneralUser() {
		GeneralUser donatedFrom = new GeneralUser();
		donatedFrom.setUsername(USER_KEY);
		List<Donation> donations = service.getDonationsMadeByGeneralUser(donatedFrom);
		assertNotEquals(0, donations.size());
	}
	
	@Test 
	public void testGetDonationsForGeneralUser() {
		GeneralUser donatedTo = new GeneralUser();
		donatedTo.setUsername(USER_KEY);
		List<Donation> donations = service.getDonationsMadeByGeneralUser(donatedTo);
		assertNotEquals(0, donations.size());
	}
	
	@Test
	public void testDeleteDonation() {
		// Delete donation by id
		doAnswer((i) -> {donationMap.remove(i.getArgument(0));
				return null;}).when(donationDao).deleteById(anyInt());
		Integer amount = 7777;
		Donation donation = null;
		GeneralUser donatedFrom = new GeneralUser();
		GeneralUser donatedTo = new GeneralUser();
		donatedFrom.setUsername("donor");
		donatedTo.setUsername("recipeint");
		try {
			donation = service.createOrUpdateDonation(DONATION_KEY, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			service.deleteDonation(DONATION_KEY);
		} catch (IllegalArgumentException e) {
			fail();
		}
		donation = service.getDonation(DONATION_KEY);
		assertNull(donation);
	}
	
	@Test
	public void testDeleteDonationNull() {
		doThrow(IllegalArgumentException.class).when(donationDao).deleteById(anyInt());
		try {
			service.deleteDonation(NONEXISTING_ADDRESS_KEY);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
}

