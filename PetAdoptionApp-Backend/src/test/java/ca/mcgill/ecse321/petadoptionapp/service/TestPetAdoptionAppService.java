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
import java.util.Map;

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

import ca.mcgill.ecse321.petadoptionapp.dao.AdoptionApplicationRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.GeneralUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.PetProfileRespository;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.ApplicationStatus;
import ca.mcgill.ecse321.petadoptionapp.model.Gender;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.UserType;

@ExtendWith(MockitoExtension.class)
public class TestPetAdoptionAppService {
	@Mock
	private GeneralUserRepository generalUserDao;
	@Mock
	private PetProfileRespository petProfileDao;
	@Mock
	private AdoptionApplicationRespository adoptionApplicationDao;

	@InjectMocks
	private PetAdoptionAppService service;

	// Param for existing user.
	private static final String USER_KEY = "TestUser";
	private static final UserType USER_USERTYPE = UserType.Owner;
	private static final String USER_EMAIL = "cooluser@email.com";
	private static final String USER_PASSWORD = "abcdef1234!";
	private static final String USER_NAME = "Steve";
	private static final byte[] USER_PROFILEPICTURE = new byte[] { (byte) 0xf5 };
	private static final String USER_DESCRIPTION = "Test Description";
	private static final String NONEXISTING_KEY = "NotAUser";
	// Map for simulating a database.
	private static HashMap<String, GeneralUser> hmp = new HashMap<>();

	// Parameters for existing pet profile
	private static final Integer PET_ID = 5;
	private static final String PET_NAME = "TestPet";
	private static final int PET_AGE = 2;
	private static final String PET_DESCRIPTION = "desciption";
	private static final String PET_REASON = "reason";
	private static final Gender PET_GENDER = Gender.Female;
	private static final byte[] PET_PROFILEPIC = new byte[] { (byte) 0xf5 };
	private static final String PET_SPECIES = "species";

	// Map for simulating a petprofile table
	private static HashMap<Integer, PetProfile> pet_map = new HashMap<Integer, PetProfile>();

	// Parameters for existing adoption application
	private static final Integer APP_ID = 1;
	private static final String APP_DESCRIPTION = "description";
	private static final ApplicationStatus APP_STATUS = ApplicationStatus.Accepted;

	// Map for simulating an adoption application
	private static HashMap<Integer, AdoptionApplication> app_map = new HashMap<Integer, AdoptionApplication>();

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

		// *********** Setup mock data for GeneralUser ************************
		// Save General User
		lenient().when(generalUserDao.save(any(GeneralUser.class))).thenAnswer((InvocationOnMock invocation) -> {
			hmp.put(((GeneralUser) invocation.getArgument(0)).getUsername(), invocation.getArgument(0));
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
		
		//get application by pet profile
		lenient().when(adoptionApplicationDao.findByPetProfile(any(PetProfile.class))).thenAnswer((InvocationOnMock invocation) -> {
			ArrayList<AdoptionApplication> apps = new ArrayList<>();
			apps.add(app_map.get(APP_ID));
			return apps;
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
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
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
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
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
}
