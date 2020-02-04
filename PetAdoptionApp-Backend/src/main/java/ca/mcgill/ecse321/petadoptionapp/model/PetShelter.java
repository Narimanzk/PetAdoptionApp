package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class PetShelter extends User{
   private Set<Donation> donation;
   
   @OneToMany(mappedBy="petShelter" )
   public Set<Donation> getDonation() {
      return this.donation;
   }
   
   public void setDonation(Set<Donation> donations) {
      this.donation = donations;
   }
   
   }
