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
public class AdoptionApplication {
  private GeneralUser user;
  
  @ManyToOne(optional=false)
  public GeneralUser getUser() {
     return this.user;
  }
  
  public void setUser(GeneralUser user) {
     this.user = user;
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

  public void setApplicationStatus(ApplicationStatus value) {
    this.applicationStatus = value;
  }

  public ApplicationStatus getApplicationStatus() {
    return this.applicationStatus;
  }
}
