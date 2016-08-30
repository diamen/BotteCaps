package com.stobinski.bottlecaps.ejb.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ISessionCache {
	void init(HttpServletRequest httpServletRequest);
	void attachCacheToSession();
	boolean match(String value, HttpSession httpSession);
	void updateCachedValue(String value);
	void clearCache(HttpServletRequest httpReq);
}
