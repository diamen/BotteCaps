package com.stobinski.bottlecaps.ejb.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.common.ImageManager;
import com.stobinski.bottlecaps.ejb.security.AuthToken;

@Path("/admin/")
public class AdminController {

	@Inject
	private ImageManager imageManager;
	
	@Inject
	private Logger log;
	
	@POST
	@AuthToken
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("image/upload")
	public void uploadImage(String baseimage) {
		log.debug(baseimage);
		try {
			imageManager.saveImage(baseimage);
		} catch (IOException e) {
			log.error(e);
		}
	}
	
}
