package com.stobinski.bottlecaps.ejb.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.stobinski.bottlecaps.ejb.security.SessionCache;
import com.stobinski.bottlecaps.ejb.security.iSessionCache;

@WebFilter(filterName = "validateSaltFilter", urlPatterns = { "/rest/auth/*" } )
public class ValidateSaltFilter implements Filter  {

	@Inject
	@SessionCache(SessionCache.Type.CSRF)
	private iSessionCache sessionCache;
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        
        if(!"application/x-www-form-urlencoded".equals(httpReq.getContentType())) {
        	chain.doFilter(request, response);
        } else {
        	sessionCache.init(httpReq);
        	String salt = (String) httpReq.getParameter("csrfPreventionSalt");

	        if(sessionCache.match(salt, httpReq.getSession())) {
	            chain.doFilter(request, response);
	        } else {
	            throw new ServletException("Potential CSRF detected!! Inform a scary sysadmin ASAP.");
	        }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
