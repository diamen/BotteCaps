package com.stobinski.bottlecaps.ejb.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.common.Base64Service;
import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.common.NewsManager;
import com.stobinski.bottlecaps.ejb.dao.TradeCapsManager;

@Path("/admin/")
public class AdminController {

	@Inject
	private ImageManager imageManager;
	
	@Inject
	private NewsManager newsManager;
	
	@Inject
	private Logger log;
	
	@Inject
	private TradeCapsManager tradeManager;
	
	@POST
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("image/upload")
	public Response uploadImage(String baseimage, 
			@QueryParam("captext") String captext, 
			@QueryParam("capbrand") String capbrand,
			@QueryParam("beer") Integer beer,
			@QueryParam("country") String country) {
		try {
			imageManager.saveImage(Base64Service.fromBase64JsonToByteArray(baseimage), captext, capbrand, beer == 1, country);
		} catch (IOException e) {
			log.error(e);
			return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().build();
	}

	@POST
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("trade/upload")
	public Response uploadTrade(String baseimage, 
			@QueryParam("filename") String filename) {

		tradeManager.saveFile(baseimage, filename);
		
		return Response.ok().build();
	}
	
	@POST
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("image/delete")
	public Response deleteFile(@QueryParam("country") String country, @QueryParam("capId") Long capId) {
		imageManager.removeCap(country, capId);
		
		return Response.ok().build();
	}
	
	@POST
//  @AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("image/update")
	public Response updateImage(@QueryParam("id") Long id,
			@QueryParam("country") String country,
			@QueryParam("captext") String captext,
			@QueryParam("capbrand") String capbrand,
			@QueryParam("beer") Integer beer) {
		
		imageManager.updateCap(id, country, captext, capbrand, beer);
		
		return Response.ok().build();
	}
	
	@POST
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("news/add")
	public Response addNews(@QueryParam("title") String title, @QueryParam("content") String content) {
		newsManager.saveNews(title, content);
		
		return Response.ok().build();
	}
	
}
