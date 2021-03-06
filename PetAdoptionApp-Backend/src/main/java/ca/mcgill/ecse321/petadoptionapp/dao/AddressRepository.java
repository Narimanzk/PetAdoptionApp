package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {
	Address findAddressById(Integer id);

}
