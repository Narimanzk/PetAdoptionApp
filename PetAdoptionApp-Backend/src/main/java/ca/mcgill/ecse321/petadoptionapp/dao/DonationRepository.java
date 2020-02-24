package ca.mcgill.ecse321.petadoptionapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Donation;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;

public interface DonationRepository extends CrudRepository<Donation, Integer>{
    Donation findDonationById(Integer id);
    
    List<Donation> findByDonatedFrom(GeneralUser user);
    
    List<Donation> findByDonatedTo(GeneralUser shelter);
}
