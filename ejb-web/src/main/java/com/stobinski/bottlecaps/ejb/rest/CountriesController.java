package com.stobinski.bottlecaps.ejb.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stobinski.bottlecaps.ejb.managers.CountriesManager;
import com.stobinski.bottlecaps.ejb.wrappers.CountriesWithAmount;

@Path("/countries/")
public class CountriesController {

	@Inject
	private CountriesManager countriesManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	public List<CountriesWithAmount> getCountries() {
		return countriesManager.getCountriesWithAmount();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{id : \\d+}")
	public String getCountry(@PathParam("id") Long id) {
		return countriesManager.getCountry(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{country : [a-zA-Z]+}/flag")
	public Response getFlag(@PathParam("country") String country) {
		Map<String, String> map = new HashMap<>();
		map.put("flag", countriesManager.getFlag(country));
		return Response.ok().type(MediaType.APPLICATION_JSON).entity(map).build();
	}

}
