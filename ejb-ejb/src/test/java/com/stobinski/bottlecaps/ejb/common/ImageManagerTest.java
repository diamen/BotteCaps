package com.stobinski.bottlecaps.ejb.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.StringQuery;
import com.stobinski.bottlecaps.ejb.entities.Brands;
import com.stobinski.bottlecaps.ejb.entities.Countries;

@RunWith(MockitoJUnitRunner.class)
public class ImageManagerTest {

	@InjectMocks
	private ImageManager imageManager;
	
	@Mock
	private DaoService dao;
	
	@Test
	public void shouldReturnProperCountryId() {
		// given
		long expectedId = 12;
		Countries countries = new Countries();
		countries.setId(expectedId);
		given(dao.retrieveSingleData(any(StringQuery.class))).willReturn(countries);
		
		// when
		long returnedId = imageManager.getCountryId("Albania");

		// then
		assertThat(returnedId).isEqualTo(expectedId);
	}
	
	@Test
	public void shouldAddNewBrandIfBrandIsNotPresentInDb() {
		// given
		Brands brands = new Brands();
		brands.setName("test");
		List<Serializable> list = Arrays.asList(brands);
		given(dao.retrieveData(any(StringQuery.class))).willReturn(list);
		
		// when
		imageManager.getBrandId("different");
		
		// then
		verify(dao, times(1)).persist(any(Object.class));
	}
	
	@Test
	public void shouldReturnBrandIdWhenBrandIsPresentInDb() {
		// given
		long expectedId = 5;
		Brands brands = new Brands();
		brands.setId(expectedId);
		brands.setName("test");
		List<Serializable> list = Arrays.asList(brands);
		given(dao.retrieveData(any(StringQuery.class))).willReturn(list);
		
		// when
		long returnedId = imageManager.getBrandId("test");
		
		// then
		verify(dao, times(0)).persist(any(Object.class));
		assertThat(returnedId).isEqualTo(expectedId);
	}
	
}
