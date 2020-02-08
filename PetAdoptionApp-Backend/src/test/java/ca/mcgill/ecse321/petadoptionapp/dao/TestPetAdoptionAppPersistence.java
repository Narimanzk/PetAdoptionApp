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

	@Test
	public void testPersistAndLoadAddress() {
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
	public void testPersistAndLoadDonation() {
		String shelterName = "Shelter";
		RegularUser user = createUserForTesting();
		PetShelter petShelter = createPetShelterForTesting();
		petShelter.setUsername(shelterName);

		Integer amount = 5;
		Integer id = 12345;
		Donation donation = new Donation();
		donation.setId(id);
		donation.setAmount(amount);
		donation.setDonatedFrom(user);
		donation.setDonatedTo(petShelter);
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

	// create sample pet shelter for testing
	private PetShelter createPetShelterForTesting() {
		String username = "testshelter";
		String email = "test@testmail.com";
		String password = "123456789";
		PetShelter user = new PetShelter();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		petShelterRepository.save(user);
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
}
