package com.stobinski.bottlecaps.ejb.managers;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.stobinski.bottlecaps.ejb.common.Base64Service;
import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Cap;

@Stateless
public class CollectManager {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private ImageManager imageManager;
	
	@Inject
	private CountriesManager countriesManager;
	
	public List<Base64Cap> getCaps(String country) {
		return entityManager.createNamedQuery("Caps.findCapsByCountryId", Caps.class).setParameter("country_id", countriesManager.getCountryId(country)).getResultList()
			.stream()
			.map(e -> new Base64Cap(e, capToBase64(e)))
			.collect(Collectors.toList());
	}
	
	public List<Base64Cap> getCaps(String country, String searchText) {
		return entityManager.createNamedQuery("Caps.findCapsByCountryIdAndCapText", Caps.class)
				.setParameter("cap_text", searchText).setParameter("country_id", countriesManager.getCountryId(country)).getResultList()
				.stream()
				.map(e -> new Base64Cap(e, capToBase64(e)))
				.collect(Collectors.toList());		
	}
	
	public List<Base64Cap> getCaps(Integer limit) {
		return entityManager.createNamedQuery("Caps.findCaps", Caps.class).setFirstResult(0).setMaxResults(limit).getResultList()
				.stream()
				.map(e -> new Base64Cap(e, capToBase64(e)))
				.collect(Collectors.toList());
	}
	
	public Base64Cap getCap(String country, Long id) {
		Caps cap = entityManager.createNamedQuery("Caps.findCapByIdAndCountryId", Caps.class)
				.setParameter("country_id", countriesManager.getCountryId(country)).setParameter("id", id).getSingleResult();
		return new Base64Cap(cap, capToBase64(cap));
	}
	
	public String getBrand(Long id) {
		return entityManager.createNamedQuery("Brands.findById", String.class).setParameter("id", id).getSingleResult();
	}
	

	
	private String capToBase64(Caps cap) {
		return Base64Service.fromByteArrayToBase64(imageManager.retrieveImage(cap.getPath(), cap.getFile_name()));
	}
	
}
