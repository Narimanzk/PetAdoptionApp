package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Response;

public interface ResponseRepository extends CrudRepository <Response, Integer> {
	
	Response findResponseById(Integer id);
	

}
