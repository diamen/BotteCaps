package com.stobinski.bottlecaps.ejb.common;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.dao.exceptions.QueryBuilderException;
import com.stobinski.bottlecaps.ejb.entities.Configuration;

@Startup
@Singleton
public class ConfigurationBean {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	private Map<String, String> items;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		try {
			this.items = ((List<Configuration>) entityManager.createQuery(new QueryBuilder().select().from(Configuration.class).build().toString()).getResultList())
			.stream().collect(Collectors.toMap(Configuration::getKeyy, Configuration::getValue));
		} catch (QueryBuilderException e) {
			Logger log = LoggerFactory.create(getClass());
			log.error(e);
		}
	}
	
	public String getValue(String key) {
		return items.get(key);
	}
	
}
