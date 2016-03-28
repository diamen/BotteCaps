package com.stobinski.bottlecaps.ejb.rest;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/photo/")
public class PhotoController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("countries")
	public List<String> countryList() {
		String basePath = "C:\\Users\\user\\workspace\\ejb\\ejb-web\\src\\main\\webapp\\resources\\gfx";
		File file = new File(basePath);
		return Arrays.asList(file.list());
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
