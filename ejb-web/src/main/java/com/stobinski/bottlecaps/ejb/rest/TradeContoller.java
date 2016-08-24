package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.dao.TradeCapsManager;
import com.stobinski.bottlecaps.ejb.wrappers.Base64TradeCap;

@Path("/trade/")
public class TradeContoller {

	@Inject
	private TradeCapsManager tradeCapsManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("caps")
	public List<Base64TradeCap> getTradeCaps() {
		return tradeCapsManager.getTradeCaps();
	}
	
}
