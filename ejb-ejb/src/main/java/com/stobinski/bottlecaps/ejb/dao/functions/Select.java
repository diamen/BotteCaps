package com.stobinski.bottlecaps.ejb.dao.functions;

public class Select implements SqlFunction {

	@Override
	public String getFunctionQuery() {
		return "SELECT e";
	}
	
}
