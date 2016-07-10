package com.stobinski.bottlecaps.ejb.dao;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.stobinski.bottlecaps.ejb.entities.Brands;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.entities.Countries;
import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

public class CapsDaoService {

	@Inject
	private DaoService dao;

	public Caps getCap(String country, Long capId) {
		return getCap(getCountryId(country), capId);
	}
	
	public Caps getCap(Long countryId, Long capId) {
		return (Caps) dao.retrieveSingleData(new QueryBuilder().select().from(Caps.class).where(Caps.COUNTRY_ID_NAME, Caps.ID_NAME).eq(countryId, capId).build());
	}
	
	public List<Caps> getCaps(String country) {
		return getCaps(getCountryId(country));
	}
	
	public List<Caps> getCaps(Long countryId) {
		return dao.retrieveData(new QueryBuilder().select().from(Caps.class).where(Caps.COUNTRY_ID_NAME).eq(countryId).build())
				.stream().map(e -> (Caps) e).collect(Collectors.toList());
	}
	
	public List<Caps> getFilteredCaps(String country, String searchText) {
		return getFilteredCaps(getCountryId(country), searchText);
	}
	
	public List<Caps> getFilteredCaps(Long countryId, String searchText) {
		return dao.retrieveData(new QueryBuilder().select().from(Caps.class).where(Caps.COUNTRY_ID_NAME).eq(countryId).build())
				.stream().map(e -> (Caps) e)	
				.filter(e -> e.getCap_text().toLowerCase().contains(searchText.toLowerCase()))
				.collect(Collectors.toList());
	}

	public long getBrandId(String capbrand) {
		List<Brands> brands = dao.retrieveData(new QueryBuilder().select().from(Brands.class).build())
										.stream().map(e -> (Brands) e).collect(Collectors.toList());
		boolean exists = brands.stream().map(e -> e.getName()).anyMatch(e -> e.equals(capbrand));
	
		if(exists)
			return brands.stream().filter(e -> e.getName().equals(capbrand)).findFirst().map(e -> e.getId()).get().intValue();
		
		Brands brand = new Brands();
		brand.setName(capbrand);
		dao.persist(brand);
		
		return brands.stream().map(e -> e.getId()).mapToLong(e -> e).max().getAsLong() + 1;
	}
	
	public Countries getCountry(String country) {
		return (Countries) dao.retrieveSingleData
				(new QueryBuilder().select().from(Countries.class).where(Countries.NAME_NAME).eq(country).build());
	}
	
	public List<CountriesWithAmount> getCountriesWithAmount() {
		return dao.retrieveData(new QueryBuilder().select().from(Countries.class).build()).stream()
				.map(e -> (Countries) e).map(e -> new CountriesWithAmount(e.getId(), e.getName(), e.getFlag(), count(e.getId())))
				.collect(Collectors.toList());
	}
	
	public long getCountryId(String country) {
		return ((Countries) dao.retrieveSingleData(new QueryBuilder().select().from(Countries.class).where(Countries.NAME_NAME).eq(country).build())).getId();
	}
	
	public void insertCap(String fileName, String captext, Long brandId, Boolean isBeer, Long countryId, String filePath, String ext) {
		Caps caps = new Caps();
		caps.setCountry_id(countryId);
		caps.setBrand_id(brandId);
		caps.setBeer(isBeer ? 1 : 0);
		caps.setAdded_date(new Date());
		caps.setPath(filePath);
		caps.setFile_name(fileName);
		caps.setExtension(ext);
		caps.setCap_text(captext);
		dao.persist(caps);
	}

	public void removeCap(String country, Long capId) {
		removeCap(getCountryId(country), capId);
	}
	
	public void removeCap(Long countryId, Long capId) {
		Caps caps = new Caps();
		caps.setCountry_id(countryId);
		caps.setId(capId);
		dao.remove(caps);
	}
	
	public String getBrand(Long id) {
		return ((Brands) dao.retrieveSingleData(new QueryBuilder().select().from(Brands.class).where(Brands.ID_NAME).eq(id).build())).getName();
	}
	
	private Long count(Long countryId) {
		return (Long) dao.retrieveSingleData(new QueryBuilder().count().from(Caps.class).where(Caps.COUNTRY_ID_NAME).eq(countryId).build());
	}
	
}
