package ca.mcgill.ecse321.petadoptionapp.dao;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPetAdoptionAppPersistence {

	@Autowired
	private RegularUserRepository regularUserRepository;
	@Autowired
	private PetProfileRespository petProfileRespository;
	@Autowired
	private PetShelterRepository petShelterRepository;

	// The tear down process for every test
	@AfterEach
	public void clearDatabase() {
		// Clear the table to avoid inconsistency
		petProfileRespository.deleteAll();
		regularUserRepository.deleteAll();
		petShelterRepository.deleteAll();
	}

	
	//Test RegularUser table
	@Test
	public void testPersistAndLoadPerson() {
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
		RegularUser user = new RegularUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		regularUserRepository.save(user);

		user = null;
		user = regularUserRepository.findRegularUserByUsername(username);
		assertNotNull(user);
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
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
		pet.setUser(createUserForTesting());
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
	private RegularUser createUserForTesting() {
		String username = "testusername";
		String email = "test@testmail.com";
		String password = "123456789";
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
		pet.setUser(createUserForTesting());
		pet.setProfilePicture(profile_pic);
		return pet;
	}
	
	@Test
	public void testPersistAndLoadPetShelter() {
		String username = "testshelter";
		String password = "testshelterpassword";
		String name = "Test Shelter";
		String phone = "123-456-7890";
		String email = "testshelter@adopt.com";
		int balance = 100;
		
		PetShelter shelter = new PetShelter();
		shelter.setUsername(username);
		shelter.setPassword(password);
		shelter.setName(name);
		shelter.setPhone(phone);
		shelter.setEmail(email);
		shelter.setBalance(balance);
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
}
