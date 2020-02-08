package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;

public interface AdoptionApplicationRespository extends CrudRepository<AdoptionApplication, String>{
    AdoptionApplication findAdoptionApplicationById(int id);
}
