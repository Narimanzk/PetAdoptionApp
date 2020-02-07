package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;

public interface RegularUserRepository extends CrudRepository<RegularUser, String>{
    RegularUser findRegularUserByUsername(String username);
}
