package ca.mcgill.ecse321.petadoptionapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

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

import ca.mcgill.ecse321.petadoptionapp.dao.GeneralUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.AddressRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.DonationRepository;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.UserType;
import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.Donation;
@ExtendWith(MockitoExtension.class)
public class TestPetAdoptionAppService {
	@Mock
	private GeneralUserRepository generalUserDao;
	@Mock
	private AddressRepository addressDao;
	@Mock
	private DonationRepository donationDao;

	@InjectMocks
	private PetAdoptionAppService service;

	//Param for existing user.
	private static final String USER_KEY = "TestUser";
	private static final UserType USER_USERTYPE = UserType.Owner;
	private static final String USER_EMAIL = "cooluser@email.com";
	private static final String USER_PASSWORD = "abcdef1234!";
	private static final String USER_NAME = "Steve";
	private static final byte[] USER_PROFILEPICTURE = new byte[] {(byte)0xf5};
	private static final String USER_DESCRIPTION = "Test Description";
	private static final String NONEXISTING_KEY = "NotAUser";
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
	
	//Map for simulating a database.
	private static HashMap<String, GeneralUser> hmp = new HashMap<>();
	private static HashMap<Integer, Address> addressMap = new HashMap<>();
	private static HashMap<Integer, Donation> donationMap = new HashMap<>();
	
	
	@BeforeEach
	public void setMockOutput() {
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
		// Find Address by id
			lenient().when(addressDao.findAddressById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
				return addressMap.get(invocation.getArgument(0));
					});	
		//Find Donation by id
			lenient().when(donationDao.findDonationById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
				return donationMap.get(invocation.getArgument(0));
					});	
			
		// Create a existing user
			GeneralUser user =  new GeneralUser();
			user.setUsername(USER_KEY);
			user.setUserType(USER_USERTYPE);
			user.setEmail(USER_EMAIL);
			user.setName(USER_NAME);
			user.setProfilePicture(USER_PROFILEPICTURE);
			user.setDescription(USER_DESCRIPTION);
			user.setPassword(USER_PASSWORD);
			hmp.put(USER_KEY,user);
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
		String username = USER_KEY;
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
		doAnswer((i) -> {hmp.remove(i.getArgument(0));
				return null;}).when(generalUserDao).deleteById(anyString());
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
	//wrong
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
	//wrong
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
	//wrong
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
	//wrong
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
	//failed
	@Test
	public void testDeleteAddress() {
		// Delete address by id
		doAnswer((i) -> {addressMap.remove(i.getArgument(0));
				return null;}).when(addressDao).deleteById(anyInt());
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
	//wrong
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
	//fail
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
	//fail
	@Test
	public void testDeleteDonation() {
		// Delete donation by id
		doAnswer((i) -> {donationMap.remove(i.getArgument(0));
				return null;}).when(donationDao).deleteById(anyInt());
		Integer id = 1234;
		Integer amount = 7777;
		Donation donation = null;
		GeneralUser donatedFrom = new GeneralUser();
		GeneralUser donatedTo = new GeneralUser();
		donatedFrom.setUsername("donor");
		donatedTo.setUsername("recipeint");
		try {
			donation = service.createOrUpdateDonation(id, amount, donatedFrom, donatedTo);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			service.deleteDonation(donation.getId());
		} catch (IllegalArgumentException e) {
			fail();
		}
		donation = service.getDonation(donation.getId());
		assertNull(donation);
	}
	//fail
	@Test
	public void testDeleteDonationNull() {
		doThrow(IllegalArgumentException.class).when(donationDao).deleteById(isNull());
		Integer id = null;
		try {
			service.deleteDonation(id);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
		
	
}
