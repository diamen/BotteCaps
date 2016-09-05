package com.stobinski.bottlecaps.ejb.managers;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.stobinski.bottlecaps.ejb.common.SqlCacher;
import com.stobinski.bottlecaps.ejb.entities.Countries;
import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

@Stateless
public class CountriesManager {
	
	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private SqlCacher sqlCacher;
	
	public List<CountriesWithAmount> getCountriesWithAmount() {
		return sqlCacher.getCountriesWithAmount();
	}
	
	public String getFlag(String country) {
		return sqlCacher.getCountriesWithAmount().stream().filter(e -> e.getName().equals(country)).findFirst().get().getFlag();
	}
	
	public Long getCountryId(String country) {
		return sqlCacher.getCountriesWithAmount().stream().filter(e -> e.getName().equals(country)).findFirst().get().getId();
	}
	
}
