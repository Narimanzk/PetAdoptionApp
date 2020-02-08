package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;

public interface PetShelterRepository extends CrudRepository<PetShelter, String>{
	PetShelter findPetShelterByUsername(String username);
}
