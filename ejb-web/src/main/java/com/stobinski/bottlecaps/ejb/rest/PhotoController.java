package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.entities.Countries;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Cap;
import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

@Path("/photo/")
public class PhotoController {
	
	@Inject
	private ImageManager imageManager;
	
	@Inject
	private DaoService dao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("countries")
	public List<CountriesWithAmount> getCountries() {
		return dao.retrieveData(new QueryBuilder().select().from(Countries.class).build()).stream()
				.map(e -> (Countries) e).map(e -> new CountriesWithAmount(e.getId(), e.getName(), e.getFlag(), count(e.getId())))
				.collect(Collectors.toList());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bycountry/{country}")
	public List<Base64Cap> photosByCountry(@PathParam("country") String country) {
		return imageManager.loadFiles(country);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("idbycountry")
	public Long idByCountry(String country) {
		return ((Countries) dao.retrieveSingleData
				(new QueryBuilder().select().from(Countries.class).where(Countries.NAME_NAME).eq(country).build())).getId();
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("singlecap")
	public Base64Cap getSingleCap(@QueryParam("country") String country, @QueryParam("id") Long id) {
		return imageManager.loadFile(country, id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("filtercap")
	public List<Caps> getFilteredCaps(@QueryParam("searchText") String searchText) {
		return dao.retrieveData(new QueryBuilder().select().from(Caps.class).where(Caps.CAP_TEXT_NAME).like(searchText).build())
				.stream().map(e -> (Caps) e).collect(Collectors.toList());
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("flag")
	public Countries getFlag(@QueryParam("countryName") String countryName) {
		return (Countries) dao.retrieveSingleData
				(new QueryBuilder().select().from(Countries.class).where(Countries.NAME_NAME).eq(countryName).build());
	}
	
	private Long count(Long countryId) {
		return (Long) dao.retrieveSingleData(new QueryBuilder().count().from(Caps.class).where(Caps.COUNTRY_ID_NAME).eq(countryId).build());
	}
}
