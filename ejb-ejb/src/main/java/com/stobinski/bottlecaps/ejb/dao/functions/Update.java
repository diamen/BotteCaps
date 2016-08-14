package com.stobinski.bottlecaps.ejb.dao.functions;

import java.io.Serializable;

import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;

public class Update implements SqlFunction {

	private String query;

	private String[] columns;
	private Object[] values;
	
	public Update(Class<? extends Serializable> entity) {
		this.query = "UPDATE " + entity.getSimpleName() + " e";
	}
	
	@Override
	public String getFunctionQuery() {
		return query;
	}
	
	public Update set(String... columns) {
		this.query += " SET ";
		this.columns = columns;
		return this;
	}
	
	public QueryBuilder eq(Object... values) {
		this.values = values;
		return new QueryBuilder(this);
	}
	
	public String[] getColumns() {
		return this.columns;
	}

	public Object[] getValues() {
		return this.values;
	}
	
}
