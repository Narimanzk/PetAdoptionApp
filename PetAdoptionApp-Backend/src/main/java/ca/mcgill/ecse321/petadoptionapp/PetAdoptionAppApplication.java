package ca.mcgill.ecse321.petadoptionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class PetAdoptionAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetAdoptionAppApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting(){
		return "It's working!";
	}

}
