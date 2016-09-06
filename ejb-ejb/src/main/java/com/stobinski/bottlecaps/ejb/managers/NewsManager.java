package com.stobinski.bottlecaps.ejb.managers;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.common.DatabaseCacher;
import com.stobinski.bottlecaps.ejb.entities.News;

@Stateless
public class NewsManager {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private DatabaseCacher dbCacher;
	
	@Inject
	private Logger log;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveNews(String title, String content) {
		News news = new News();
		news.setTitle(title);
		news.setContent(content);
		news.setDate(new Date(new java.util.Date().getTime()));
		
		entityManager.persist(news);
		
		entityManager.flush();
		
		dbCacher.refreshNews();
		
		log.debug(String.format("News with title: %s and content: %s added to database", title, content));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateNews(Integer id, String title, String content) {
		News news = entityManager.find(News.class, id);
		news.setTitle(title);
		news.setContent(content);
		
		entityManager.flush();
		
		dbCacher.refreshNews();
		
		log.debug(String.format("News with title: %s and content: %s updated in database", title, content));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeNews(int newsId) {
		News news = entityManager.find(News.class, newsId);
		entityManager.remove(news);
	
		entityManager.flush();
		
		dbCacher.refreshNews();
		
		log.debug(String.format("News with id: %d removed from database", newsId));
	}
	
	public List<News> getNews() {
		return dbCacher.getNews();
	}
	
	public List<News> getNews(int numberOfNews) {
		int offset = (numberOfNews - 1) * 10;
		
		return dbCacher.getNews().stream().skip(offset).limit(10).collect(Collectors.toList());
	}
	
	public News getSingleNews(int id) {
		return dbCacher.getNews().stream().filter(e -> e.getId() == id).findFirst().get();
	}
	
	public int countNews() {
		return dbCacher.getNews().size();
	}
	
}
