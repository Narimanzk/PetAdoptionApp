package ca.mcgill.ecse321.petadoptionapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.petadoptionapp.dto.AddressDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.AdoptionApplicationDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.GeneralUserDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.PetProfileDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.ResponseDTO;
import ca.mcgill.ecse321.petadoptionapp.model.Address;
import ca.mcgill.ecse321.petadoptionapp.model.AdoptionApplication;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.Response;
import ca.mcgill.ecse321.petadoptionapp.service.PetAdoptionAppService;

@CrossOrigin(origins = "*")
@RestController
public class PetAdoptionAppController {
	@Autowired
	private PetAdoptionAppService service;

	@GetMapping(value = { "/petprofiles" })
	public List<PetProfileDTO> getAllPetProfiles() {
		return service.getAllPetProfile().stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	@PostMapping(value = {
			"users/{username}/petprofiles" }, consumes = "application/json", produces = "application/json")
	public PetProfileDTO createPetProfile(@PathVariable("username") String username, @RequestBody PetProfileDTO pet) {
		GeneralUser user = service.getGeneralUser(username);
		PetProfile petProfile = service.createPetProfile(pet.getName(), pet.getAge(), pet.getGender(),
				pet.getDescription(), pet.getSpecies(), pet.getProfilePic(), pet.getReason(), user);
		return convertToDTO(petProfile);
	}

	@GetMapping(value = { "users/{username}/petprofile" })
	public List<PetProfileDTO> getPetProfileByUser(@PathVariable("username") String username) {
		GeneralUser user = service.getGeneralUser(username);
		return service.getPetProfileByUser(user).stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}

	private GeneralUserDTO convertToDTO(GeneralUser user) {
		// GeneralUserDTO userDTO = new
		// GeneralUserDTO(user.getUsername(),user.getEmail(),null,
		// user.getName(),user.getPhone(),user.getPersonalDescription(),user.getProfilePicture());
		// if(user.getAddress()!=null)userDTO.setAddress(convertToAttributeDTO(user.getAddress()));
		// List<AdoptionApplicationDTO> applications = new
		// ArrayList<AdoptionApplicationDTO>();
		// for(AdoptionApplication app: user.getAdoptionApplications()) {
		// applications.add(convertToAttributeDTO(app));
		// }
		// userDTO.setAdoptionApplications(applications);
		//
		// List<PetProfileDTO> profiles = new ArrayList<PetProfileDTO>();
		// for(PetProfile prof: user.getPetProfiles()) {
		// profiles.add(convertToAttributeDTO(prof));
		// }
		// userDTO.setPetProfiles(profiles);
		//
		// List<ResponseDTO> responses = new ArrayList<ResponseDTO>();
		// for(Response res: user.getResponses()) {
		// responses.add(convertToAttributeDTO(res));
		// }
		// userDTO.setResponses(responses);
		return null;
	}

	private PetProfileDTO convertToDTO(PetProfile p) {
		PetProfileDTO pet = new PetProfileDTO(p.getId(), p.getPetName(), p.getAge(), p.getPetGender(),
				p.getDescription(), p.getPetSpecies(), p.getProfilePicture(), p.getReason());
		pet.setUser(convertToAttributeDTO(p.getUser()));
		return pet;
	}

	private PetProfileDTO convertToAttributeDTO(PetProfile prof) {
		// TODO Auto-generated method stub
		return null;
	}

	private ResponseDTO convertToAttributeDTO(Response res) {
		// TODO Auto-generated method stub
		return null;
	}

	private AdoptionApplicationDTO convertToAttributeDTO(AdoptionApplication app) {
		// TODO Auto-generated method stub
		return null;
	}

	private AddressDTO convertToAttributeDTO(Address address) {
		// TODO Auto-generated method stub
		return null;
	}

	private GeneralUserDTO convertToAttributeDTO(GeneralUser user) {
		return null;
	}
}
