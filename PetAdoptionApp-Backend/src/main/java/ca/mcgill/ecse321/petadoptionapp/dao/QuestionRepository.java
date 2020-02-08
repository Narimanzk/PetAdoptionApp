package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Question;

public interface QuestionRepository extends CrudRepository <Question, Id> {
	
	Question findQuestionById(Id id);
	

}
