package ca.mcgill.ecse321.petadoptionapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.Response;

public interface ResponseRepository extends CrudRepository <Response, Integer> {
	
	Response findResponseById(Integer id);
	
	List<Response> findByQuestion(Question question);
	
	List<Response> findByUser(GeneralUser user);
}
