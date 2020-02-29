package ca.mcgill.ecse321.petadoptionapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
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
	private static final Integer PET_ID = 1;
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
		lenient().when(petProfileDao.findByUser(user)).thenAnswer((InvocationOnMock invocation) -> {
			ArrayList<PetProfile> pets = new ArrayList<>();
			return pets.add(pet_map.get(invocation.getArgument(0)));
		});
		
		// find pet profile by application
		lenient().when(petProfileDao.findByAdoptionApplications(app)).thenAnswer((InvocationOnMock invocation) -> {
			return pet_map.get(invocation.getArgument(0));
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
		lenient().when(adoptionApplicationDao.findByUser(user)).thenAnswer((InvocationOnMock invocation) -> {
			ArrayList<AdoptionApplication> apps = new ArrayList<>();
			return apps.add(app_map.get(invocation.getArgument(0)));
		});
		
		//get application by pet profile
		lenient().when(adoptionApplicationDao.findByPetProfile(pet)).thenAnswer((InvocationOnMock invocation) -> {
			ArrayList<AdoptionApplication> apps = new ArrayList<>();
			return apps.add(app_map.get(invocation.getArgument(0)));
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

	// ************************ Test PetProfile service
	// *******************************
}
