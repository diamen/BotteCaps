package com.stobinski.bottlecaps.ejb.dao;

import java.io.Serializable;

public class QueryBuilder {

	private SqlFunction sqlFunction;
	private Class<? extends Serializable> entity;	
	private boolean where = false;
	private String[] columns;
	private Object[] values;
	private String[] likeValues;
	
	public Query build() {
		return new Query(this);
	}
	
	public QueryBuilder select() {
		if(this.sqlFunction != null)
			throw new MultipleInvocationException();
			
		this.sqlFunction = SqlFunction.SELECT;
		return this;
	}

	public QueryBuilder from(Class<? extends Serializable> entity) {
		if(this.entity != null)
			throw new MultipleInvocationException();
			
		this.entity = entity;
		return this;
	}

	public QueryBuilder where(String... columns) {
		this.where = true;
		this.columns = columns;
		return this;
	}
	
	public QueryBuilder eq(Object... values) {
		this.values = values;
		return this;
	}
	
	public QueryBuilder like(String... likeValues) {
		this.likeValues = likeValues;
		return this;
	}
	
	public SqlFunction getSqlFunction() {
		return sqlFunction;
	}
	
	public Class<? extends Serializable> getEntity() {
		return entity;
	}

	public boolean isWhere() {
		return where;
	}

	public String[] getColumns() {
		return columns;
	}

	public Object[] getValues() {
		return values;
	}

	public String[] getLikeValues() {
		return likeValues;
	}
	
}
