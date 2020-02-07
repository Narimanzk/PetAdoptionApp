package ca.mcgill.ecse321.petadoptionapp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.petadoptionapp.doa.PetProfileRespository;
import ca.mcgill.ecse321.petadoptionapp.doa.RegularUserRepository;
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

	// The tear down process for every test
	@AfterEach
	public void clearDatabase() {
		// Clear the table to avoid inconsistency
		petProfileRespository.deleteAll();
		regularUserRepository.deleteAll();
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
		pet.setDescription(description);
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
		assertEquals(description, pet.getDescription());
		assertEquals(age, pet.getAge());
		assertEquals(petSpecies, pet.getPetSpecies());
		//assertEquals(profile_pic, pet.getProfilePicture());
		assertEquals(Gender.Female, pet.getPetGender());
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
}
