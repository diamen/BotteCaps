package com.stobinski.bottlecaps.ejb.managers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.common.Base64Service;
import com.stobinski.bottlecaps.ejb.common.DatabaseCacher;
import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.dao.exceptions.QueryBuilderException;
import com.stobinski.bottlecaps.ejb.entities.Brands;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Entity;

@Stateless
public class CollectManager {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private DaoService dao;
	
	@Inject
	private ImageManager imageManager;
	
	@Inject
	private CountriesManager countriesManager;
	
	@Inject
	private DatabaseCacher dbCacher;
	
	@Inject
	private Logger log;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveCap(byte[] b, String captext, String capbrand, Boolean isBeer, String country) throws IOException {
		
		Long fileNameSequence = imageManager.generateFileNameSequence();
		String filePath = imageManager.generateFilePath(country);
		imageManager.saveImage(b, fileNameSequence, filePath);
		
		persistCap(captext, String.valueOf(fileNameSequence), filePath, imageManager.getExt(), getBrandId(capbrand), countriesManager.getCountryId(country), isBeer);
		
		entityManager.flush();
		
		dbCacher.refreshCountriesWithAmount();
		dbCacher.refreshNewestCaps();
		
		log.debug(String.format("File %d saved in database", fileNameSequence));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCap(Long id, String captext, String country, String capbrand, Integer beer) {
		Long countryId = countriesManager.getCountryId(country);
		Long capbrandId = getBrandId(capbrand);
		try {
			dao.update(entityManager,
					new QueryBuilder().update(Caps.class).set(Caps.COUNTRY_ID_NAME, Caps.CAP_TEXT_NAME, Caps.BRAND_ID_NAME, Caps.BEER_NAME)
					.eq(countryId, captext, capbrandId, beer)
					.where(Caps.ID_NAME).eq(id)
					.build());
		} catch (QueryBuilderException e) {
			log.error(e);
		}
		
		log.debug(String.format("File with id=%d updated", id));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeCap(Long capId) {
		Caps cap = entityManager.find(Caps.class, capId);
		entityManager.remove(cap);
		
		entityManager.flush();
		
		dbCacher.refreshCountriesWithAmount();
		dbCacher.refreshNewestCaps();
		
		log.debug(String.format("File with id=%d removed from database", capId));
	}
	
	public List<Base64Entity> getCaps(String country) {
		return entityManager.createNamedQuery("Caps.findCapsByCountryId", Caps.class).setParameter("country_id", countriesManager.getCountryId(country)).getResultList()
			.stream()
			.map(e -> new Base64Entity(e, capToBase64(e)))
			.collect(Collectors.toList());
	}
	
	public List<Base64Entity> getCaps(String country, String searchText) {
		return entityManager.createNamedQuery("Caps.findCapsByCountryIdAndCapText", Caps.class)
				.setParameter("cap_text", searchText).setParameter("country_id", countriesManager.getCountryId(country)).getResultList()
				.stream()
				.map(e -> new Base64Entity(e, capToBase64(e)))
				.collect(Collectors.toList());		
	}
	
	public List<Base64Entity> getNewestCaps() {
		return dbCacher.getNewestCaps()
				.stream()
				.map(e -> new Base64Entity(e, capToBase64(e)))
				.collect(Collectors.toList());
	}
	
	public Base64Entity getCap(String country, Long id) {
		Caps cap = entityManager.createNamedQuery("Caps.findCapByIdAndCountryId", Caps.class)
				.setParameter("country_id", countriesManager.getCountryId(country)).setParameter("id", id).getSingleResult();
		return new Base64Entity(cap, capToBase64(cap));
	}
	
	public String getBrand(Long id) {
		return entityManager.createNamedQuery("Brands.findById", String.class).setParameter("id", id).getSingleResult();
	}
	
	public Long getBrandId(String brandName) {
		try {
			return entityManager.createNamedQuery("Brands.findByName", Long.class).setParameter("name", brandName).getSingleResult();
		} catch(NoResultException e) {
			log.debug(e);
			
			Brands brand = new Brands();
			brand.setName(brandName);
			entityManager.persist(brand);
			
			entityManager.flush();
			
			return brand.getId();
		}
	}
	
	private void persistCap(String captext, String fileName, String filePath, String ext, Long brandId, Long countryId, boolean isBeer) {
		Caps caps = new Caps();
		caps.setCountry_id(countryId);
		caps.setBrand_id(brandId);
		caps.setBeer(isBeer ? 1 : 0);
		caps.setAdded_date(new Date());
		caps.setPath(filePath);
		caps.setFile_name(fileName);
		caps.setExtension(ext);
		caps.setCap_text(captext);
		entityManager.persist(caps);
	}
	
	private String capToBase64(Caps cap) {
		return Base64Service.fromByteArrayToBase64(imageManager.retrieveImage(cap.getPath(), cap.getFile_name()));
	}
	
}
