package ca.mcgill.ecse321.petadoptionapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.Response;

public interface QuestionRepository extends CrudRepository <Question, Integer> {
	
	Question findQuestionById(Integer id);
	Question findQuestionByResponse(Response response);
	List<Question> findQuestionsByUser(GeneralUser user);
	

}
