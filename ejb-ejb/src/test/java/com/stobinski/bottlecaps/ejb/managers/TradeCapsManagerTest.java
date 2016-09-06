package com.stobinski.bottlecaps.ejb.managers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.stobinski.bottlecaps.ejb.common.ConfigurationBean;
import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.StringQuery;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.entities.MiniTradeCaps;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Entity;

@RunWith(MockitoJUnitRunner.class)
public class TradeCapsManagerTest {

	@Mock
	private EntityManager entityManager;
	
	@Mock
	private DaoService dao;
	
	@Mock
	private ConfigurationBean configurationBean;
	
	@Mock
	private ImageManager imageManager;
	
	@Mock
	private Logger log;
	
	@InjectMocks
	private TradeCapsManager trade;
	
	private TradeCapsManager spyTrade;
	
	@Test
	public void shouldInvokeDaoRemoveWhenDeleteInvoked() {
		// given
		Set<Long> ids = new HashSet<>();
		ids.add(1l);
		// when
		trade.deleteFiles(ids);
		// then
		verify(dao, times(1)).remove(any(EntityManager.class), any(StringQuery.class));
	}
	
	@Test
	public void shouldMapMiniTradeCapListToBase64MiniTradeCapListCorrectly() {
		// given
		spyTrade = spy(trade);
		List<MiniTradeCaps> miniTestList = Arrays.asList(new MiniTradeCaps(), new MiniTradeCaps(), new MiniTradeCaps());
		// when
		doReturn(miniTestList).when(spyTrade).findMiniCaps();
		when(imageManager.retrieveImage(any(), any())).thenReturn("test".getBytes());
		List<Base64Entity> returnedList = spyTrade.getMiniTradeCaps(); 
		
		// then
		assertThat(returnedList.size()).isEqualTo(3);
		verify(imageManager, times(3)).retrieveImage(any(), any());
	}
	
	@Test
	public void shouldThrowExceptionWhenFileIsAlreadyInDatabase() {
		// given
		List<Serializable> list = new ArrayList<>();
		list.add(new Caps());
		// when
		when(dao.retrieveData(any(EntityManager.class), any(StringQuery.class))).thenReturn(list);
		// then
		assertThatThrownBy(() -> { trade.saveFile("test", "test"); }).isInstanceOf(FileAlreadyExistsException.class);
	}
	
}
