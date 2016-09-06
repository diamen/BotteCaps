package com.stobinski.bottlecaps.ejb.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.stobinski.bottlecaps.ejb.common.DatabaseCacher;
import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

@Stateless
public class CountriesManager {
	
	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private DatabaseCacher dbCacher;
	
	public List<CountriesWithAmount> getCountriesWithAmount() {
		return dbCacher.getCountriesWithAmount();
	}
	
	public String getFlag(String country) {
		return dbCacher.getCountriesWithAmount().stream().filter(e -> e.getName().equals(country)).findFirst().get().getFlag();
	}
	
	public Long getCountryId(String country) {
		return dbCacher.getCountriesWithAmount().stream().filter(e -> e.getName().equals(country)).findFirst().get().getId();
	}
	
}
