package com.stobinski.bottlecaps.ejb.security;

import java.security.SecureRandom;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {
	public static String generate() { return RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom()); }
}
