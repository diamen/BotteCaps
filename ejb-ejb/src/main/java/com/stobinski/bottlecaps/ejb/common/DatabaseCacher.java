package com.stobinski.bottlecaps.ejb.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.entities.News;
import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

@Startup
@Singleton
public class DatabaseCacher {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	private Logger log = Logger.getLogger(getClass());
	
	private List<CountriesWithAmount> countriesWithAmount; 
	private List<News> news;
	
	@PostConstruct
	private void init() {
		refresh();
	}
	
	@Schedule(minute="30", hour="22")
	private void refresh() {
		refreshCountriesWithAmount();
		refreshNews();
		
		log.debug("Refresh SQL data executed");
		log.debug(String.format("Countries with amount after refresh: %s", 
				countriesWithAmount.stream().map(Object::toString).collect(Collectors.joining(", "))));
		log.debug(String.format("News after refresh: %s", 
				news.stream().map(Object::toString).collect(Collectors.joining(", "))));
	}

	public List<CountriesWithAmount> getCountriesWithAmount() {
		return Collections.unmodifiableList(countriesWithAmount);
	}
	
	public List<News> getNews() {
		return Collections.unmodifiableList(news);
	}
	
	public void refreshCountriesWithAmount() {
		List<Object[]> list = entityManager.createNamedQuery("Caps.countCapsGroupByCountryId", Object[].class).getResultList();
		List<CountriesWithAmount> countries = new ArrayList<>();

		for(Object[] elem : list)
			countries.add(new CountriesWithAmount((Long) elem[0], (String) elem[1], (String) elem[2], (Long) elem[3]));
		
		this.countriesWithAmount = countries;
	}
	
	public void refreshNews() {
		this.news = entityManager.createNamedQuery("News.findNews", News.class).getResultList();
	}
	
}