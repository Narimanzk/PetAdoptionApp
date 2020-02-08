package ca.mcgill.ecse321.petadoptionapp.dao;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.petadoptionapp.model.GeneralUser;
import ca.mcgill.ecse321.petadoptionapp.model.RegularUser;

@Repository
public class PetAdoptionAppRepository {

		@Autowired
		EntityManager entityManager;

		@Transactional
		public RegularUser createRegularUser(String username, String password, String email) {
		      RegularUser newUser = new RegularUser();
		      newUser.setUsername(username);
		      newUser.setPassword(password);
		      newUser.setEmail(email);
		      entityManager.persist(newUser);
		      return newUser;
		}

		@Transactional
		public RegularUser getPerson(String username) {
			RegularUser user = entityManager.find(RegularUser.class, username);
			return user;
		}
}
