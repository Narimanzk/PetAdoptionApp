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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.petadoptionapp.dto.RegularUserDTO;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;
import ca.mcgill.ecse321.petadoptionapp.service.PetAdoptionAppService;

@CrossOrigin(origins = "*")
@RestController
public class PetAdoptionAppController {
  @Autowired
  private PetAdoptionAppService service;

  @GetMapping(value = {"/RegularUsers", "/RegularUsers/"})
  public List<RegularUserDTO> getAllRegularUser() {
    return service.getAllRegularUser().stream().map(p -> convertToDto(p))
        .collect(Collectors.toList());
  }

  @PostMapping(value = {"/RegularUsers", "/RegularUsers/"})
  public RegularUserDTO createRegularUser(@RequestParam("username") String username,
      @RequestParam("password") String password, @RequestParam("email") String email,
      @RequestParam("name") String name) throws IllegalArgumentException {
    RegularUser regularuser = service.createRegularuser(username, password, email, name);
    return convertToDto(regularuser);
  }

  private RegularUserDTO convertToDto(RegularUser regularuser) {
    // TODO Auto-generated method stub
    return null;
  }

  private RegularUserDTO convertToAttributeDto(RegularUser regularuser) {
    return null;
  }
}
