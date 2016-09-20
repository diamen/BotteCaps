package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stobinski.bottlecaps.ejb.managers.CollectManager;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Entity;

@Path("/collect/")
public class CollectController {

	@Inject
	private CollectManager collectManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("cap/{country}/{id}")
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
	@Path("caps/{country}/{beer: \\d+}")
	public List<Base64Entity> getCaps(@PathParam("country") String country, @PathParam("beer") Integer beer, 
			@QueryParam("page") Integer page, @QueryParam("max") Integer max, @QueryParam("searchText") String searchText) {
		
		return (searchText == null && beer == 2) ? collectManager.getCaps(country, page, max) :
				(searchText == null && beer != 2) ? collectManager.getCaps(country, beer, page, max) :
				(searchText != null && beer == 2) ? collectManager.getCaps(country, searchText, page, max) :
				collectManager.getCaps(country, searchText, beer, page, max);
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("newest")
	public List<Base64Entity> getNewestCaps() {
		return collectManager.getNewestCaps();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("progress")
	public Response getCapsAmountProgress() {
		return Response.ok().entity(collectManager.getCapsAmountProgress()).type(MediaType.APPLICATION_JSON).build();
	}
	
}
