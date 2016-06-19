package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.entities.Countries;

@Path("/collect/")
public class CollectionController {
	
	@Inject
	private DaoService dao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("countries")
	public List<Countries> getCountries() {
		return dao.retrieveData(new QueryBuilder().select().from(Countries.class).build())
				.stream().map(e -> (Countries) e).collect(Collectors.toList());
	}
	
}
