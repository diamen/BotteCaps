package com.stobinski.bottlecaps.ejb.managers;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.common.Base64Service;
import com.stobinski.bottlecaps.ejb.common.ConfigurationBean;
import com.stobinski.bottlecaps.ejb.common.EConfigKeys;
import com.stobinski.bottlecaps.ejb.common.FileHelper;
import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.dao.exceptions.QueryBuilderException;
import com.stobinski.bottlecaps.ejb.entities.MiniTradeCaps;
import com.stobinski.bottlecaps.ejb.entities.TradeCaps;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Entity;

@Stateless
public class TradeCapsManager {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private DaoService dao;
	
	@Inject
	private ConfigurationBean config;
	
	@Inject
	private ImageManager imageManager;
	
	@Inject
	private Logger log;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveFile(String base64, String filename) throws FileAlreadyExistsException {
		String fullPath = FileHelper.getFullPath(
				FileHelper.joinPath(config.getValue(EConfigKeys.PATH.toString()), config.getValue(EConfigKeys.TRADE.toString())), 
				filename);
		
		String fullPathMini = FileHelper.getFullPath(
				FileHelper.joinPath(config.getValue(EConfigKeys.PATH.toString()), config.getValue(EConfigKeys.MINI.toString())), 
				filename);
		
		if(isAlreadyInDatabase(filename))
			throw new FileAlreadyExistsException(filename);
		
		persistEntity(filename);
		
		try {
			imageManager.saveFile(Base64Service.fromBase64JsonToByteArray(base64), fullPath);
			imageManager.saveFile(imageManager.miniaturizeImage(Base64Service.fromBase64JsonToByteArray(base64)), fullPathMini);
		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteFiles(Set<Long> ids) {
		Set<Object> set = (Set<Object>)(Set<?>) ids;
		
		try {
			dao.remove(entityManager,
					new QueryBuilder().delete().from(MiniTradeCaps.class).where(MiniTradeCaps.ID_NAME).in(set).build()
					);

		} catch (QueryBuilderException e) {
			log.error(e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Base64Entity getTradeCap(Long id) {
		TradeCaps cap = entityManager.createNamedQuery("TradeCaps.findTradeCapById", TradeCaps.class).setParameter("id", id).getSingleResult();
		return new Base64Entity(cap, Base64Service.fromByteArrayToBase64(imageManager.retrieveImage(cap.getPath(), cap.getFile_name())));
	}
	
	public List<Base64Entity> getMiniTradeCaps() {
		return findMiniCaps()
				.stream()
				.map(e -> new Base64Entity(e, Base64Service.fromByteArrayToBase64(imageManager.retrieveImage(e.getPath(), e.getFile_name()))))
				.collect(Collectors.toList());
	}
	
	protected List<MiniTradeCaps> findMiniCaps() {
		return entityManager.createNamedQuery("MiniTradeCaps.findTradeCaps", MiniTradeCaps.class).getResultList();
	}
	
	private boolean isAlreadyInDatabase(String filename) {	
		try {
			return !dao.retrieveData(entityManager,
					new QueryBuilder().select().from(TradeCaps.class).where(TradeCaps.FILE_NAME_NAME).eq(filename.split("\\.")[0]).build())
					.isEmpty();
		} catch (QueryBuilderException e) {
			log.error(e);
			return false;
		}
	}
	
	private void persistEntity(String filename) {
		
		String name = filename.split("\\.")[0];
		String ext = filename.split("\\.")[1];
		
		MiniTradeCaps mini = new MiniTradeCaps();
		mini.setAdded_date(Date.valueOf(LocalDate.now()));
		mini.setExtension(ext);
		mini.setFile_name(name);
		mini.setPath(FileHelper.joinPath(config.getValue(EConfigKeys.PATH.toString()), config.getValue(EConfigKeys.MINI.toString())));
		
		TradeCaps trade = new TradeCaps();
		trade.setAdded_date(Date.valueOf(LocalDate.now()));
		trade.setExtension(ext);
		trade.setFile_name(name);
		trade.setPath(FileHelper.joinPath(config.getValue(EConfigKeys.PATH.toString()), config.getValue(EConfigKeys.TRADE.toString())));	
		trade.setMini_trade_caps(mini);
		mini.setTrade_caps(trade);
		
		entityManager.persist(mini);
	}

}
