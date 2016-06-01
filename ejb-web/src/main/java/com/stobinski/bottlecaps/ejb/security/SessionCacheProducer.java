package com.stobinski.bottlecaps.ejb.security;

import javax.enterprise.inject.Produces;
import com.stobinski.bottlecaps.ejb.security.SessionCache.Type;


public class SessionCacheProducer {
	
	@Produces
	@SessionCache(Type.CSRF)
	public iSessionCache csrfSessionCacheFactory() {
		return new CsrfSessionCacheBean();
	}
	
	@Produces
	@SessionCache(Type.TOKEN)
	public iSessionCache authTokenSessionCacheFactory() {
		return new AuthTokenSessionCacheBean();
	}
	
}
