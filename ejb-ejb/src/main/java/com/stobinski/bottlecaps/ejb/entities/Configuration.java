package com.stobinski.bottlecaps.ejb.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Configuration implements Serializable {

	private static final long serialVersionUID = -1392126481532083536L;
	
	public static final String KEYY_NAME = "keyy";
	public static final String VALUE_NAME = "value";

	@Id
	@Column(name=KEYY_NAME)
	private String keyy;
	
	@Column(name=VALUE_NAME)
	private String value;

	public String getKeyy() {
		return keyy;
	}

	public void setKeyy(String keyy) {
		this.keyy = keyy;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
