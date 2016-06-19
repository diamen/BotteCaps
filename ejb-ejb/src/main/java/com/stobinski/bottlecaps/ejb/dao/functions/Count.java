package com.stobinski.bottlecaps.ejb.dao.functions;

public class Count implements SqlFunction {

	@Override
	public String getFunctionQuery() {
		return "SELECT COUNT(e)";
	}
	
}
