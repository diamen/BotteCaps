package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.entities.News;
import com.stobinski.bottlecaps.ejb.managers.NewsManager;

@Path("/news/")
public class NewsController {
	
	@Inject
	private NewsManager newsManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	public List<News> getNews() {
		return newsManager.getNews();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("page/{no}")
	public List<News> getPageNews(@PathParam("no") int no) {
		return newsManager.getNews(no);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public News getSingleNews(@PathParam("id") int id) {
		return newsManager.getSingleNews(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("count")
	public Long getNewsCount() {
		return newsManager.countNews();
	}
	
}
