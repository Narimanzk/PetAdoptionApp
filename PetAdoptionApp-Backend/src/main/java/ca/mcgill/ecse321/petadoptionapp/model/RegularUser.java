package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class RegularUser extends User{
   private String personalDescription;

public void setPersonalDescription(String value) {
    this.personalDescription = value;
}
public String getPersonalDescription() {
    return this.personalDescription;
}
   private Set<Donation> donations;
   
   @OneToMany(mappedBy="donatedFrom" )
   public Set<Donation> getDonations() {
      return this.donations;
   }
   
   public void setDonations(Set<Donation> donationss) {
      this.donations = donationss;
   }
   
   private Set<Question> questions;
   
   @OneToMany(mappedBy="user" )
   public Set<Question> getQuestions() {
      return this.questions;
   }
   
   public void setQuestions(Set<Question> questionss) {
      this.questions = questionss;
   }
   
   }
