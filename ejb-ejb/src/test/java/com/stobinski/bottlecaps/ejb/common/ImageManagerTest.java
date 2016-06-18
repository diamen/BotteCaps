package com.stobinski.bottlecaps.ejb.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.io.File;
import java.util.Base64;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.entities.Caps;

@RunWith(MockitoJUnitRunner.class)
public class ImageManagerTest {
	
	@InjectMocks
	private ImageManager imageManager;
	
	@Mock
	private DaoService daoService;
	
	@Test
	public void shouldConvertBase64ToByteArray() {
		// given
		String base64 = "{\"baseimage\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6CAYAAACI7Fo9AAAgAEl\"}";
		byte[] expected = Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6CAYAAACI7Fo9AAAgAEl");
		
		// when
		byte[] actual = imageManager.base64ToByteArray(base64);
		
		// then
		assertThat(actual).isEqualTo(expected);
	}
	
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
		String exceptedValue = ImageManager.PATH + File.separatorChar + fileName  + "." + ImageManager.EXT;
		
		// when
		String returnedValue = imageManager.generateFilePath(fileName);
		
		// then
		assertThat(returnedValue).isEqualTo(exceptedValue);
	}
	
	
	@Test
	public void shouldIncreaseNumberOfEntriesInDatabase() {
		// given
		int dbIndex = 13;
		int exceptedDbIndex = 14;
		SoftAssertions soft = new SoftAssertions();
		given(daoService.getLastFileName()).willReturn(dbIndex, exceptedDbIndex);
		
		// when
		int returnedBefore = daoService.getLastFileName();
		daoService.persist(any(Caps.class));
		int returnedAfter = daoService.getLastFileName();
		
		// then
		soft.assertThat(returnedBefore).isEqualTo(dbIndex);
		soft.assertThat(returnedAfter).isEqualTo(exceptedDbIndex);
		soft.assertAll();
	}
	
}
