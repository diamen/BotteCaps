package com.stobinski.bottlecaps.ejb.dao;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
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
import com.stobinski.bottlecaps.ejb.common.FileHelper;
import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.entities.EConfigKeys;
import com.stobinski.bottlecaps.ejb.entities.MiniTradeCaps;
import com.stobinski.bottlecaps.ejb.entities.TradeCaps;
import com.stobinski.bottlecaps.ejb.wrappers.Base64TradeCap;

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
	
	public List<Base64TradeCap> getTradeCaps() {
		return findCaps()
				.stream()
				.map(e -> (TradeCaps) e)
				.map(e -> new Base64TradeCap(e, Base64Service.fromByteArrayToBase64(imageManager.retrieveImage(e.getPath(), e.getFile_name()))))
				.collect(Collectors.toList());
	}
	
	private boolean isAlreadyInDatabase(String filename) {	
		return !dao.retrieveData(entityManager,
				new QueryBuilder().select().from(TradeCaps.class).where(TradeCaps.FILE_NAME_NAME).eq(filename.split("\\.")[0]).build())
				.isEmpty();
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
	
	@SuppressWarnings("unchecked")
	private List<Serializable> findCaps() {
		return entityManager.createQuery(new QueryBuilder().select().from(TradeCaps.class).build().toString())
				.getResultList();
	}
	
}
