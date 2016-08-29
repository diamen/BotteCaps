package com.stobinski.bottlecaps.ejb.dao.functions;

import java.io.Serializable;

import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;

public class Delete implements SqlFunction {

	private String query;
	
	@Override
	public String getFunctionQuery() {
		return query;
	}
	
	public QueryBuilder from(Class<? extends Serializable> entity) {
		this.query = "DELETE FROM " + entity.getSimpleName() + " e";
		return new QueryBuilder(this);
	}
	
}