package com.stobinski.bottlecaps.ejb.rest;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.common.Base64Service;
import com.stobinski.bottlecaps.ejb.managers.CollectManager;
import com.stobinski.bottlecaps.ejb.managers.NewsManager;
import com.stobinski.bottlecaps.ejb.trade.TradeCapsManager;

@Path("/admin/")
public class AdminController {

	@Inject
	private CollectManager collectManager;
	
	@Inject
	private NewsManager newsManager;
	
	@Inject
	private TradeCapsManager tradeManager;
	
	@Inject
	private Logger log;
	
	@POST
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("collect")
	public Response addCap(String baseimage, 
			@QueryParam("captext") String captext, 
			@QueryParam("capbrand") String capbrand,
			@QueryParam("beer") Integer beer,
			@QueryParam("country") String country) {
		try {
			collectManager.saveCap(Base64Service.fromBase64JsonToByteArray(baseimage), captext, capbrand, beer == 1, country);
		} catch (IOException e) {
			log.error(e);
			return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().build();
	}

	@PUT
//  @AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("collect")
	public Response editCap(@QueryParam("id") Long id,
			@QueryParam("country") String country,
			@QueryParam("captext") String captext,
			@QueryParam("capbrand") String capbrand,
			@QueryParam("beer") Integer beer) {
		
		collectManager.updateCap(id, captext, country, capbrand, beer);
		
		return Response.ok().build();
	}
	
	@DELETE
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("collect")
	public Response deleteCap( @QueryParam("capId") Long capId) {
		// TODO Plik nie jest usuwany z lokalizacji
		
		collectManager.removeCap(capId);
		
		return Response.ok().build();
	}
	
	@POST
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("trade/upload")
	public Response addTrade(String baseimage, 
			@QueryParam("filename") String filename) {

		try {
			tradeManager.saveFile(baseimage, filename);
		} catch (FileAlreadyExistsException e) {
			log.error(e);
			Map<String, String> map = new HashMap<>();
			map.put("message", "Plik już istnieje w bazie danych");
			return Response.status(Response.Status.CONFLICT)
					.entity(map)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		
		return Response.ok().build();
	}
	
	@DELETE
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("tradecaps")
	public Response deleteTrade(@QueryParam("ids") Set<Long> ids) {
		tradeManager.deleteFiles(ids);
		
		return Response.ok().build();
	}
	
	@POST
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("news")
	public Response addNews(@QueryParam("title") String title, @QueryParam("content") String content) {
		
		// TODO Przekierowanie na formatce po dodaniu nowego newsa
		
		newsManager.saveNews(title, content);
		
		return Response.ok().build();
	}
	
}
