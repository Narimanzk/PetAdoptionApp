package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;

public interface GeneralUserRepository extends CrudRepository <GeneralUser, String> {
	
	GeneralUser findGeneralUserByUsername(String username);

	GeneralUser findGeneralUserByAddress(Address address);
}
