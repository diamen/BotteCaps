package com.stobinski.bottlecaps.ejb.common;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
class ImageFileHandler {

	private String path;
	private String ext;
	
	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@EJB
	private ConfigurationBean configurationBean;
	
	@PostConstruct
	public void init() {
		this.path = configurationBean.getValue(EConfigKeys.PATH.toString()) + File.separatorChar + configurationBean.getValue(EConfigKeys.COLL.toString());
		this.ext = configurationBean.getValue(EConfigKeys.EXT.toString());
	}
	
	protected Long getLastFileNameNumber() {
		return entityManager.createNamedQuery("Caps.findMaxId", Long.class).getSingleResult();
	}
	
	protected Long getNewFileNameNumber(Long oldFileName) {
		return oldFileName + 1;
	}
	
	protected String generateFilePath(String country) {
		return this.path + File.separatorChar + country;
	}

	protected String generateFullFilePath(String filePath, Long fileNameSequence) {
		return filePath + File.separatorChar + fileNameSequence + "." + this.ext;
	}
	
}
