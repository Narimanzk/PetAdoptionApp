package ca.mcgill.ecse321.petadoptionapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;

public interface AdoptionApplicationRespository extends CrudRepository<AdoptionApplication, String>{
    AdoptionApplication findAdoptionApplicationById(int id);
    List<AdoptionApplication> findByUser(GeneralUser user);
    List<AdoptionApplication> findByPetProfile(PetProfile profile);
}
