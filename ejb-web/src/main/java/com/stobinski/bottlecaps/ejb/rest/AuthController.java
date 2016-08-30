package com.stobinski.bottlecaps.ejb.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;
import org.jboss.security.Base64Utils;

import com.stobinski.bottlecaps.ejb.security.AuthToken;
import com.stobinski.bottlecaps.ejb.security.PasswordGenerator;
import com.stobinski.bottlecaps.ejb.security.SessionCache;
import com.stobinski.bottlecaps.ejb.security.ISessionCache;
import com.stobinski.bottlecaps.ejb.user.UserLoginValidator;
import com.stobinski.bottlecaps.ejb.wrappers.Login;

@Path("/auth/")
public class AuthController {
	
	@Inject
	private Logger logger;
	
	@Inject 
	private UserLoginValidator loginValidator;
	
	@Inject
	@SessionCache(SessionCache.Type.TOKEN)
	private ISessionCache sessionCache;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login")
	public String login(
			@FormParam("username") String username, 
			@FormParam("password") String password,
			@Context HttpServletRequest httpServletRequest) {
		
		MessageDigest messageDigest = null;
		sessionCache.init(httpServletRequest);
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
		
		byte[] hash = messageDigest.digest(password.getBytes());
		String passwordHash = Base64Utils.tob64(hash);
		Login login = new Login();
		login.setUsername(username);
		login.setPassword(passwordHash);
		
		if(loginValidator.validate(login)) {
			String token = PasswordGenerator.generate();
			sessionCache.attachCacheToSession();
			sessionCache.updateCachedValue(token);
			return token;
		} else {
			String token = PasswordGenerator.generate();
			sessionCache.attachCacheToSession();
			sessionCache.updateCachedValue(token);
			return token;
		}		
	}
	
	@GET
	@Path("validate")
	public boolean validatePermissions(@Context HttpServletRequest httpReq) {
		return sessionCache.match(httpReq.getHeader("AUTH-TOKEN"), httpReq.getSession());
	}
	
	@POST
	@AuthToken
	@Path("secure/logout")
	public void logout(@Context HttpServletRequest httpReq) {
		sessionCache.clearCache(httpReq);
	}
	
}
