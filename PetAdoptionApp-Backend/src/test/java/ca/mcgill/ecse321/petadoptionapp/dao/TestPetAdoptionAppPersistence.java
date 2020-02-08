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

import ca.mcgill.ecse321.petadoptionapp.dao.PetProfileRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.RegularUserRepository;
import ca.mcgill.ecse321.petadoptionapp.model.Gender;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;

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
	private GeneralUserRepository generalUserRepository;

	// The tear down process for every test
	@AfterEach
	public void clearDatabase() {
		// Clear the table to avoid inconsistency
		petProfileRespository.deleteAll();
		regularUserRepository.deleteAll();
		petShelterRepository.deleteAll();
		generalUserRepository.deleteAll();
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
	
	// create sample user for testing
	private RegularUser createAndSaveRegularUser(String username,String email, String password) {
		RegularUser user = new RegularUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		regularUserRepository.save(user);
		return user;
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
		
		// Check the pet shelter still exists, and all the superclass attributes can be accessed
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
		
		// Check the regular user still exists, and all the superclass attributes can be accessed
		genUser = null;
		genUser = generalUserRepository.findGeneralUserByUsername(userUsername);
		assertNotNull(genUser);
		assertEquals(userEmail, genUser.getEmail());
		assertEquals(userPassword, genUser.getPassword());
		
		// Check the regular user specific items can still be accessed
		user = (RegularUser) genUser;
		assertEquals(userPersonalDescription, user.getPersonalDescription());
	}
	
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
}
