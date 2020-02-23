package ca.mcgill.ecse321.petadoptionapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;

public interface PetProfileRespository extends CrudRepository<PetProfile, String>{
    PetProfile findPetProfileById(int id);
    List<PetProfile> findByUser(GeneralUser user);
}
