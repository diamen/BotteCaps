package com.stobinski.bottlecaps.ejb.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.stobinski.bottlecaps.ejb.security.AuthToken;
import com.stobinski.bottlecaps.ejb.security.SessionCache;
import com.stobinski.bottlecaps.ejb.security.ISessionCache;

@Provider
@AuthToken
public class AuthRequestInterceptor implements ContainerRequestFilter {

	@Inject
	@SessionCache(SessionCache.Type.TOKEN)
	private ISessionCache sessionCache;
	
	@Context
	private HttpServletRequest httpReq;
	
	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		String authToken = requestCtx.getHeaderString("AUTH-TOKEN");
		
		if(!sessionCache.match(authToken, httpReq.getSession())) {
			requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

}
