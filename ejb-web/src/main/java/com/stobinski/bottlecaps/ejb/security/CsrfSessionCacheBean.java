package com.stobinski.bottlecaps.ejb.security;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@SessionScoped
public class CsrfSessionCacheBean implements Serializable, ISessionCache {

	private static final long serialVersionUID = -4776662887609673255L;
	private Cache<String, Boolean> csrfPreventionSaltCache;
	public static final String SALT = "csrfPreventionSalt";
	public static final String SALT_CACHE = "csrfPreventionSaltCache";
	
	@Override
	public void attachCacheToSession(HttpSession httpSession) {
        csrfPreventionSaltCache = CacheBuilder.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .build();
        
        httpSession.setAttribute(SALT_CACHE, csrfPreventionSaltCache);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean match(String httpReqSalt, HttpSession httpSession) {
        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)
            httpSession.getAttribute(SALT_CACHE);
        
        return (csrfPreventionSaltCache != null && httpReqSalt != null &&
                csrfPreventionSaltCache.getIfPresent(httpReqSalt) != null);
	}

	@Override
	public void updateCachedValue(HttpServletRequest httpReq, String value) {
		csrfPreventionSaltCache.put(value, Boolean.TRUE);
		httpReq.setAttribute(SALT, value);
	}

	@Override
	public void clearCache(HttpServletRequest httpReq) {
		httpReq.getSession().setAttribute(SALT_CACHE, null);
		httpReq.setAttribute(SALT, null);
	}
	
}
