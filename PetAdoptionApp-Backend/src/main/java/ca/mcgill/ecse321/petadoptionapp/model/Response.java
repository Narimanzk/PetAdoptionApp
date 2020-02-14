package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Response {
	private String text;

	public void setText(String value) {
		this.text = value;
	}
	@Column(columnDefinition = "TEXT")
	public String getText() {
		return this.text;
	}

	private Question question;

	@ManyToOne(optional = false)
	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	private GeneralUser user;

	@ManyToOne(optional = false)
	public GeneralUser getUser() {
		return this.user;
	}

	public void setUser(GeneralUser user) {
		this.user = user;
	}

	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}
}
