package com.stobinski.bottlecaps.ejb.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ISessionCache {
	void attachCacheToSession(HttpSession httpSession);
	boolean match(String value, HttpSession httpSession);
	void updateCachedValue(HttpServletRequest httpReq, String value);
	void clearCache(HttpServletRequest httpReq);
}
