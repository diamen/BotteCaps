package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.trade.TradeCapsManager;
import com.stobinski.bottlecaps.ejb.wrappers.Base64MiniTradeCap;
import com.stobinski.bottlecaps.ejb.wrappers.Base64TradeCap;

@Path("/trade/")
public class TradeContoller {

	@Inject
	private TradeCapsManager tradeCapsManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("minicaps")
	public List<Base64MiniTradeCap> getMiniTradeCaps() {
		return tradeCapsManager.getMiniTradeCaps();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("tradecap/{id}")
	public Base64TradeCap getTradeCap(@PathParam("id") Long id) {
		return tradeCapsManager.getTradeCap(id);
	}
	
}
