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
import ca.mcgill.ecse321.petadoptionapp.dao.PetProfileRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.RegularUserRepository;
import ca.mcgill.ecse321.petadoptionapp.model.Gender;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPetAdoptionAppPersistence {

	@Autowired
	private RegularUserRepository regularUserRepository;
	@Autowired
	private PetProfileRespository petProfileRespository;
	@Autowired
	private PetShelterRepository petShelterRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private DonationRepository donationRepository;

	// The tear down process for every test
	@AfterEach
	public void clearDatabase() {
		// Clear the table to avoid inconsistency
		donationRepository.deleteAll();
		addressRepository.deleteAll();
		petProfileRespository.deleteAll();
		regularUserRepository.deleteAll();
		petShelterRepository.deleteAll();
	}


	//Test RegularUser table in create and load. (C/R)
	@Test
	public void testPersistAndLoadPerson() {
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		//make sure user doesn't exist
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

	//Test the regular table in create and update (C/U)
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

	//Test the regular table in create and delete (C/D)
	@Test
	public void testDeleteRegularUser() {
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		RegularUser user = createAndSaveRegularUser(username, email, password);
		user = null;
		user = regularUserRepository.findRegularUserByUsername(username);
		//make sure user exist in database
		assertNotNull(user);
		//delete the user
		regularUserRepository.delete(user);
		user = regularUserRepository.findRegularUserByUsername(username);
		assertNull(user);
	}
	//Test PetProfile table
	//Test persist and load functionality
	@Test
	public void testPersistAndReadPetProfile() {
		int age = 3;
		int pet_id = 1;
		String pet_name = "test_name";
		String description = "description";
		String petSpecies = "species";
		byte[] profile_pic = "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes();

		PetProfile pet = new PetProfile();
		pet.setId(pet_id);
		pet.setAge(age);
		//pet.setDescription(description);
		pet.setPetGender(Gender.Female);
		pet.setPetName(pet_name);
		pet.setPetSpecies(petSpecies);
		pet.setUser(createAndSaveRegularUser("testusername","test@gmail.com","admin"));
		pet.setProfilePicture(profile_pic);

		petProfileRespository.save(pet);
		pet = null;
		pet = petProfileRespository.findPetProfileById(pet_id);
		assertNotNull(pet);
		assertEquals(pet_name, pet.getPetName());
		//assertEquals(description, pet.getDescription());
		assertEquals(age, pet.getAge());
		assertEquals(petSpecies, pet.getPetSpecies());
		assertArrayEquals(profile_pic, pet.getProfilePicture());
		assertEquals(Gender.Female, pet.getPetGender());
	}

	//Test delete functionality
	@Test
	public void testDeletePetProfile() {
		PetProfile pet = createPetProfile();
		petProfileRespository.delete(pet);
		pet = petProfileRespository.findPetProfileById(pet.getId());
		assertEquals(pet, null);
	}

	//Test update functionality
	@Test
	public void testUpdatePetProfile() {
		PetProfile pet = createPetProfile();
		String new_name = "new_name";
		int new_age = 15;
		String description = "new_description";
		String petSpecies = "new_species";
		Gender gender = Gender.Male;
		byte[] pic = "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0031\u0030\u009d".getBytes();

	}

	@Test
	public void testPersistAndLoadAddress() {
		Integer id = 12345;
		String street = "12 Sherbrooke W";
		String city = "Montreal";
		String state = "QC";
		String postalCode = "AAA-111";
		String country = "CA";
		Address address = createAddressForTesting();
		addressRepository.save(address);

		address = null;
		address = addressRepository.findAddressById(id);
		assertNotNull(address);
		assertEquals(id, address.getId());
		assertEquals(street,address.getStreet());
		assertEquals(city,address.getCity());
		assertEquals(state,address.getState());
		assertEquals(postalCode,address.getPostalCode());
		assertEquals(country,address.getCountry());
	}

	@Test
	public void testDeleteAddress() {
		Address address = createAddressForTesting();
		addressRepository.delete(address);
		address = addressRepository.findAddressById(address.getId());
		assertEquals(address, null);
	}

	@Test
	public void testPersistAndLoadDonation() {
		Integer amount = 5;
		Integer id = 12345;
		RegularUser user = createAndSaveRegularUser("testDon", "TestDon@test.com", "1234");
		PetShelter petShelter = createPetShelter();
		Donation donation = createDonationForTesting();

		regularUserRepository.save(user);
		petShelterRepository.save(petShelter);
		donationRepository.save(donation);

		donation = null;
		donation = donationRepository.findDonationById(id);
		assertNotNull(donation);
		assertEquals(id, donation.getId());
		assertEquals(amount, donation.getAmount());
		assertEquals(user.getUsername(), donation.getDonatedFrom().getUsername());
		assertEquals(petShelter.getUsername(), donation.getDonatedTo().getUsername());

	}

	@Test
	public void testDeleteDonation() {
		Donation donation = createDonationForTesting();
		donationRepository.delete(donation);
		donation = donationRepository.findDonationById(donation.getId());
		assertEquals(donation, null);
	}

	// create sample user for testing
	private RegularUser createAndSaveRegularUser(String username,String email, String password) {
		RegularUser user = new RegularUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		regularUserRepository.save(user);
		return user;
	}

//	// create sample pet shelter for testing
//	private PetShelter createPetShelterForTesting() {
//		String username = "testshelter";
//		String email = "test@testmail.com";
//		String password = "123456789";
//		PetShelter user = new PetShelter();
//		user.setUsername(username);
//		user.setEmail(email);
//		user.setPassword(password);
//		petShelterRepository.save(user);
//		return user;
//	}

	private Address createAddressForTesting() {
		Integer id = 12345;
		String street = "12 Sherbrooke W";
		String city = "Montreal";
		String state = "QC";
		String postalCode = "AAA-111";
		String country = "CA";
		Address address = new Address();
		address.setId(id);
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setPostalCode(postalCode);
		address.setCountry(country);
		return address;
	}

	private Donation createDonationForTesting() {
		Integer amount = 5;
		Integer id = 12345;
		RegularUser user = createAndSaveRegularUser("testDon", "TestDon@test.com", "1234");
		PetShelter petShelter = createPetShelter();
		Donation donation = new Donation();
		donation.setId(id);
		donation.setAmount(amount);
		donation.setDonatedFrom(user);
		donation.setDonatedTo(petShelter);
		return donation;

	}

	//create sample pet profile
	private PetProfile createPetProfile() {

		int age = 3;
		int pet_id = 1;
		String pet_name = "test_name";
		String description = "description";
		String petSpecies = "species";
		byte[] profile_pic = "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes();

		PetProfile pet = new PetProfile();
		pet.setId(pet_id);
		pet.setAge(age);
		//pet.setDescription(description);
		pet.setPetGender(Gender.Female);
		pet.setPetName(pet_name);
		pet.setPetSpecies(petSpecies);
		pet.setUser(createAndSaveRegularUser("testusername","test@gmail.com","admin"));
		pet.setProfilePicture(profile_pic);
		return pet;
	}

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
}
