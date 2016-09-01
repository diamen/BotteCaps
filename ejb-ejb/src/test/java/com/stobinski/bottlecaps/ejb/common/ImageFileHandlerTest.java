package com.stobinski.bottlecaps.ejb.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageFileHandlerTest {

	@Mock
	private ConfigurationBean configBean;
	
	@InjectMocks
	private ImageFileHandler fileHandler;

	private String country = "Albania";
	
	@Before
	public void init() {
		when(configBean.getValue(any())).thenReturn("test", "test", "JPG");
		fileHandler.init();
	}
	
	@Test
	public void shouldGenerateProperFilePath() {
		// given
		String expectedPath = "test" + File.separatorChar + "test" + File.separatorChar + country;
		// when
		String filePath = fileHandler.generateFilePath(country);
		// then
		assertThat(filePath).isEqualTo(expectedPath);
	}
	
	@Test
	public void shouldGenerateProperFullFilePath() {
		// given
		Long fileNameSequence = 13l;
		String expectedPath = "test" + File.separatorChar + "test" + File.separatorChar + country + File.separatorChar + fileNameSequence + ".JPG";
		// when
		String fullFilePath = fileHandler.generateFullFilePath(fileHandler.generateFilePath(country), fileNameSequence);
		// then
		assertThat(fullFilePath).isEqualTo(expectedPath);
	}
	
}
