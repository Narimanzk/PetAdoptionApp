package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("RegularUser")
public class RegularUser extends GeneralUser {
    
	private String personalDescription;

	public void setPersonalDescription(String value) {
		this.personalDescription = value;
	}
	// @Basic(fetch = FetchType.LAZY)
	//	@Lob
	//	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "TEXT")
	public String getPersonalDescription() {
		return this.personalDescription;
	}

	private Set<Donation> donations;

	@OneToMany(mappedBy = "donatedFrom")
	public Set<Donation> getDonations() {
		return this.donations;
	}

	public void setDonations(Set<Donation> donationss) {
		this.donations = donationss;
	}

	private Set<Question> questions;

	@OneToMany(mappedBy = "user")
	public Set<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set<Question> questionss) {
		this.questions = questionss;
	}

}
