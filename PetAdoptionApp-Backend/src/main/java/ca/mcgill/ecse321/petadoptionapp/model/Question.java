package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Question {
	private String status;

	public void setStatus(String value) {
		this.status = value;
	}

	public String getStatus() {
		return this.status;
	}

	private String title;

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}

	private String description;

	public void setDescription(String value) {
		this.description = value;
	}
	@Column(columnDefinition = "TEXT")
	public String getDescription() {
		return this.description;
	}

	private Set<Response> responses;

	@OneToMany(mappedBy = "question", cascade = { CascadeType.ALL })
	public Set<Response> getResponses() {
		return this.responses;
	}

	public void setResponses(Set<Response> responsess) {
		this.responses = responsess;
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

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "Threadstatus")
	private ThreadStatus threadStatus;

	public void setThreadStatus(ThreadStatus value) {
		this.threadStatus = value;
	}

	public ThreadStatus getThreadStatus() {
		return this.threadStatus;
	}
}
