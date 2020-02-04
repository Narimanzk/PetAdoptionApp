package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class Question{
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
public String getDescription() {
    return this.description;
}
   private Set<Response> responses;
   
   @OneToMany(mappedBy="question" , cascade={CascadeType.ALL})
   public Set<Response> getResponses() {
      return this.responses;
   }
   
   public void setResponses(Set<Response> responsess) {
      this.responses = responsess;
   }
   
   private RegularUser user;
   
   @ManyToOne(optional=false)
   public RegularUser getUser() {
      return this.user;
   }
   
   public void setUser(RegularUser user) {
      this.user = user;
   }
   
   }
