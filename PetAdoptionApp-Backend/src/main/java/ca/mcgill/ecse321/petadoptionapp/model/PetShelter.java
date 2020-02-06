package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("PetShelter")
public class PetShelter extends GeneralUser{
   private Set<Donation> receivedDonations;
   
   @OneToMany(mappedBy="donatedTo" )
   public Set<Donation> getReceivedDonations() {
      return this.receivedDonations;
   }
   
   public void setReceivedDonations(Set<Donation> receivedDonationss) {
      this.receivedDonations = receivedDonationss;
   }
   
   private Integer balance;

public void setBalance(Integer value) {
    this.balance = value;
}
public Integer getBalance() {
    return this.balance;
}
}
