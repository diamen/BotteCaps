package com.stobinski.bottlecaps.ejb.user;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.entities.Users;
import com.stobinski.bottlecaps.ejb.wrappers.Login;

@Stateless
public class UserLoginValidator {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	private Logger log = Logger.getLogger(getClass());
	
	public boolean validate(Login login) {
		String formPassword = login.getPassword();
		Users user = null;
		
		try {
			user = entityManager.find(Users.class, login.getUsername());
			String dbPassword = user.getPassword(); 
			return formPassword.equals(dbPassword);
		}
		catch(EJBException | NullPointerException e) {
			log.error(e);
			return false;
		}
	}
	
}
