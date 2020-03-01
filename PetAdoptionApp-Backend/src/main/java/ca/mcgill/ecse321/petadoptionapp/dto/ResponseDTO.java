package ca.mcgill.ecse321.petadoptionapp.dto;

public class ResponseDTO {
	private Integer id;
	private String text;
	private QuestionDTO question;
	private GeneralUserDTO user;
	
	public ResponseDTO() {
		
	}
	
	public ResponseDTO(Integer id, String text) {
		this.text = text;
	}
	
	public Integer getID() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public QuestionDTO getQuestion() {
		return question;
	}
	
	public GeneralUserDTO getUser() {
		return user;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setQuestion(QuestionDTO question) {
		this.question = question;
	}
	
	public void setUser(GeneralUserDTO user) {
		this.user = user;
	}
}
