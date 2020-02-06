package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

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
private GeneralUser user;

@ManyToOne(optional=false)
public GeneralUser getUser() {
   return this.user;
}

public void setUser(GeneralUser user) {
   this.user = user;
}

private Set<AdoptionApplication> adoptionApplications;

@OneToMany(mappedBy="petProfile" )
public Set<AdoptionApplication> getAdoptionApplications() {
   return this.adoptionApplications;
}

public void setAdoptionApplications(Set<AdoptionApplication> adoptionApplicationss) {
   this.adoptionApplications = adoptionApplicationss;
}

private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
private String petSpecies;

public void setPetSpecies(String value) {
    this.petSpecies = value;
}
public String getPetSpecies() {
    return this.petSpecies;
}

@Enumerated(EnumType.ORDINAL)
@Column(name = "gender")
private Gender petGender;

public void setPetGender(Gender value) {
    this.petGender = value;
}
public Gender getPetGender() {
    return this.petGender;
}
}
