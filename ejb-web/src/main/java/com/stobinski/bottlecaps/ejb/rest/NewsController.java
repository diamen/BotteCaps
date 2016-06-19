package com.stobinski.bottlecaps.ejb.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.entities.News;

@Path("/news/")
public class NewsController {
	
	@Inject
	private DaoService dao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	public List<News> getAllTheNews() {
		return dao.retrieveData(new QueryBuilder().select().from(News.class).build())
				.stream().map(e -> (News) e).collect(Collectors.toList());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("page/{no}")
	public List<News> getPageNews(@PathParam("no") int no) {
		int offset = (no - 1) * 10;
		
		return dao.retrieveData(new QueryBuilder().select().from(News.class).build(), 10, offset)
				.stream().map(e -> (News) e).collect(Collectors.toList());
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public News getNews(@PathParam("id") int id) {
		return (News) dao.retrieveSingleData(new QueryBuilder().select().from(News.class).where(News.ID_NAME).eq(id).build());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("count")
	public Long getNewsCount() {
		return (Long) dao.retrieveSingleData(new QueryBuilder().count().from(News.class).build());
	}
	
}
