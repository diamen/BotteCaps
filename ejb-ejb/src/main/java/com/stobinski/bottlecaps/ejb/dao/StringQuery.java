package com.stobinski.bottlecaps.ejb.dao;

import java.io.Serializable;

import com.stobinski.bottlecaps.ejb.dao.exceptions.ColumnsValuesNotMatchException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.FromClassLackException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.OrderByException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.SqlFunctionLackException;
import com.stobinski.bottlecaps.ejb.dao.functions.SqlFunction;

public class StringQuery {

	public static final String LIKE_VALUE = "likeValue";
	
	private SqlFunction sqlFunction;
	private Class<? extends Serializable> entity;
	private boolean where;
	private boolean asc;
	private boolean desc;
	private String orderColumn;
	private String[] columns;
	private Object[] values;
	private String[] likeValues;
	
	private static final String WHITESPACE = " ";
	private static final String AND = "AND";
	
	public StringQuery(QueryBuilder queryBuilder) {
		handleSqlFunction(queryBuilder.getSqlFunction());
		handleFrom(queryBuilder.getEntity());
		this.where = queryBuilder.isWhere();
		this.asc = queryBuilder.isAsc();
		this.desc = queryBuilder.isDesc();
		this.orderColumn = queryBuilder.getOrderColumn();
		
		if(asc && desc)
			throw new OrderByException();
		
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
		String query = sqlFunction.getFunctionQuery() + " FROM " + entity.getSimpleName() + " e";
			
		query = where && this.values != null ? 
				appendColumnsToWhereEq(query + " WHERE ", this.columns): 
				where && this.likeValues != null ?
				appendColumnsToWhereLike(query + " WHERE ", this.columns):
				query;
		
		boolean orderExist = orderColumn != null;		
				
		query = orderExist && !asc && !desc ? appendColumnToOrderBy(query, orderColumn):
				
				orderExist && asc && !desc ? appendAsc(appendColumnToOrderBy(query, orderColumn)):
					
				orderExist && !asc && desc ? appendDesc(appendColumnToOrderBy(query, orderColumn)):
					
				query;	
				
		return query;
	}
	
	public Object[] getValues() {
		return values;
	}
	
	public Object[] getLikeValues() {
		return likeValues;
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
		
		for(int i = 1; i <= columns.length; i++)
			sb.append("e." + columns[i - 1] + " LIKE :" + LIKE_VALUE + i);
		
		return sb.toString();
	}
	
	private String appendColumnToOrderBy(String query, String column) {
		query += " ORDER BY e." + column;
		return query;
	}

	private String appendDesc(String query) {
		query += " DESC";
		return query;
	}
	
	private String appendAsc(String query) {
		query += " ASC";
		return query;
	}
	
	private boolean isLengthMatch(String[] columns, Object[] values) {
		return columns.length == values.length;
	}
	
}
