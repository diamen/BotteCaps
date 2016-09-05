package com.stobinski.bottlecaps.ejb.managers;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.entities.News;

@Stateless
public class NewsManager {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private Logger log;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveNews(String title, String content) {
		News news = new News();
		news.setTitle(title);
		news.setContent(content);
		news.setDate(new Date(new java.util.Date().getTime()));
		
		entityManager.persist(news);
		
		log.debug(String.format("News with title: %s and content: %s added to database", title, content));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateNews(Integer id, String title, String content) {
		News news = entityManager.find(News.class, id);
		news.setTitle(title);
		news.setContent(content);
	}
	
	public List<News> getNews() {
		return entityManager.createNamedQuery("News.findNews", News.class).getResultList();
	}
	
	public List<News> getNews(int numberOfNews) {
		int offset = (numberOfNews - 1) * 10;
		
		return entityManager.createNamedQuery("News.findNews", News.class)
				.setFirstResult(offset).setMaxResults(10).getResultList();
	}
	
	public News getSingleNews(int id) {
		return entityManager.createNamedQuery("News.findNewsById", News.class)
				.setParameter("id", id).getSingleResult();
	}
	
	public Long countNews() {
		return entityManager.createNamedQuery("News.countNews", Long.class).getSingleResult();
	}
	
}
