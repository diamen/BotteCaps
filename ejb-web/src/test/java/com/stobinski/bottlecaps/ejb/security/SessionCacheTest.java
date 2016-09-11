package com.stobinski.bottlecaps.ejb.security;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.cache.Cache;

@RunWith(MockitoJUnitRunner.class)
public class SessionCacheTest {

	@InjectMocks
	private AuthTokenSessionCacheBean sessionCacheBean;
	
	@Mock
	private HttpServletRequest httpRequest;
	
	@Mock
	private HttpSession httpSession;
	
	@Mock
	private Cache<String, Boolean> authTokenSaltCache;
	
	@Test
	public void shouldAttachCacheToHttpSession() {
		// when
		sessionCacheBean.attachCacheToSession(httpSession);
		
		// then
		verify(httpSession, times(1)).setAttribute(any(String.class), any(Cache.class));
	}
	
	@Test
	public void shouldSaltAndCacheMatch() {
		// given
		String salt = PasswordGenerator.generate();
		
		// when
		when(httpSession.getAttribute(AuthTokenSessionCacheBean.SALT_CACHE)).thenReturn(authTokenSaltCache);
		when(authTokenSaltCache.getIfPresent(salt)).thenReturn(true);
		boolean isMatch = sessionCacheBean.match(salt, httpSession);
		
		// then
		assertTrue(isMatch);
	}
	
	@Test
	public void shouldUpdateCachedValue() {
		// given
		String salt = PasswordGenerator.generate();
		
		// when
		sessionCacheBean.updateCachedValue(httpRequest, salt);
		
		// then
		verify(authTokenSaltCache, times(1)).put(salt, true);;
		verify(httpRequest, times(1)).setAttribute(AuthTokenSessionCacheBean.SALT, salt);
	}
	
	@Test
	public void shouldClearTheCache() {
		// when
		when(httpRequest.getSession()).thenReturn(httpSession);
		sessionCacheBean.clearCache(httpRequest);
		
		// then
		verify(httpSession, times(1)).setAttribute(any(String.class), any(Null.class));
		verify(httpRequest, times(1)).setAttribute(any(String.class), any(Null.class));
	}
	
}
