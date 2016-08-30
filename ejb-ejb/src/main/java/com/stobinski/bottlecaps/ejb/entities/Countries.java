package com.stobinski.bottlecaps.ejb.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name="Countries.findCountries",
			query="SELECT e FROM Countries e")

@Entity
public class Countries implements Serializable {

	private static final long serialVersionUID = -1948795941184625184L;
	
	public static final String NAME_NAME = "name";
	public static final String FLAG_NAME = "flag";
	public static final String ID_NAME = "id";
	
	@Column(name=NAME_NAME, nullable=false, unique=true)
	private String name;
	
	@Column(name=FLAG_NAME, nullable=false, unique=true)
	private String flag;
	
	@Id
	@Column(name=ID_NAME, nullable=false, unique=true)
	private long id;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
