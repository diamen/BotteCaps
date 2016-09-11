package com.stobinski.bottlecaps.ejb.security;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@SessionScoped
public class AuthTokenSessionCacheBean implements Serializable, ISessionCache {

	private static final long serialVersionUID = -8720325565573249407L;
	private Cache<String, Boolean> authTokenSaltCache;
	public static final String SALT = "authTokenPreventionSalt";
	public static final String SALT_CACHE = "authTokenPreventionSaltCache";
	
	@Override
	public void attachCacheToSession(HttpSession httpSession) {
        authTokenSaltCache = CacheBuilder.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .build();
        
        httpSession.setAttribute(SALT_CACHE, authTokenSaltCache);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean match(String value, HttpSession httpSession) {
        Cache<String, Boolean> authTokenPreventionSaltCache = (Cache<String, Boolean>)
            httpSession.getAttribute(SALT_CACHE);
        
        return (authTokenPreventionSaltCache != null && value != null &&
                authTokenPreventionSaltCache.getIfPresent(value) != null);
	}
	
	@Override
	public void updateCachedValue(HttpServletRequest httpReq, String value) {
		authTokenSaltCache.put(value, Boolean.TRUE);
		httpReq.setAttribute(SALT, value);
	}

	@Override
	public void clearCache(HttpServletRequest httpReq) {
		httpReq.getSession().setAttribute(SALT_CACHE, null);
		httpReq.setAttribute(SALT, null);
	}
	
}
