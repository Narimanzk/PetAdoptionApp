package ca.mcgill.ecse321.petadoptionapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Donation;
import ca.mcgill.ecse321.petadoptionapp.model.PetShelter;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;

public interface DonationRepository extends CrudRepository<Donation, Integer>{
    Donation findDonationById(Integer id);
    
    List<Donation> findByDonatedFrom(RegularUser user);
    
    List<Donation> findByDonatedTo(PetShelter shelter);
}
