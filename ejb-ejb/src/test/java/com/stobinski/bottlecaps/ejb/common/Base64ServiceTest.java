package com.stobinski.bottlecaps.ejb.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;

import org.junit.Test;

public class Base64ServiceTest {
	
	@Test
	public void shouldConvertBase64JsonToBase64String() {
		String base64Json = "{\"baseimage\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6CAYAAACI7Fo9AAAgAEl\"}";
		String expected = "iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6CAYAAACI7Fo9AAAgAEl";
		
		// when
		String actual = Base64Service.fromJsonBase64ToBase64(base64Json);
		
		// then
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void shouldConvertBase64JsonToByteArray() {
		// given
		String base64Json = "{\"baseimage\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6CAYAAACI7Fo9AAAgAEl\"}";
		byte[] expected = Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6CAYAAACI7Fo9AAAgAEl");
		
		// when
		byte[] actual = Base64Service.fromBase64JsonToByteArray(base64Json);
		
		// then
		assertThat(actual).isEqualTo(expected);
	}
	
}
