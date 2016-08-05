package com.stobinski.bottlecaps.ejb.common;

import java.sql.Date;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.entities.News;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NewsManager {

	@Inject
	private Logger log;
	
	@Inject
	private DaoService dao;
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveNews(String title, String content) {
		News news = new News();
		news.setTitle(title);
		news.setContent(content);
		news.setDate(new Date(new java.util.Date().getTime()));
		
		dao.persist(news);
		
		log.debug("News added to database");
	}
	
}
