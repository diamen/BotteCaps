package com.stobinski.bottlecaps.ejb.managers;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.stobinski.bottlecaps.ejb.entities.Countries;
import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

@Stateless
public class CountriesManager {
	
	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	public List<CountriesWithAmount> getCountriesWithAmount() {
		return entityManager.createNamedQuery("Countries.findCountries", Countries.class).getResultList()
				.stream()
				.map(e -> new CountriesWithAmount(e.getId(), e.getName(), e.getFlag(), count(e.getId())))
				.collect(Collectors.toList());
	}
	
	public String getFlag(String country) {
		return entityManager.createNamedQuery("Countries.findFlagByName", String.class).setParameter("country", country).getSingleResult();
	}
	
	public Long getCountryId(String country) {
		return entityManager.createNamedQuery("Countries.findIdByName", Long.class).setParameter("country", country).getSingleResult();
	}
	
	private Long count(Long countryId) {
		return entityManager.createNamedQuery("Caps.countCapsByCountryId", Long.class)
				.setParameter("country_id", countryId).getSingleResult();
	}
	
}
