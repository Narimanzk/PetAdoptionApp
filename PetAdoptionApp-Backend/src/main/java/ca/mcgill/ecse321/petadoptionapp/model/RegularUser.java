package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
   private Donation user;
   
   @ManyToOne(optional=false)
   public Donation getUser() {
      return this.user;
   }
   
   public void setUser(Donation user) {
      this.user = user;
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
