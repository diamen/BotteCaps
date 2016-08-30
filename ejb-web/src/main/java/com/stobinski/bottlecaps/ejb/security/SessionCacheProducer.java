package com.stobinski.bottlecaps.ejb.security;

import javax.enterprise.inject.Produces;
import com.stobinski.bottlecaps.ejb.security.SessionCache.Type;


public class SessionCacheProducer {
	
	@Produces
	@SessionCache(Type.CSRF)
	public ISessionCache csrfSessionCacheFactory() {
		return new CsrfSessionCacheBean();
	}
	
	@Produces
	@SessionCache(Type.TOKEN)
	public ISessionCache authTokenSessionCacheFactory() {
		return new AuthTokenSessionCacheBean();
	}
	
}
