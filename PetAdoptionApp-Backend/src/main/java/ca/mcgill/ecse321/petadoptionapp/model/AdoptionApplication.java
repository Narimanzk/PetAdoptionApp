package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class AdoptionApplication{
   private Set<GeneralUser> user;
   
   @ManyToMany
   public Set<GeneralUser> getUser() {
      return this.user;
   }
   
   public void setUser(Set<GeneralUser> users) {
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
   
   @Enumerated(EnumType.ORDINAL)
   @Column(name = "applicationStatus")
   private ApplicationStatus applicationStatus;
   
   public void setPetGender(ApplicationStatus value) {
     this.applicationStatus = value;
 }

  public ApplicationStatus getPetGender() {
    return this.applicationStatus;
  }
}
