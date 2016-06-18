package com.stobinski.bottlecaps.ejb.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.common.ImageManager;

@Path("/admin/")
public class AdminController {

	@Inject
	private ImageManager imageManager;
	
	@Inject
	private Logger log;
	
	@POST
//	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("image/upload")
	public Response uploadImage(String baseimage) {
		log.debug(baseimage);
		try {
			imageManager.saveImage(baseimage);
		} catch (IOException e) {
			log.error(e);
			return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().build();
	}
	
}
