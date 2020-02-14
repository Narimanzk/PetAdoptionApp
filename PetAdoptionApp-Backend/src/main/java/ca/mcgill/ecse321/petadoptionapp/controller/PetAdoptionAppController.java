package ca.mcgill.ecse321.petadoptionapp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.petadoptionapp.service.PetAdoptionAppService;

@CrossOrigin(origins = "*")
@RestController
public class PetAdoptionAppController {
	@Autowired
	private PetAdoptionAppService service;
}
