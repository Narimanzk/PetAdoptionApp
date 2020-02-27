package ca.mcgill.ecse321.petadoptionapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

	@BeforeEach
	public void setMockOutput() {
		lenient().when(generalUserDao.findGeneralUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USER_KEY)) {
				GeneralUser user = new GeneralUser();
				user.setUsername(USER_KEY);
				return user;
			} else {
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
		String username = "CoolUser123";
		UserType userType = UserType.Owner;
		String email = "cooluser@email.com";
		String password = "abcdef1234!";
		String name = "Steve";
		GeneralUser user = null;
		user = service.createGeneralUser(username, userType, email, password, name);
		assertNotNull(user);
		
		user = null;
		user = service.getGeneralUser(username);
		assertNotNull(user);
		
		String newEmail = "differentuser@website.ca";
		String newPassword = "zyxvut9876$";
		byte[] profilePicture = new byte[] {(byte)0xe0};
		String description = "Some stuff about me";
		user = service.updateGeneralUser(username, newEmail, newPassword, profilePicture, description);
		
		assertNotNull(user);
		assertEquals(newEmail, user.getEmail());
		assertEquals(newPassword, user.getPassword());
		assertEquals(profilePicture, user.getProfilePicture());
		assertEquals(description, user.getDescription());
	}
	
	@Test
	public void testUpdateGeneralUserNull() {
		String username = "CoolUser123";
		UserType userType = UserType.Owner;
		String email = "cooluser@email.com";
		String password = "abcdef1234!";
		String name = "Steve";
		GeneralUser user = null;
		user = service.createGeneralUser(username, userType, email, password, name);
		assertNotNull(user);
		
		byte[] profilePicture = {(byte)0xe0};
		String description = "Some stuff about me";
		user = service.updateGeneralUser(username, null, null, profilePicture, description);
		
		assertNotNull(user);
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(profilePicture, user.getProfilePicture());
		assertEquals(description, user.getDescription());
		
		user = service.updateGeneralUser(username, null, null, null, null);
		assertEquals(profilePicture, user.getProfilePicture());
		assertEquals(description, user.getDescription());
	}
	
	@Test
	public void testUpdateGeneralUserEmpty() {
		String username = "CoolUser123";
		UserType userType = UserType.Owner;
		String email = "cooluser@email.com";
		String password = "abcdef1234!";
		String name = "Steve";
		GeneralUser user = null;
		user = service.createGeneralUser(username, userType, email, password, name);
		assertNotNull(user);
		
		byte[] profilePicture = {(byte)0xe0};
		String description = "Some stuff about me";
		user = service.updateGeneralUser(username, "", "", profilePicture, description);
		
		assertNotNull(user);
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(profilePicture, user.getProfilePicture());
		assertEquals(description, user.getDescription());
		
		user = service.updateGeneralUser(username, null, null, null, "");
		assertEquals("", user.getDescription());
	}
	
	@Test
	public void testUpdateGeneralUserSpaces() {
		String username = "CoolUser123";
		UserType userType = UserType.Owner;
		String email = "cooluser@email.com";
		String password = "abcdef1234!";
		String name = "Steve";
		GeneralUser user = null;
		user = service.createGeneralUser(username, userType, email, password, name);
		assertNotNull(user);
		
		byte[] profilePicture = {(byte)0xe0};
		String description = "Some stuff about me";
		user = service.updateGeneralUser(username, "  ", " ", profilePicture, description);
		
		assertNotNull(user);
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(profilePicture, user.getProfilePicture());
		assertEquals(description, user.getDescription());
		
		String newDescription = "   ";
		user = service.updateGeneralUser(username, null, null, null, newDescription);
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
}
