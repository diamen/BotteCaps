package com.stobinski.bottlecaps.ejb.dao;

import java.io.Serializable;

public class Query {

	public static final String LIKE_VALUE = "likeValue";
	
	private SqlFunction sqlFunction;
	private Class<? extends Serializable> entity;
	private boolean where;
	private String[] columns;
	private Object[] values;
	private String[] likeValues;
	
	private static final String WHITESPACE = " ";
	private static final String AND = "AND";
	
	public Query(QueryBuilder queryBuilder) {
		handleSqlFunction(queryBuilder.getSqlFunction());
		handleFrom(queryBuilder.getEntity());
		this.where = queryBuilder.isWhere();
		
		if(where) {
			this.columns = queryBuilder.getColumns();
			
			if(queryBuilder.getValues() != null) {
				this.values = queryBuilder.getValues();
				
				if(!isLengthMatch(columns, values))
					throw new ColumnsValuesNotMatchException();
			}
			
			if(queryBuilder.getLikeValues() != null) {
				this.likeValues = queryBuilder.getLikeValues();
				
				if(!isLengthMatch(columns, likeValues))
					throw new ColumnsValuesNotMatchException();
			}
			
		}
	}
	
	@Override
	public String toString() {
		String query = sqlFunction.name() + " e FROM " + entity.getSimpleName() + " e";
		
		query = where && this.values != null ? 
				appendColumnsToWhereEq(query + " WHERE ", this.columns): 
				where && this.likeValues != null ?
				appendColumnsToWhereLike(query + " WHERE ", this.columns):
				query;
		
		return query;
	}
	
	private void handleSqlFunction(SqlFunction sqlFunction) {
		if(sqlFunction != null)
			this.sqlFunction = sqlFunction;
		else
			throw new SqlFunctionLackException();
	}
	
	private void handleFrom(Class<? extends Serializable> entity) {
		if(entity != null)
			this.entity = entity;
		else
			throw new FromClassLackException();
	}
	
	private String appendColumnsToWhereEq(String query, String[] columns) {
		StringBuilder sb = new StringBuilder(query);
		
		for(int i = 0; i < columns.length; i++) {
			int valuesIndex = i + 1;
			sb.append("e." + columns[i] + "=?" + valuesIndex);
			
			if(i + 1 < columns.length)
				sb.append(WHITESPACE + AND + WHITESPACE);
		}
		
		return sb.toString();
	}
	
	private String appendColumnsToWhereLike(String query, String[] columns) {
		StringBuilder sb = new StringBuilder(query);
		
		for(int i = 0; i < columns.length; i++)
			sb.append("e." + columns[i] + " LIKE :" + LIKE_VALUE);
		
		return sb.toString();
	}
	
	private boolean isLengthMatch(String[] columns, Object[] values) {
		return columns.length == values.length;
	}
	
}
