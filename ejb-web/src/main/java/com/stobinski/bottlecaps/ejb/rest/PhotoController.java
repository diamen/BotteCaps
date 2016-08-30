package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.dao.CapsDaoService;
import com.stobinski.bottlecaps.ejb.entities.Countries;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Cap;
import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

@Path("/photo/")
public class PhotoController {
	
	@Inject
	private ImageManager imageManager;
	
	@Inject
	private CapsDaoService dao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bycountry/{country}")
	public List<Base64Cap> photosByCountry(@PathParam("country") String country) {
		return imageManager.loadCapFiles(dao.getCaps(country));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("singlecap")
	public Base64Cap getSingleCap(@QueryParam("country") String country, @QueryParam("id") Long id) {
		return imageManager.loadCapFile(dao.getCap(country, id));
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("brand")
	public String getBrandById(@QueryParam("id") Long id) {
		return dao.getBrand(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("filtercap")
	public List<Base64Cap> getFilteredCaps(@QueryParam("country") String country, @QueryParam("searchText") String searchText) {
		return imageManager.loadCapFiles(dao.getFilteredCaps(country, searchText));
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("flag")
	public Countries getFlag(@QueryParam("country") String country) {
		return dao.getCountry(country);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("idbycountry")
	public Long idByCountry(String country) {
		return dao.getCountryId(country);
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("newest")
	public List<Base64Cap> getNewestCaps(@QueryParam("limit") Integer limit) {
		return imageManager.loadCapFiles(dao.getNewestCaps(limit));
	}
	
}
