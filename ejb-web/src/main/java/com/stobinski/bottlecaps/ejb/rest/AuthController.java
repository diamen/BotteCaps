package com.stobinski.bottlecaps.ejb.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	private Logger log;
	
	@Inject 
	private UserLoginValidator loginValidator;
	
	@Inject
	@SessionCache(SessionCache.Type.TOKEN)
	private ISessionCache sessionCache;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login")
	public Response login(
			@FormParam("username") String username, 
			@FormParam("password") String password,
			@Context HttpServletRequest httpRequest) {
		
		MessageDigest messageDigest = null;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
		}
		
		byte[] hash = messageDigest.digest(password.getBytes());
		String passwordHash = Base64Utils.tob64(hash);
		Login login = new Login();
		login.setUsername(username);
		login.setPassword(passwordHash);
		
		Map<String, String> entity = new HashMap<>();
		
		if(loginValidator.validate(login)) {
			String token = PasswordGenerator.generate();
			sessionCache.attachCacheToSession(httpRequest.getSession());
			sessionCache.updateCachedValue(httpRequest, token);
			entity.put("token", token);
			return Response.ok().entity(entity).type(MediaType.APPLICATION_JSON).build();
		} else {
			entity.put("message", "Wrong username or password");
			return Response.status(Response.Status.UNAUTHORIZED).entity(entity).type(MediaType.APPLICATION_JSON).build();
		}		
	}
	
	@GET
	@Path("validate")
	public boolean validatePermissions(@Context HttpServletRequest httpReq) {
		return sessionCache.match(httpReq.getHeader("AUTH-TOKEN"), httpReq.getSession());
//		return sessionCache.match(httpReq.getHeader("AUTH-TOKEN"), httpReq.getSession()) ? 
//				Response.ok().entity(true).build() : Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@POST
	@AuthToken
	@Path("secure/logout")
	public void logout(@Context HttpServletRequest httpReq) {
		sessionCache.clearCache(httpReq);
	}
	
}
