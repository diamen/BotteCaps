package com.stobinski.bottlecaps.ejb.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.stobinski.bottlecaps.ejb.dao.DaoService;

//@RunWith(MockitoJUnitRunner.class)
public class ImageFileHandlerTest {

	@InjectMocks
	private ImageFileHandler fileHandler;
	
	@Mock
	private DaoService daoService;

	// TODO Do porpawki
	
//	@Test
//	public void shouldReturnProperFileNameNumber() {
//		// given
//		int fileName = 13;
//		int expectedValue = fileName + 1;
//		
//		// when
//		int returnedValue = fileHandler.getNewFileNameNumber(fileName);
//		
//		// then
//		assertThat(returnedValue).isEqualTo(expectedValue);
//	}
//	
//	@Test
//	public void shouldGenerateProperName() {
//		// given
//		int fileName = 13;
//		String country = "ALBANIA";
//		String exceptedValue = ImageFileHandler.PATH + File.separatorChar + country;
//		
//		// when
//		String returnedValue = fileHandler.generateFilePath(fileName, country);
//		
//		// then
//		assertThat(returnedValue).isEqualTo(exceptedValue);
//	}
//	
//	@Test
//	public void shouldGenerateFullFilePath() {
//		// given
//		int fileName = 13;
//		String country = "ALBANIA";
//		String expectedValue = ImageFileHandler.PATH + File.separatorChar + country + File.separatorChar + fileName + "." + ImageFileHandler.EXT;
//
//		// when
//		String returnedValue = fileHandler.generateFullFilePath(fileName, country);
//		
//		// then
//		assertThat(returnedValue).isEqualTo(expectedValue);
//	}
	
}
