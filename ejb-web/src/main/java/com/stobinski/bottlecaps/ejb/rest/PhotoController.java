package com.stobinski.bottlecaps.ejb.rest;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.entities.Countries;

@Path("/photo/")
public class PhotoController {
	
	@Inject
	private DaoService dao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("countries")
	public List<String> countryList() {
		String basePath = "C:\\Users\\user\\workspace\\ejb\\ejb-web\\src\\main\\webapp\\resources\\gfx";
		File file = new File(basePath);
		return Arrays.asList(file.list());
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bycountry")
	public List<Caps> photosByCountry(String country) {
		Countries countries = (Countries) dao
				.retrieveSingleData(new QueryBuilder().select().from(Countries.class).where(Countries.NAME_NAME).eq(country).build());
		
		return dao.retrieveData(
				new QueryBuilder().select().from(Caps.class).where(Caps.COUNTRY_ID_NAME).eq(countries.getId()).build())
				.stream().map(e -> (Caps) e).collect(Collectors.toList());
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("idbycountry")
	public Integer idByCountry(String country) {
		Countries countries = (Countries) dao.retrieveSingleData
				(new QueryBuilder().select().from(Countries.class).where(Countries.NAME_NAME).eq(country).build());
		return countries.getId();
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("singlecap")
	public Caps getSingleCap(@QueryParam("countryId") Integer countryId, @QueryParam("id") Integer id) {
		return (Caps) dao.retrieveSingleData
				(new QueryBuilder().select().from(Caps.class).where(Caps.COUNTRY_ID_NAME, Caps.ID_NAME).eq(countryId, id).build());
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("count")
	public Map<String, Integer> countPhotos() {
		String basePath = "C:\\Users\\user\\workspace\\ejb\\ejb-web\\src\\main\\webapp\\resources\\gfx";
		File file = new File(basePath);
		List<String> countries = Arrays.asList(file.list());
		
		Map<String, Integer> countryMap = new HashMap<>();
		
		for(String country : countries) {
			int count = new File(basePath + File.separatorChar + country).list().length;
			countryMap.put(country, count);
		}
		return countryMap;
	}
}
