package com.stobinski.bottlecaps.ejb.security;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@SessionScoped
public class AuthTokenSessionCacheBean implements Serializable, iSessionCache {

	private static final long serialVersionUID = -8720325565573249407L;
	private Cache<String, Boolean> authTokenSaltCache;
	private HttpServletRequest httpReq;
	private HttpSession httpSession;
	
	@Override
	public void init(HttpServletRequest httpServletRequest) {
		this.httpReq = httpServletRequest;
		this.httpSession = httpReq.getSession();
	}
	
	@Override
	public void attachCacheToSession() {
        authTokenSaltCache = CacheBuilder.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .build();
        
        httpSession.setAttribute("authTokenPreventionSaltCache", authTokenSaltCache);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean match(String value, HttpSession httpSession) {
        Cache<String, Boolean> authTokenPreventionSaltCache = (Cache<String, Boolean>)
            httpSession.getAttribute("authTokenPreventionSaltCache");
        
        if(authTokenPreventionSaltCache != null && value != null &&
                authTokenPreventionSaltCache.getIfPresent(value) != null) {
        	return true;
        } else {
        	return false;
        }
	}
	
	@Override
	public void updateCachedValue(String value) {
		authTokenSaltCache.put(value, Boolean.TRUE);
		httpReq.setAttribute("authTokenPreventionSalt", value);
	}

	@Override
	public void clearCache(HttpServletRequest httpReq) {
		httpReq.getSession().setAttribute("authTokenPreventionSaltCache", null);
		httpReq.setAttribute("authTokenPreventionSalt", null);
	}
	
}
