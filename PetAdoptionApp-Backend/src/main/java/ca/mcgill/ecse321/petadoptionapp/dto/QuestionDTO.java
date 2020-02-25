package ca.mcgill.ecse321.petadoptionapp.dto;

import java.util.List;

import ca.mcgill.ecse321.petadoptionapp.model.ThreadStatus;

public class QuestionDTO {

	private String title; 
	private String description;
	private ThreadStatus status;
	private List<ResponseDTO> responses;
	private GeneralUserDTO author; 
	
	public QuestionDTO() {
		
	}
	
	public QuestionDTO(String title, String description, ThreadStatus status, GeneralUserDTO author, List<ResponseDTO> responses) {
		this.title = title; 
		this.description = description; 
		this.status = status;
		this.author = author; 
		this.responses = responses;
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
}
