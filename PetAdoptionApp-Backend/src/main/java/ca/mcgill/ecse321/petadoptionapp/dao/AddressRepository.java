package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;

public interface AddressRepository extends CrudRepository<Address, Integer> {
	Address findAddressById(Integer id);
	Address findAddressByUser(GeneralUser user);

}
