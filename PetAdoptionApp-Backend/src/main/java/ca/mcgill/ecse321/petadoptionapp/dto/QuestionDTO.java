package ca.mcgill.ecse321.petadoptionapp.dto;

import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.Response;
import ca.mcgill.ecse321.petadoptionapp.model.ThreadStatus;

public class QuestionDTO {

	private Integer id;
	private String title; 
	private String description;
	private ThreadStatus status;
	private List<ResponseDTO> responses;
	private GeneralUserDTO author; 
	
	public QuestionDTO() {
		
	}
	
	public QuestionDTO(Integer id, String title, String description, ThreadStatus status) {
		this.id = id;
		this.title = title; 
		this.description = description; 
		this.status = status;
	}
	
	public Integer getID() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description; 
	}
	
	public ThreadStatus getStatus() {
		return status;
	}
	
	public List<ResponseDTO> getResponses(){
		return responses;
	}
	
	public GeneralUserDTO getAuthor() {
		return author;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setStatus(ThreadStatus status) {
		this.status = status;
	}
	
	public void setResponses(List<ResponseDTO> responses) {
		this.responses = responses;
	}
	
	public void setAuthor(GeneralUserDTO author) {
		this.author = author;
	}
	
	
	public void deleteQuestion() {
		
	}
}
