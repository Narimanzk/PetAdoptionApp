package ca.mcgill.ecse321.petadoptionapp.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petadoptionapp.model.Donation;

public interface DonationRepository extends CrudRepository<Donation, Integer>{
    Donation findDonationById(Integer id);
}