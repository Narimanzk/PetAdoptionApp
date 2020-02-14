package ca.mcgill.ecse321.petadoptionapp.service;

import java.util.List;
import javax.persistence.Lob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.petadoptionapp.dao.AddressRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.AdoptionApplicationRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.DonationRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.GeneralUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.PetProfileRespository;
import ca.mcgill.ecse321.petadoptionapp.dao.PetShelterRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.QuestionRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.RegularUserRepository;
import ca.mcgill.ecse321.petadoptionapp.dao.ResponseRepository;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;

@Service
public class PetAdoptionAppService {
  @Autowired
  private RegularUserRepository regularUserRepository;
  @Autowired
  private PetProfileRespository petProfileRespository;
  @Autowired
  private AdoptionApplicationRespository adoptionApplicationRespository;
  @Autowired
  private PetShelterRepository petShelterRepository;
  @Autowired
  private GeneralUserRepository generalUserRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private DonationRepository donationRepository;
  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private ResponseRepository responseRepository;
  
  @Transactional
  public RegularUser createRegularuser(String username,String password,String email) {
    RegularUser user = new RegularUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    regularUserRepository.save(user);
    return user;
  }
  
  @Transactional
  public RegularUser getRegularUser(String username) {
    RegularUser user = regularUserRepository.findRegularUserByUsername(username);
    return user;
  }
  
  @Transactional
  public List<RegularUser> getAllPersons() {
      return null;
      //toList(regularUserRepository.findAll());
  }
}
