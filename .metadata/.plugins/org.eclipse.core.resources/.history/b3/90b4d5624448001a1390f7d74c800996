package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class AdoptionApplication{
   private Set<User> user;
   
   @ManyToMany
   public Set<User> getUser() {
      return this.user;
   }
   
   public void setUser(Set<User> users) {
      this.user = users;
   }
   
   private PetProfile petProfile;
   
   @ManyToOne(optional=false)
   public PetProfile getPetProfile() {
      return this.petProfile;
   }
   
   public void setPetProfile(PetProfile petProfile) {
      this.petProfile = petProfile;
   }
   
   private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
private String applicationDescription;

public void setApplicationDescription(String value) {
    this.applicationDescription = value;
}
public String getApplicationDescription() {
    return this.applicationDescription;
}
}
