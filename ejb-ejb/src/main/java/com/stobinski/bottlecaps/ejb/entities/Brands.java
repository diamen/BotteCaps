package com.stobinski.bottlecaps.ejb.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Brands implements Serializable {
	
	private static final long serialVersionUID = -430815667411950690L;
	
	public static final String NAME_NAME = "name";
	public static final String ID_NAME = "id";
	
	@Column(name=NAME_NAME, nullable=false)
	private String name;
	
	@Id
	@Column(name=ID_NAME, nullable=false, unique=true)
	private long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
