package com.stobinski.bottlecaps.ejb.dao;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.common.LoggerFactory;
import com.stobinski.bottlecaps.ejb.dao.exceptions.ColumnsValuesNotMatchException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.OrderByException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.SqlFunctionLackException;
import com.stobinski.bottlecaps.ejb.dao.functions.SqlFunction;
import com.stobinski.bottlecaps.ejb.dao.functions.Update;
import com.stobinski.bottlecaps.ejb.dao.functions.eSqlFunctions;

public class StringQuery {

	private Logger log = LoggerFactory.create(StringQuery.class);
	
	private SqlFunction sqlFunction;
	private boolean where;
	private boolean asc;
	private boolean desc;
	private String orderColumn;
	private String[] columns;
	private Object[] setValues;
	private Object[] whereValues;
	private String[] likeValues;
	
	public static final String LIKE_VALUE = "likeValue";
	private static final String WHITESPACE = " ";
	private static final String AND = "AND";
	
	public StringQuery(QueryBuilder queryBuilder) {
		handleSqlFunction(queryBuilder.getSqlFunction());
		this.where = queryBuilder.isWhere();
		this.asc = queryBuilder.isAsc();
		this.desc = queryBuilder.isDesc();
		this.orderColumn = queryBuilder.getOrderColumn();
		
		if(asc && desc)
			throw new OrderByException();
		
		if(where) {
			this.columns = queryBuilder.getWhereColumns();
			
			if(queryBuilder.getWhereValues() != null) {
				this.whereValues = queryBuilder.getWhereValues();
				
				if(!isLengthMatch(columns, whereValues))
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
		
		String query = "";
		
		if(sqlFunction.getSqlFunctionName().equals(eSqlFunctions.Select.name()) | sqlFunction.getSqlFunctionName().equals(eSqlFunctions.Count.name())) {
			query = sqlFunction.getFunctionQuery();
		}
			
		boolean update = false;
		
		if(sqlFunction.getSqlFunctionName().equals(eSqlFunctions.Update.name())) {
			update = true;
			query = sqlFunction.getFunctionQuery();
			
			String[] setColumns = ((Update) sqlFunction).getColumns();
			setValues = ((Update) sqlFunction).getValues();
			
			query = appendUpdateColumns(query, setColumns);
		}
		
		query = where && this.whereValues != null ? 
				appendWhereColumns(update, query + " WHERE ", this.columns): 
				where && this.likeValues != null ?
				appendLikeColumns(query + " WHERE ", this.columns):
				query;
		
		boolean orderExist = orderColumn != null;		
				
		query = orderExist && !asc && !desc ? appendColumnToOrderBy(query, orderColumn):
				
				orderExist && asc && !desc ? appendAsc(appendColumnToOrderBy(query, orderColumn)):
					
				orderExist && !asc && desc ? appendDesc(appendColumnToOrderBy(query, orderColumn)):
					
				query;	
				
		log.debug(query);		
				
		return query;
	}
	
	public Object[] getSetValues() {
		return setValues;
	}
	
	public Object[] getWhereValues() {
		return whereValues;
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

	private String appendUpdateColumns(String query, String[] columns) {
		StringBuilder sb = new StringBuilder(query);
		
		for(int i = 0; i < columns.length; i++) {
			int valuesIndex = i + 1;
			sb.append("e." + columns[i] + "=?" + valuesIndex);
			
			if(i + 1 < columns.length)
				sb.append(", ");
		}
		
		return sb.toString();
	}
	
	private String appendWhereColumns(boolean update, String query, String[] columns) {
		StringBuilder sb = new StringBuilder(query);
		
		int j = 0;
		
		if(update) {
			j = ((Update) sqlFunction).getColumns().length;
		}
		
		for(int i = 0; i < columns.length; i++) {
			int valuesIndex = j + 1;
			sb.append("e." + columns[i] + "=?" + valuesIndex);
			
			j++;
			
			if(i + 1 < columns.length)
				sb.append(WHITESPACE + AND + WHITESPACE);
		}
		
		return sb.toString();
	}
	
	private String appendLikeColumns(String query, String[] columns) {
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
