package ca.mcgill.ecse321.petadoptionapp.doa;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;

public interface PetProfileRespository extends CrudRepository<PetProfile, String>{
    PetProfile findPetProfileById(int id);
}
