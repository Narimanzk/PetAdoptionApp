package ca.mcgill.ecse321.petadoptionapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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

import ca.mcgill.ecse321.petadoptionapp.dao.GeneralUserRepository;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.UserType;

@ExtendWith(MockitoExtension.class)
public class TestPetAdoptionAppService {
	@Mock
	private GeneralUserRepository generalUserDao;

	@InjectMocks
	private PetAdoptionAppService service;

	private static final String USER_KEY = "TestUser";
	private static final String NONEXISTING_KEY = "NotAUser";
	private static final String UPDATE_USER_USERNAME = "TestUpdateUser";
	private static final UserType UPDATE_USER_USERTYPE = UserType.Owner;
	private static final String UPDATE_USER_EMAIL = "cooluser@email.com";
	private static final String UPDATE_USER_PASSWORD = "abcdef1234!";
	private static final String UPDATE_USER_NAME = "Steve";
	private static final byte[] UPDATE_USER_PROFILEPICTURE = new byte[] {(byte)0xf5};
	private static final String UPDATE_USER_DESCRIPTION = "Test Description";

	@BeforeEach
	public void setMockOutput() {
		lenient().when(generalUserDao.findGeneralUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USER_KEY)) {
				GeneralUser user = new GeneralUser();
				user.setUsername(USER_KEY);
				return user;
			}
			else if (invocation.getArgument(0).equals(UPDATE_USER_USERNAME)) {
				GeneralUser user = new GeneralUser();
				user.setUsername(UPDATE_USER_USERNAME);
				user.setUserType(UPDATE_USER_USERTYPE);
				user.setEmail(UPDATE_USER_EMAIL);
				user.setPassword(UPDATE_USER_PASSWORD);
				user.setName(UPDATE_USER_NAME);
				user.setProfilePicture(UPDATE_USER_PROFILEPICTURE);
				user.setDescription(UPDATE_USER_DESCRIPTION);
				return user;
			}
			else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(generalUserDao.save(any(GeneralUser.class))).thenAnswer(returnParameterAsAnswer);
	}

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
		assertEquals("User needs a username. User needs a user type. User needs an email. User needs a password. User needs a name.",
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
		assertEquals("User needs a username. User needs an email. User needs a password. User needs a name.",
				error);
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
		assertEquals("User needs a username. User needs an email. User needs a password. User needs a name.",
				error);
	}
	
	@Test
	public void testUpdateGeneralUser() {
		String username = UPDATE_USER_USERNAME;
		String newEmail = "differentuser@website.ca";
		String newPassword = "zyxvut9876$";
		byte[] newProfilePicture = new byte[] {(byte)0xe0};
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
		String username = UPDATE_USER_USERNAME;
		GeneralUser user = null;
		
		user = service.updateGeneralUser(username, null, null, null, null);
		
		assertNotNull(user);
		assertEquals(UPDATE_USER_EMAIL, user.getEmail());
		assertEquals(UPDATE_USER_PASSWORD, user.getPassword());
		assertEquals(UPDATE_USER_PROFILEPICTURE, user.getProfilePicture());
		assertEquals(UPDATE_USER_DESCRIPTION, user.getDescription());
	}
	
	@Test
	public void testUpdateGeneralUserEmpty() {
		String username = UPDATE_USER_USERNAME;
		GeneralUser user = null;
		String emptyDescription = "";

		user = service.updateGeneralUser(username, "", "", null, emptyDescription);
		
		assertNotNull(user);
		assertEquals(UPDATE_USER_EMAIL, user.getEmail());
		assertEquals(UPDATE_USER_PASSWORD, user.getPassword());
		assertEquals(UPDATE_USER_PROFILEPICTURE, user.getProfilePicture());
		assertEquals(emptyDescription, user.getDescription());
	}
	
	@Test
	public void testUpdateGeneralUserSpaces() {
		String username = UPDATE_USER_USERNAME;
		GeneralUser user = null;
		String newDescription = "   ";
		
		user = service.updateGeneralUser(username, "  ", " ", null, newDescription);
		
		assertNotNull(user);
		assertEquals(UPDATE_USER_EMAIL, user.getEmail());
		assertEquals(UPDATE_USER_PASSWORD, user.getPassword());
		assertEquals(newDescription, user.getDescription());
	}
	
	@Test
	public void testDeleteGeneralUser() {
		doNothing().when(generalUserDao).deleteById(anyString());
		String username = USER_KEY;
		try {
			service.deleteGeneralUser(username);
		} catch (IllegalArgumentException e) {
			fail();
		}
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
	
	@Test
	public void testGetExistingPerson() {
		assertEquals(USER_KEY, service.getGeneralUser(USER_KEY).getUsername());
	}
	
	@Test
	public void testGetNonExistingPerson() {
		assertNull(service.getGeneralUser(NONEXISTING_KEY));
	}
}
