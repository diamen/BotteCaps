package com.stobinski.bottlecaps.ejb.wrappers;

public class Base64Cap {

	private long id;
	private String base64;
	
	public Base64Cap(long id, String base64) {
		this.id = id;
		this.base64 = base64;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getBase64() {
		return base64;
	}
	
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
}
