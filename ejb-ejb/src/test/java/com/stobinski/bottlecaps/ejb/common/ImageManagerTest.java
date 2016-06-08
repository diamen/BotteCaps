package com.stobinski.bottlecaps.ejb.common;

import static org.junit.Assert.assertEquals;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageManagerTest {
	
	@InjectMocks
	private ImageManager imageManager;
	
	@Mock
	private DaoService daoService;
	
	@Test
	public void shouldGenerateProperName() {
		// given
		Integer fileName = 13;
		String exceptedValue = ImageManager.PATH + "\\" + fileName  + "." + ImageManager.EXT;
		
		// when
		String returnedValue = imageManager.generateFileNamePath(fileName);
		
		// then
		assertEquals(exceptedValue, returnedValue);
	}
	
	@Test
	public void shouldConvertBase64ToByteArray() {
		// given
		String base64 = "{\"baseimage\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6CAYAAACI7Fo9AAAgAEl\"}";
		byte[] expected = Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6CAYAAACI7Fo9AAAgAEl");
		
		// when
		byte[] actual = imageManager.base64ToByteArray(base64);
		
		// then
		org.junit.Assert.assertArrayEquals(expected, actual);
	}
	
}
