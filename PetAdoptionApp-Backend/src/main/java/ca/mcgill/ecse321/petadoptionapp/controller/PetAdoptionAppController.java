package ca.mcgill.ecse321.petadoptionapp.controller;

import java.util.List;
import java.util.Map;
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

import ca.mcgill.ecse321.petadoptionapp.dto.GeneralUserDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.PetProfileDTO;
import ca.mcgill.ecse321.petadoptionapp.dto.RegularUserDTO;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.PetProfile;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;
import ca.mcgill.ecse321.petadoptionapp.service.PetAdoptionAppService;

@CrossOrigin(origins = "*")
@RestController
public class PetAdoptionAppController {
	@Autowired
	private PetAdoptionAppService service;

	@GetMapping(value = { "/user", "/users" })
	public List<RegularUserDTO> getAllUsers() {
		return service.getAllRegularUser().stream().map(p -> convertToRegularUserDto(p)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/user"}, consumes = "application/json", produces = "application/json")
	public RegularUserDTO createPetProfile(@RequestBody RegularUserDTO userDto){
		RegularUser user = service.createRegularuser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(),userDto.getName());
		return convertToRegularUserDto(user);
	}
	
	@GetMapping(value = { "/petprofiles", "/petprofile" })
	public List<PetProfileDTO> getAllPetProfiles() {
		return service.getAllPetProfile().stream().map(p -> convertToPetProfileDto(p)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/{username}/petprofile"}, consumes = "application/json", produces = "application/json")
	public PetProfileDTO createPetProfile(@PathVariable("username") String username, @RequestBody PetProfileDTO pet){
		GeneralUser user = service.getRegularUser(username);
		PetProfile petProfile = service.createPetProfile(pet.getName(), pet.getAge(), pet.getGender(), pet.getDescription(), 
				pet.getSpecies(), pet.getProfilePic(), pet.getReason(), user);
		return convertToPetProfileDto(petProfile);
	}
	
	@GetMapping(value = {"/{username}/petprofile"})
	public List<PetProfileDTO> getPetProfileByUser(@PathVariable("username") String username){
		GeneralUser user = service.getRegularUser(username);
		return service.getPetProfileByUser(user).stream().map(p -> convertToPetProfileDto(p)).collect(Collectors.toList());
	}

	private PetProfileDTO convertToPetProfileDto(PetProfile p) {
		PetProfileDTO pet = new PetProfileDTO(p.getId(), p.getPetName(), p.getAge(), p.getPetGender(),
				p.getDescription(), p.getPetSpecies(), p.getProfilePicture(), p.getReason());
		pet.setUser(convertToRegularUserDto(p.getUser()));
		return pet;
	}

	private RegularUserDTO convertToRegularUserDto(GeneralUser user) {
		RegularUserDTO new_user = new RegularUserDTO(user.getName(), user.getEmail(), user.getPassword());
		return new_user;
	}
}
