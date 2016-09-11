package com.stobinski.bottlecaps.ejb.servlets;

import java.io.IOException;
import java.security.SecureRandom;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;

import com.stobinski.bottlecaps.ejb.security.SessionCache;
import com.stobinski.bottlecaps.ejb.security.CsrfSessionCacheBean;
import com.stobinski.bottlecaps.ejb.security.ISessionCache;

@WebFilter(filterName = "loadSaltFilter", urlPatterns = { "/admin/login" } )
public class LoadSaltFilter implements Filter {

	@Inject
	@SessionCache(SessionCache.Type.CSRF)
	private ISessionCache sessionCache;
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;
		
        if(!(httpReq.getSession().getAttribute(CsrfSessionCacheBean.SALT_CACHE) != null)) {
        	sessionCache.attachCacheToSession(httpReq.getSession());
        }

        String salt = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());
        sessionCache.updateCachedValue(httpReq, salt);
        
        chain.doFilter(httpReq, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

}
