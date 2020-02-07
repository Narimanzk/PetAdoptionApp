package ca.mcgill.ecse321.petadoptionapp.doa;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;

public interface PetShelterRepository extends CrudRepository<PetShelter, String> {
	PetShelter findPetShelterByUsername(String username);

}
