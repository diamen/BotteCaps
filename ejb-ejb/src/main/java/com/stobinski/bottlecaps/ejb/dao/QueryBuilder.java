package com.stobinski.bottlecaps.ejb.dao;

import java.io.Serializable;

import com.stobinski.bottlecaps.ejb.dao.exceptions.MultipleInvocationException;
import com.stobinski.bottlecaps.ejb.dao.functions.Count;
import com.stobinski.bottlecaps.ejb.dao.functions.Select;
import com.stobinski.bottlecaps.ejb.dao.functions.SqlFunction;
import com.stobinski.bottlecaps.ejb.dao.functions.Update;

public class QueryBuilder {

	private SqlFunction sqlFunction;	
	private boolean where = false;
	private boolean desc = false;
	private boolean asc = false;
	private String orderColumn;
	private String[] whereColumns;
	private Object[] whereValues;
	private String[] likeValues;
	
	public QueryBuilder() {}
	
	public QueryBuilder(SqlFunction sqlFunction) {
		this.sqlFunction = sqlFunction;
	}
	
	public StringQuery build() {
		return new StringQuery(this);
	}
	
	public Select select() {
		if(this.sqlFunction != null)
			throw new MultipleInvocationException();
		
		Select select = new Select();
		this.sqlFunction = select;
		return select;
	}

	public Update update(Class <? extends Serializable> entity) {
		if(this.sqlFunction != null)
			throw new MultipleInvocationException();
			
		Update update = new Update(entity);
		this.sqlFunction = update;
		return update;
	}
	
	public Count count() {
		if(this.sqlFunction != null)
			throw new MultipleInvocationException();
			
		Count count = new Count();
		this.sqlFunction = count;
		return count;	
	}
	
	public QueryBuilder where(String... columns) {
		this.where = true;
		this.whereColumns = columns;
		return this;
	}
	
	public QueryBuilder orderBy(String column) {
		this.orderColumn = column;
		return this;
	}
	
	public QueryBuilder desc() {
		this.desc = true;
		return this;
	}

	public QueryBuilder asc() {
		this.asc = true;
		return this;
	}
	
	public QueryBuilder eq(Object... values) {
		this.whereValues = values;
		return this;
	}
	
	public QueryBuilder like(String... likeValues) {
		this.likeValues = likeValues;
		return this;
	}
	
	public SqlFunction getSqlFunction() {
		return sqlFunction;
	}

	public boolean isWhere() {
		return where;
	}
	
	public boolean isAsc() {
		return asc;
	}
	
	public boolean isDesc() {
		return desc;
	}
	
	public String[] getWhereColumns() {
		return whereColumns;
	}

	public Object[] getWhereValues() {
		return whereValues;
	}
	
	public String[] getLikeValues() {
		return likeValues;
	}
	
	public String getOrderColumn() {
		return orderColumn;
	}
	
}
