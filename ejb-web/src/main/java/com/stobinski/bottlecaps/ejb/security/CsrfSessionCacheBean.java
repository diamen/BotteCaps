package com.stobinski.bottlecaps.ejb.security;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@SessionScoped
public class CsrfSessionCacheBean implements Serializable, iSessionCache {

	private static final long serialVersionUID = -4776662887609673255L;
	private Cache<String, Boolean> csrfPreventionSaltCache;
	private HttpServletRequest httpReq;
	
	@Override
	public void init(HttpServletRequest httpServletRequest) {
		this.httpReq = httpServletRequest;
	}
	
	@Override
	public void attachCacheToSession() {
        csrfPreventionSaltCache = CacheBuilder.newBuilder()
                .maximumSize(5000)
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .build();
        
        httpReq.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean match(String value, HttpSession httpSession) {
        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)
            httpSession.getAttribute("csrfPreventionSaltCache");
        
        if(csrfPreventionSaltCache != null && value != null &&
                csrfPreventionSaltCache.getIfPresent(value) != null) {
        	return true;
        } else {
        	return false;
        }
	}

	@Override
	public void updateCachedValue(String value) {
		csrfPreventionSaltCache.put(value, Boolean.TRUE);
		httpReq.setAttribute("csrfPreventionSalt", value);
	}

	@Override
	public void clearCache(HttpServletRequest httpReq) {
		// TODO Auto-generated method stub
		
	}
	
}
