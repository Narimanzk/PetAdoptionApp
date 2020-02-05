package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Donation{
   private Integer amount;

public void setAmount(Integer value) {
    this.amount = value;
}
public Integer getAmount() {
    return this.amount;
}
private PetShelter donatedTo;

@ManyToOne(optional=false)
public PetShelter getDonatedTo() {
   return this.donatedTo;
}

public void setDonatedTo(PetShelter donatedTo) {
   this.donatedTo = donatedTo;
}

private RegularUser donatedFrom;

@ManyToOne(optional=false)
public RegularUser getDonatedFrom() {
   return this.donatedFrom;
}

public void setDonatedFrom(RegularUser donatedFrom) {
   this.donatedFrom = donatedFrom;
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
