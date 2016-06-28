package com.stobinski.bottlecaps.ejb.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.stobinski.bottlecaps.ejb.dao.DaoService;

@RunWith(MockitoJUnitRunner.class)
public class ImageManagerTest {
	
	@InjectMocks
	private ImageManager imageManager;
	
	@Mock
	private DaoService daoService;
	
	@Test
	public void shouldReturnProperFileNameNumber() {
		// given
		int fileName = 13;
		int expectedValue = fileName + 1;
		
		// when
		int returnedValue = imageManager.getNewFileNameNumber(fileName);
		
		// then
		assertThat(returnedValue).isEqualTo(expectedValue);
	}
	
	@Test
	public void shouldGenerateProperName() {
		// given
		int fileName = 13;
		String country = "ALBANIA";
		String exceptedValue = ImageManager.PATH + File.separatorChar + country;
		
		// when
		String returnedValue = imageManager.generateFilePath(fileName, country);
		
		// then
		assertThat(returnedValue).isEqualTo(exceptedValue);
	}
	
}
