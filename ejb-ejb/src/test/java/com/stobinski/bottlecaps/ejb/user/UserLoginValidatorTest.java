package com.stobinski.bottlecaps.ejb.user;
import javax.persistence.EntityManager;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.stobinski.bottlecaps.ejb.entities.Users;
import com.stobinski.bottlecaps.ejb.user.UserLoginValidator;
import com.stobinski.bottlecaps.ejb.wrappers.Login;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginValidatorTest {

	@InjectMocks
	private UserLoginValidator userLoginValidator;
	
	@Mock
	private Logger log;
	
	@Mock
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldValidateCorrectly() {
		// given
		Login login = new Login(); login.setUsername("waldo"); login.setPassword("whereareyou");
		Users users = new Users(); users.setUsername("waldo"); users.setPassword("whereareyou");
		
		// when
		when(entityManager.find(any(Class.class), any(String.class))).thenReturn(users);
		boolean validated = userLoginValidator.validate(login);
		
		// then
		assertTrue(validated);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldNotValidateCorrectly() {
		// given
		Login login = new Login(); login.setUsername("waldo"); login.setPassword("whereareyou");
		Users users = new Users(); users.setUsername("waldo"); users.setPassword("imhere");
		
		// when
		when(entityManager.find(any(Class.class), any(String.class))).thenReturn(users);
		boolean validated = userLoginValidator.validate(login);
		
		// then
		assertFalse(validated);
	}
	
}
