package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class GeneralUser{
   private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private String phone;

public void setPhone(String value) {
    this.phone = value;
}
public String getPhone() {
    return this.phone;
}
private String email;

public void setEmail(String value) {
    this.email = value;
}
public String getEmail() {
    return this.email;
}
private Set<Response> responses;

@OneToMany(mappedBy="user" )
public Set<Response> getResponses() {
   return this.responses;
}

public void setResponses(Set<Response> responsess) {
   this.responses = responsess;
}

private Address address;

@OneToOne
public Address getAddress() {
   return this.address;
}

public void setAddress(Address address) {
   this.address = address;
}

private Set<PetProfile> petProfiles;

@OneToMany(mappedBy="user" )
public Set<PetProfile> getPetProfiles() {
   return this.petProfiles;
}

public void setPetProfiles(Set<PetProfile> petProfiless) {
   this.petProfiles = petProfiless;
}

private Set<AdoptionApplication> adoptionApplications;

@ManyToMany(mappedBy="user" )
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
}
