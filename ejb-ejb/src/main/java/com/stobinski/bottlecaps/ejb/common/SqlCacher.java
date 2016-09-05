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

import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

@Startup
@Singleton
public class SqlCacher {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	private Logger log = Logger.getLogger(getClass());
	
	private List<CountriesWithAmount> countriesWithAmount; 
	
	@PostConstruct
	private void init() {
		refresh();
	}
	
	@Schedule(minute="30", hour="22")
	private void refresh() {
		this.countriesWithAmount = refreshCountriesWithAmount();
		
		log.debug("Refresh SQL data executed");
		log.debug(String.format("Countries with amount: %s", 
				countriesWithAmount.stream().map(Object::toString).collect(Collectors.joining(", "))));
	}

	public List<CountriesWithAmount> getCountriesWithAmount() {
		return Collections.unmodifiableList(countriesWithAmount);
	}
	
	public List<CountriesWithAmount> refreshCountriesWithAmount() {
		List<Object[]> list = entityManager.createNamedQuery("Caps.countCapsGroupByCountryId", Object[].class).getResultList();
		List<CountriesWithAmount> countries = new ArrayList<>();

		for(Object[] elem : list)
			countries.add(new CountriesWithAmount((Long) elem[0], (String) elem[1], (String) elem[2], (Long) elem[3]));
		
		return countries;
	}
	
}