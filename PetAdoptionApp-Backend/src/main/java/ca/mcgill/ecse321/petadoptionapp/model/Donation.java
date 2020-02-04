package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Donation{
   private Integer amount;

public void setAmount(Integer value) {
    this.amount = value;
}
public Integer getAmount() {
    return this.amount;
}
   private PetShelter petShelter;
   
   @ManyToOne(optional=false)
   public PetShelter getPetShelter() {
      return this.petShelter;
   }
   
   public void setPetShelter(PetShelter petShelter) {
      this.petShelter = petShelter;
   }
   
   private Set<RegularUser> donation;
   
   @OneToMany(mappedBy="user" )
   public Set<RegularUser> getDonation() {
      return this.donation;
   }
   
   public void setDonation(Set<RegularUser> donations) {
      this.donation = donations;
   }
   
   }
