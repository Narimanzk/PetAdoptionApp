package ca.mcgill.ecse321.petadoptionapp.doa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;


public interface QuestionRepository extends CrudRepository<Question, String>{

	Question findQuestionByTitle(String title);
	List<Question> findByRegularUser(RegularUser regularUser);
}