package com.stobinski.bottlecaps.ejb.dao.functions;

@FunctionalInterface
public interface SqlFunction {
	String getFunctionQuery();
	
	public default String getSqlFunctionName() {
		return this.getClass().getSimpleName();
	}
}
