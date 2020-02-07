package ca.mcgill.ecse321.petadoptionapp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.petadoptionapp.doa.AddressRepository;
import ca.mcgill.ecse321.petadoptionapp.doa.DonationRepository;
import ca.mcgill.ecse321.petadoptionapp.doa.PetShelterRepository;
import ca.mcgill.ecse321.petadoptionapp.doa.RegularUserRepository;
import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.Donation;
import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPetAdoptionAppPersistence {
  
  @Autowired
  private RegularUserRepository regularUserRepository;
  @Autowired
  private PetShelterRepository petShelterRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private DonationRepository donationRepository;
  
  //The tear down process for every test
  @AfterEach
  public void after() {
      // Clear the table to avoid inconsistency
      donationRepository.deleteAll();
      regularUserRepository.deleteAll();
      petShelterRepository.deleteAll();
      addressRepository.deleteAll();
      
  }
  
  @BeforeEach
  public void before() {
      // Clear the table to avoid inconsistency
      donationRepository.deleteAll();
      regularUserRepository.deleteAll();
      petShelterRepository.deleteAll();
      addressRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadRegularUser() {
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
	  String username = "testusername";
	  String shelterName = "Shelter";
	  RegularUser user = new RegularUser();
	  user.setUsername(username);
	  
	  PetShelter petShelter = new PetShelter();
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
}
