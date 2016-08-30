package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	
}
