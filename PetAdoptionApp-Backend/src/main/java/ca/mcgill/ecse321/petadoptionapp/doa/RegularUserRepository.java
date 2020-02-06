package ca.mcgill.ecse321.petadoptionapp.doa;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;

public interface RegularUserRepository extends CrudRepository<RegularUser, String>{
    RegularUser findRegularUserByUsername(String username);
}
