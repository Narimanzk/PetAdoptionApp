package ca.mcgill.ecse321.petadoptionapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import ca.mcgill.ecse321.petadoptionapp.dao.GeneralUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.QuestionRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.ResponseRepository;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.Question;
import ca.mcgill.ecse321.petadoptionapp.model.Response;
import ca.mcgill.ecse321.petadoptionapp.model.ThreadStatus;
import ca.mcgill.ecse321.petadoptionapp.model.UserType;

@ExtendWith(MockitoExtension.class)
public class TestPetAdoptionAppService {
	@Mock
	private GeneralUserRepository generalUserDao;
	@Mock 
	private QuestionRepository questionDao;
	@Mock
	private ResponseRepository responseDao;

	@InjectMocks
	private PetAdoptionAppService service;

	//Param for existing user.
	private static final String USER_KEY = "TestUser";
	private static final UserType USER_USERTYPE = UserType.Owner;
	private static final String USER_EMAIL = "cooluser@email.com";
	private static final String USER_PASSWORD = "abcdef1234!";
	private static final String USER_NAME = "Steve";
	private static final byte[] USER_PROFILEPICTURE = new byte[] {(byte)0xf5};
	private static final String USER_DESCRIPTION = "Test Description";
	private static final String NONEXISTING_KEY = "NotAUser";
	//Map for simulating a database.
	private static HashMap<String, GeneralUser> hmp = new HashMap<>();
	//Param for existing question
	private static final Integer QUESTION_ID = 2552;
	private static final String QUESTION_TITLE = "QuestionTitle";
	private static final String QUESTION_DESCRIPTION = "QuestionDescription";
	private static final ThreadStatus QUESTION_STATUS = ThreadStatus.Open;
	private static final Integer NONEXISTING_QUESTION_ID = 2755;
	private static final List<Response> QUESTION_RESPONSES = new ArrayList<Response>();
	//Map for simulating a question database
	private static HashMap<Integer, Question> questionMap = new HashMap<>();
	//Param for existing response
	private static final Integer RESPONSE_ID = 3552;
	private static final String RESPONSE_TEXT = "Response Text";
	//Map for simulating response databse
	private static HashMap<Integer, Response> responseMap = new HashMap<>();
	
	
	
	@BeforeEach
	public void setMockOutput() {
		//Save General User
			lenient().when(generalUserDao.save(any(GeneralUser.class))).thenAnswer((InvocationOnMock invocation) -> {
				hmp.put(((GeneralUser)invocation.getArgument(0)).getUsername(),invocation.getArgument(0));
				return invocation.getArgument(0);
			});
		// Find General User by username
			lenient().when(generalUserDao.findGeneralUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
				return hmp.get(invocation.getArgument(0));
		});
		// Create a existing user
			GeneralUser user =  new GeneralUser();
			user.setUsername(USER_KEY);
			user.setUserType(USER_USERTYPE);
			user.setEmail(USER_EMAIL);
			user.setName(USER_NAME);
			user.setProfilePicture(USER_PROFILEPICTURE);
			user.setDescription(USER_DESCRIPTION);
			user.setPassword(USER_PASSWORD);
			hmp.put(USER_KEY,user);
			
		//Save Question 
			lenient().when(questionDao.save(any(Question.class))).thenAnswer((InvocationOnMock invocation) -> {
				questionMap.put(((Question)invocation.getArgument(0)).getId(), invocation.getArgument(0));
				return invocation.getArgument(0);
			});
		//Find Question by ID
			lenient().when(questionDao.findQuestionById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
				return questionMap.get(invocation.getArgument(0));
			});
		//Create an existing question
			Question question = new Question();
			question.setId(QUESTION_ID);
			question.setDescription(QUESTION_DESCRIPTION);
			question.setThreadStatus(QUESTION_STATUS);
			question.setUser(service.getGeneralUser(USER_KEY));
			questionMap.put(QUESTION_ID, question);
			
		//Save Response
			lenient().when(responseDao.save(any(Response.class))).thenAnswer((InvocationOnMock invocation) -> {
				responseMap.put(((Response)invocation.getArgument(0)).getId(), invocation.getArgument(0));
				return invocation.getArgument(0);
			});
		//Find Response by ID
			lenient().when(responseDao.findResponseById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
				return responseMap.get(invocation.getArgument(0));
			});
		// Create an existing response 
			Response response = new Response();
			response.setId(RESPONSE_ID);
			response.setText(RESPONSE_TEXT);
			response.setQuestion(service.getQuestion(QUESTION_ID));
			response.setUser(service.getGeneralUser(USER_KEY));
			responseMap.put(RESPONSE_ID, response);
	}

	@Test
	public void testCreateGeneralUser() {
		assertEquals(0, service.getAllGeneralUsers().size());
		
		String username = "CoolUser123";
		UserType userType = UserType.Owner;
		String email = "cooluser@email.com";
		String password = "abcdef1234!";
		String name = "Steve";
		GeneralUser user = null;
		
		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(userType, user.getUserType());
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(name, user.getName());
	}
	
	@Test
	public void testCreateGeneralUserNull() {
		String username = null;
		UserType userType = null;
		String email = null;
		String password = null;
		String name = null;
		String error = null;
		GeneralUser user = null;
		
		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(user);
		assertEquals("User needs a username. User needs a user type. User needs an email. User needs a password. User needs a name.",
				error);
	}
	
	@Test
	public void testCreateGeneralUserEmpty() {
		String username = "";
		UserType userType = UserType.Admin;
		String email = "";
		String password = "";
		String name = "";
		String error = null;
		GeneralUser user = null;
		
		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(user);
		assertEquals("User needs a username. User needs an email. User needs a password. User needs a name.",
				error);
	}
	
	@Test
	public void testCreateGeneralUserSpaces() {
		String username = "  ";
		UserType userType = UserType.Admin;
		String email = "   ";
		String password = " ";
		String name = "    ";
		String error = "";
		GeneralUser user = null;
		
		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(user);
		assertEquals("User needs a username. User needs an email. User needs a password. User needs a name.",
				error);
	}
	
	@Test
	public void testUpdateGeneralUser() {
		String username = USER_KEY;
		String newEmail = "differentuser@website.ca";
		String newPassword = "zyxvut9876$";
		byte[] newProfilePicture = new byte[] {(byte)0xe0};
		String newDescription = "Some stuff about me";
		
		GeneralUser user = null;
		user = service.updateGeneralUser(username, newEmail, newPassword, newProfilePicture, newDescription);
		
		assertNotNull(user);
		assertEquals(newPassword, user.getPassword());
		assertEquals(newEmail, user.getEmail());
		
		assertEquals(newProfilePicture, user.getProfilePicture());
		assertEquals(newDescription, user.getDescription());
	}
	
	@Test
	public void testUpdateGeneralUserNull() {
		String username = USER_KEY;
		GeneralUser user = null;
		
		user = service.updateGeneralUser(username, null, null, null, null);
		
		assertNotNull(user);
		assertEquals(USER_EMAIL, user.getEmail());
		assertEquals(USER_PASSWORD, user.getPassword());
		assertEquals(USER_PROFILEPICTURE, user.getProfilePicture());
		assertEquals(USER_DESCRIPTION, user.getDescription());
	}
	
	@Test
	public void testUpdateGeneralUserEmpty() {
		String username = USER_KEY;
		GeneralUser user = null;
		String emptyDescription = "";

		user = service.updateGeneralUser(username, USER_EMAIL, USER_PASSWORD, USER_PROFILEPICTURE, emptyDescription);
		
		assertNotNull(user);
		assertEquals(USER_EMAIL, user.getEmail());
		assertEquals(USER_PASSWORD, user.getPassword());
		assertEquals(USER_PROFILEPICTURE, user.getProfilePicture());
		assertEquals(emptyDescription, user.getDescription());
	}
	
	@Test
	public void testUpdateGeneralUserSpaces() {
		String username = USER_KEY;
		GeneralUser user = null;
		String newDescription = "   ";
		
		user = service.updateGeneralUser(username, USER_EMAIL, USER_PASSWORD, USER_PROFILEPICTURE, newDescription);
		
		assertNotNull(user);
		assertEquals(USER_EMAIL, user.getEmail());
		assertEquals(USER_PASSWORD, user.getPassword());
		assertEquals(newDescription, user.getDescription());
	}
	
	@Test
	public void testGetExistingPerson() {
		assertEquals(USER_KEY, service.getGeneralUser(USER_KEY).getUsername());
	}
	
	@Test
	public void testGetNonExistingPerson() {
		assertNull(service.getGeneralUser(NONEXISTING_KEY));
	}
	
	@Test
	public void testDeleteGeneralUser() {
		// Delete general user by id
		doAnswer((i) -> {hmp.remove(i.getArgument(0));
				return null;}).when(generalUserDao).deleteById(anyString());
		String username = "CoolUser123";
		UserType userType = UserType.Owner;
		String email = "cooluser@email.com";
		String password = "abcdef1234!";
		String name = "Steve";
		GeneralUser user;	
		try {
			user = service.createGeneralUser(username, userType, email, password, name);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			service.deleteGeneralUser(username);
		} catch (IllegalArgumentException e) {
			fail();
		}
		user = service.getGeneralUser(username);
		assertNull(user);
	}

	@Test
	public void testDeleteGeneralUserNull() {
		doThrow(IllegalArgumentException.class).when(generalUserDao).deleteById(isNull());
		String username = null;
		try {
			service.deleteGeneralUser(username);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
	
//~~~~~~~~~~~~ QUESTION TESTS ~~~~~~~~~~~~~~~~~~~
	
	@Test 
	public void testCreateQuestion() {
		assertEquals(0, service.getAllQuestions().size());
		Integer id = 2553;
		String title = "QuestionTitle";
		String description = "QuestionDescription";
		ThreadStatus status = ThreadStatus.Open;
		GeneralUser author = service.getGeneralUser(USER_KEY);
		Question question = null;
		
		try {
			question = service.createQuestion(id,title, description, status, author);
		} catch(IllegalArgumentException e) {
			fail();
		}
		assertNotNull(question);
		assertEquals(title, question.getTitle());
		assertEquals(description, question.getDescription());
		assertEquals(status, question.getThreadStatus());
		assertEquals(author, question.getUser());
	}
	
	@Test
	public void testCreateQuestionNull() {
		Integer id = 2535;
		String title = null;
		String description = null;
		ThreadStatus status = null;
		GeneralUser author = null;
		String error = null;
		Question question = null;
		
		try {
			question = service.createQuestion(id, title, description, status, author);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(question);
		assertEquals("Question needs a title. Question needs a description. Question needs a status. Question needs a user.", error);
		
	}
	
	@Test
	public void testCreateQuestionEmpty() {
		Integer id = 2553;
		String title = "";
		String description = "";
		ThreadStatus status = null;
		GeneralUser author = null;
		String error = null;
		Question question = null;
		

		try {
			question = service.createQuestion(id, title, description, status, author);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(question);
		assertEquals("Question needs a title. Question needs a description. Question needs a Thread Status. Question needs a user. ", error);
	}
	
	@Test
	public void testCreateQuestionSpaces() {
		Integer id = 2553;
		String title = "  ";
		String description = "  ";
		ThreadStatus status = null;
		GeneralUser author = null;
		String error = null;
		Question question = null;
		

		try {
			question = service.createQuestion(id, title, description, status, author);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(question);
		assertEquals("Question needs a title. Question needs a description. Question needs a Thread Status. Question needs a user. ", error);
	}
	
	@Test
	public void testUpdateQuestion() {
		Integer id = QUESTION_ID;
		String newTitle = "newTitle";
		String newDescription = "new description";
		ThreadStatus newStatus = ThreadStatus.Closed;
		GeneralUser author = service.getGeneralUser(USER_KEY);
		Question question = null;
		question = service.updateQuestion(id, newTitle, newDescription, newStatus, author);
		
		assertNotNull(question);
		assertEquals(newTitle, question.getTitle());
		assertEquals(newDescription, question.getDescription());
		assertEquals(newStatus, question.getThreadStatus());
		assertEquals(author, question.getUser());
	}
	
	@Test
	public void testUpdateQuestionNull() {
		Integer ID = QUESTION_ID;
		Question question = null;
		
		question = service.updateQuestion(ID, null, null, null, null);
		
		assertNotNull(question);
		assertEquals(QUESTION_TITLE, question.getTitle());
		assertEquals(QUESTION_DESCRIPTION, question.getDescription());
		assertEquals(QUESTION_STATUS, question.getThreadStatus());
		assertEquals(service.getGeneralUser(USER_KEY), question.getUser());
	}
	
	@Test
	public void testUpdateQuestionEmpty() {
		Integer id = QUESTION_ID;
		Question question = null; 
		String emptyDescription = "";
		question = service.updateQuestion(QUESTION_ID, QUESTION_TITLE, emptyDescription, QUESTION_STATUS, service.getGeneralUser(USER_KEY));
		assertNotNull(question);
		assertEquals(QUESTION_TITLE, question.getTitle());
		assertEquals(emptyDescription, question.getDescription());
		assertEquals(QUESTION_STATUS, question.getThreadStatus());
		assertEquals(service.getGeneralUser(USER_KEY), question.getUser());
	}
	
	@Test 
	public void testGetExistingQuestion() {
		assertEquals(QUESTION_TITLE, service.getQuestion(QUESTION_ID).getTitle());
		
	}
	
	@Test
	public void testGetNonExistingQuestion() {
		assertNull(service.getQuestion(NONEXISTING_QUESTION_ID));
	}
	
	@Test
	public void testDeleteQuestion() {
		doAnswer((i) -> {questionMap.remove(i.getArgument(0));
		return null;}).when(questionDao).deleteById(anyInt());
		Integer id = 1234;
		String title = "New Title";
		String description = "New Description";
		ThreadStatus status = ThreadStatus.Open;
		GeneralUser author = service.getGeneralUser(USER_KEY);
		Question question;
		
		try {
			question = service.createQuestion(id, title, description, status, author);
		}catch (IllegalArgumentException e) {
			fail();
		}
		try {
			service.deleteQuestion(id);
		}catch (IllegalArgumentException e) {
			fail();
		}
		question = service.getQuestion(id);
		assertNull(question);
	}
	
	@Test
	public void testDeleteQuestionNull() {
		doThrow(IllegalArgumentException.class).when(questionDao).deleteById(isNull());
		Integer id = null;
		try {
			service.deleteQuestion(id);
		}catch(IllegalArgumentException e) {
			return;
		}
		fail();
	}
	
	
	// ~~~~~~~~~~~~RESPONSE TESTS ~~~~~~~~~~~~~~~~~
	
	@Test 
	public void testCreateResponse() {
		assertEquals(0, service.getAllResponses().size());
		Integer id = 3554;
		String text = "Response Text";
		GeneralUser author = service.getGeneralUser(USER_KEY);
		Question question = service.getQuestion(QUESTION_ID);
		Response response = null;
		
		try {
			response = service.createResponse(id,text, question, author);
		} catch(IllegalArgumentException e) {
			fail();
		}
		assertNotNull(response);
		assertEquals(text, response.getText());
		assertEquals(question, response.getQuestion());
		assertEquals(author, response.getUser());
	}
	
	@Test
	public void testCreateResponseNull() {
		Integer id = 3554;
		String text = null;
		GeneralUser author = null;
		Question question = null;
		String error = null;
		Response response = null;
		
		try {
			response = service.createResponse(id, text, question, author);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(response);
		assertEquals("Response needs a text. Response needs a question. Response needs a user.", error);
		
	}
	
	@Test
	public void testCreateResponseEmpty() {
		Integer id = 3554;
		String text = "";
		GeneralUser author = null;
		Question question = null;
		String error = null;
		Response response = null;
		

		try {
			response = service.createResponse(id, text, question, author);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(response);
		assertEquals("Response needs a text. Response needs a question. Response needs a user.", error);
	}
	
	@Test
	public void testCreateResponseSpaces() {
		Integer id = 3554;
		String text = "  ";
		GeneralUser author = null;
		Question question = null;
		String error = null;
		Response response = null;
		

		try {
			response = service.createResponse(id, text, question, author);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(response);
		assertEquals("Response needs a text. Response needs a question. Response needs a user.", error);
	}
	
	@Test
	public void testUpdateResponse() {
		Integer id = RESPONSE_ID;
		String newText = "newText";
		GeneralUser author = service.getGeneralUser(USER_KEY);
		Question question = service.getQuestion(QUESTION_ID);
		Response response = null;
		response = service.updateResponse(id, newText, question, author);
		
		assertNotNull(response);
		assertEquals(newText, response.getText());;
		assertEquals(question, response.getQuestion());
		assertEquals(author, response.getUser());
	}
	
	@Test
	public void testUpdateResponseNull() {
		Integer ID = RESPONSE_ID;
		Response response = null;
		
		response = service.updateResponse(ID, null, null, null);
		
		assertNotNull(response);
		assertEquals(RESPONSE_TEXT, response.getText());
		assertEquals(service.getQuestion(QUESTION_ID), response.getQuestion());
		assertEquals(service.getGeneralUser(USER_KEY), response.getUser());
	}
	
	@Test
	public void testUpdateResponseEmpty() {
		Integer id = RESPONSE_ID;
		Response response = null; 
		String emptyText = "";
		response = service.updateResponse(id, emptyText, service.getQuestion(QUESTION_ID), service.getGeneralUser(USER_KEY));
		assertNotNull(response);
		assertEquals(emptyText, response.getText());
		assertEquals(service.getQuestion(QUESTION_ID), response.getQuestion());
		assertEquals(service.getGeneralUser(USER_KEY), response.getUser());
	}
	
	@Test 
	public void testGetExistingResponse() {
		assertEquals(RESPONSE_TEXT, service.getResponse(RESPONSE_ID).getText());
		
	}
	
	@Test
	public void testGetNonExistingResponse() {
		assertNull(service.getResponse(NONEXISTING_QUESTION_ID));
	}
	
	@Test
	public void testDeleteResponse() {
		doAnswer((i) -> {responseMap.remove(i.getArgument(0));
		return null;}).when(responseDao).deleteById(anyInt());
		Integer id = 3554;
		String text = "New Text";
		Question question = service.getQuestion(QUESTION_ID);
		GeneralUser author = service.getGeneralUser(USER_KEY);
		Response response;
		
		try {
			response = service.createResponse(id, text, question, author);
		}catch (IllegalArgumentException e) {
			fail();
		}
		try {
			service.deleteResponse(id);
		}catch (IllegalArgumentException e) {
			fail();
		}
		response = service.getResponse(id);
		assertNull(response);
	}
	
	@Test
	public void testDeleteResponseNull() {
		doThrow(IllegalArgumentException.class).when(responseDao).deleteById(isNull());
		Integer id = null;
		try {
			service.deleteResponse(id);
		}catch(IllegalArgumentException e) {
			return;
		}
		fail();
	}
}

