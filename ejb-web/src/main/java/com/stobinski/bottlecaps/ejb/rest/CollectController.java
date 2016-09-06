package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.managers.CollectManager;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Entity;

@Path("/collect/")
public class CollectController {

	@Inject
	private CollectManager collectManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("caps/{country}")
	public List<Base64Entity> getCaps(@PathParam("country") String country) {
		return collectManager.getCaps(country);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("caps/{country}/{id}")
	public Base64Entity getCap(@PathParam("country") String country, @PathParam("id") Long id) {
		return collectManager.getCap(country, id);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("brand/{id}")
	public String getBrand(@PathParam("id") Long id) {
		return collectManager.getBrand(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("filtercaps/{country}")
	public List<Base64Entity> getFilteredCaps(@PathParam("country") String country, @QueryParam("searchText") String searchText) {
		return collectManager.getCaps(country, searchText);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("newest")
	public List<Base64Entity> getNewestCaps(@QueryParam("limit") Integer limit) {
		return collectManager.getCaps(limit);
	}
	
}
