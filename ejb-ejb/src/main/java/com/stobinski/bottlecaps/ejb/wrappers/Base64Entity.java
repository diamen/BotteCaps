package com.stobinski.bottlecaps.ejb.wrappers;

import com.stobinski.bottlecaps.ejb.entities.IBase64;

public class Base64Entity {

	private IBase64 entity;
	private String base64;
	
	public Base64Entity(IBase64 entity, String base64) {
		this.entity = entity;
		this.base64 = base64;
	}

	public IBase64 getEntity() {
		return entity;
	}

	public void setEntity(IBase64 entity) {
		this.entity = entity;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
}
