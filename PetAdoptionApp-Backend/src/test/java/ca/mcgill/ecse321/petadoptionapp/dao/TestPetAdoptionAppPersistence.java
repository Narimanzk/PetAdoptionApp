package ca.mcgill.ecse321.petadoptionapp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.petadoptionapp.doa.RegularUserRepository;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;
import ca.mcgill.ecse321.petadoptionapp.model.ThreadStatus;
import java.lang.Thread;
import ca.mcgill.ecse321.petadoptionapp.doa.QuestionRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPetAdoptionAppPersistence {
  
  @Autowired
  private RegularUserRepository regularUserRepository;
  @Autowired
  private QuestionRepository questionRepository;
  
  //The tear down process for every test
  @AfterEach
  public void clearDatabase() {
      // Clear the table to avoid inconsistency
      regularUserRepository.deleteAll();
      questionRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadPerson() {
    String username = "testusername";
    String email = "test@testmail.com";
    String password = "123456789";
    RegularUser user = new RegularUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    regularUserRepository.save(user);
    
    user = null;
    user = regularUserRepository.findRegularUserByUsername(username);
    assertNotNull(user);
    assertEquals(email, user.getEmail());
    assertEquals(password, user.getPassword());
  }
  
  @Test
  public void testPersistandLoadQuestion() {
	  String questionTitle = "testQuestionTitle";
	  String questionDescription = "testQuestionDescription";
	  ThreadStatus questionStatus = ThreadStatus.Open;
	  Question question = new Question();
	  question.setTitle(questionTitle);
	  question.setDescription(questionDescription);
	  question.setThreadStatus(questionStatus);
	  questionRepository.save(question);
	  
	  question = null; 
	  
	  question = questionRepository.findQuestionByTitle(questionTitle);
	  assertNotNull(question);
	  assertEquals(questionTitle, question.getTitle());
	  assertEquals(questionDescription, question.getDescription());
	  assertEquals(questionStatus, question.getThreadStatus());
	  
	  
  }
  
  
  
  
}
