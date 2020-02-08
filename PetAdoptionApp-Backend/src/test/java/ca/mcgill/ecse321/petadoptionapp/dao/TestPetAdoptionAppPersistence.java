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
import ca.mcgill.ecse321.petadoptionapp.dao.AdoptionApplicationRespository;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.Gender;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
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
	private AdoptionApplicationRespository adoptionApplicationRespository;

	// The tear down process for every test
	@AfterEach
	public void clearDatabase() {
		// Clear the table to avoid inconsistency
		adoptionApplicationRespository.deleteAll();
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
	
	/**
	 * @author Andrew Ta
	 * perform CREATE and READ test on PetProfile table
	 */
	@Test
	public void testPersistAndReadPetProfile() {
		//create a new pet profile
		PetProfile pet = createPetProfile();
		
		//query that pet profile by id
		PetProfile pet2 = petProfileRespository.findPetProfileById(pet.getId());
		assertNotNull(pet2);
		assertEquals(pet2.getPetName(), pet.getPetName());
		assertEquals(pet2.getDescription(), pet.getDescription());
		assertEquals(pet2.getAge(), pet.getAge());
		assertEquals(pet2.getPetSpecies(), pet.getPetSpecies());
		assertArrayEquals(pet2.getProfilePicture(), pet.getProfilePicture());
		assertEquals(pet2.getPetGender(), pet.getPetGender());
	}
	
	/**
	 * @author ANDREW TA
	 * perform CREATE and DELETE test on PetProfile table 
	 */
	@Test
	public void testDeletePetProfile() {
		PetProfile pet = createPetProfile(); //create a pet profile
		petProfileRespository.delete(pet); //delete that pet profile
		pet = petProfileRespository.findPetProfileById(pet.getId());
		assertEquals(pet, null);
	}
	
	/**
	 * @author ANDREW TA
	 * perform CREATE and UPDATE test on PetProfile table
	 */
	@Test
	public void testUpdatePetProfile() {
		PetProfile pet = createPetProfile(); //create a pet profile
		
		int id = pet.getId();
		pet = null;
		pet = petProfileRespository.findPetProfileById(id); //find that pet profile by id
		
		//new information
		int age = 3;
		String pet_name = "new_name";
		String description = "new_description";
		String petSpecies = "new_species";
		byte[] profile_pic = "\u00e0\u005f\u00d4\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes();
		Gender gender = Gender.Male;
		
		//update the entity and save to database
		pet.setAge(age);
		pet.setDescription(description);
		pet.setPetGender(gender);
		pet.setPetName(pet_name);
		pet.setPetSpecies(petSpecies);
		pet.setUser(createUserForTesting());
		pet.setProfilePicture(profile_pic);
		petProfileRespository.save(pet);
		
		//check if it is updated
		pet = null;
		pet = petProfileRespository.findPetProfileById(id);
		assertNotNull(pet);
		assertEquals(pet.getPetName(), pet_name);
		assertEquals(pet.getDescription(), description);
		assertEquals(pet.getAge(), age);
		assertEquals(pet.getPetSpecies(), petSpecies);
		assertArrayEquals(pet.getProfilePicture(), profile_pic);
		assertEquals(pet.getPetGender(), gender);
	}
	
	/**
	 * @author ANDREW TA
	 * perform CREATE AND READ test on AdoptionApplication table
	 */
	@Test
	public void testPersistAndLoadAdoptionApplication() {
		AdoptionApplication application = createAdoptionApplication(); //create a new application
		int id = application.getId();
		
		AdoptionApplication application2 = adoptionApplicationRespository.findAdoptionApplicationById(id);
		assertNotNull(application2);
		assertEquals(application.getApplicationDescription(), application2.getApplicationDescription());
		assertEquals(application.getApplicationStatus(), application2.getApplicationStatus());
	}
	
	/**
	 * @author ANDREW TA
	 * perform CREATE AND DELETE test on AdoptionApplication table
	 */
	@Test
	public void testDeleteAdoptionApplication() {
		AdoptionApplication application = createAdoptionApplication();
		int id = application.getId();
		
		adoptionApplicationRespository.delete(application);
		application = adoptionApplicationRespository.findAdoptionApplicationById(id);
		assertEquals(application, null);
	}
	
	
	/**
	 * @author ANDREW TA
	 * perform CREATE AND UPDATE test on AdoptionApplication table
	 */
	@Test
	public void testUpdateAdoptionApplication() {
		AdoptionApplication application = createAdoptionApplication();
		int id = application.getId();
		
		String new_description = "new_description";
		application.setApplicationDescription(new_description);
		adoptionApplicationRespository.save(application);
		
		application = adoptionApplicationRespository.findAdoptionApplicationById(id);
		assertEquals(application.getApplicationDescription(), new_description);
	}
	
	/**
	 * @author ANDREW TA
	 * create sample user for testing 
	 * @return RegularUser a new regularUser
	 */
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
	
	/**
	 * @author ANDREW TA
	 * create sample pet profile for testing
	 * @return PetProfile a new petProfile
	 */
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
		pet.setDescription(description);
		pet.setPetGender(Gender.Female);
		pet.setPetName(pet_name);
		pet.setPetSpecies(petSpecies);
		pet.setUser(createUserForTesting());
		pet.setProfilePicture(profile_pic);
		
		petProfileRespository.save(pet);
		return pet;
	}
	
	/**
	 * @author ANDREW TA
	 * create sample application for testing
	 * @return AdoptionApplication a new application
	 */
	private AdoptionApplication createAdoptionApplication() {
		int id = 1;
		String description = "description";
		PetProfile pet = createPetProfile();
		GeneralUser user = createUserForTesting();
		
		AdoptionApplication application = new AdoptionApplication();
		application.setId(id);
		application.setApplicationDescription(description);
		application.setPetProfile(pet);
		application.setUser(user);
		adoptionApplicationRespository.save(application);
		
		return application;
	}
}
