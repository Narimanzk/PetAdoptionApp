package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class PetProfile{
   private String petName;

public void setPetName(String value) {
    this.petName = value;
}
public String getPetName() {
    return this.petName;
}
private String image;

public void setImage(String value) {
    this.image = value;
}
public String getImage() {
    return this.image;
}
private String reason;

public void setReason(String value) {
    this.reason = value;
}
public String getReason() {
    return this.reason;
}
private Integer age;

public void setAge(Integer value) {
    this.age = value;
}
public Integer getAge() {
    return this.age;
}
private Boolean gender;

public void setGender(Boolean value) {
    this.gender = value;
}
public Boolean getGender() {
    return this.gender;
}
   private User user;
   
   @ManyToOne(optional=false)
   public User getUser() {
      return this.user;
   }
   
   public void setUser(User user) {
      this.user = user;
   }
   
   private Set<AdoptionApplication> adoptionApplication;
   
   @OneToMany(mappedBy="petProfile" )
   public Set<AdoptionApplication> getAdoptionApplication() {
      return this.adoptionApplication;
   }
   
   public void setAdoptionApplication(Set<AdoptionApplication> adoptionApplications) {
      this.adoptionApplication = adoptionApplications;
   }
   
   }
