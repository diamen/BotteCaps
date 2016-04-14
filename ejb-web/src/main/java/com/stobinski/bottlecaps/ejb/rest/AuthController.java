package com.stobinski.bottlecaps.ejb.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.jboss.security.Base64Utils;

import com.stobinski.bottlecaps.ejb.user.UserLoginValidator;
import com.stobinski.bottlecaps.ejb.wrappers.Login;

@Path("/api/")
public class AuthController {
	
	@Inject
	private Logger logger;
	
	@Inject 
	private UserLoginValidator loginValidator;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("auth")
	public Response.Status.Family login(Login login) {

		String password = login.getPassword();
		MessageDigest messageDigest = null;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
		
		byte[] hash = messageDigest.digest(password.getBytes());
		String passwordHash = Base64Utils.tob64(hash);
		login.setPassword(passwordHash);
		
		if(loginValidator.validate(login))
			return Response.Status.Family.SUCCESSFUL;
		else
			return Response.Status.Family.CLIENT_ERROR;
	}
	
}
