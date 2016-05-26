package com.stobinski.bottlecaps.ejb.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Caps implements Serializable {

	private static final long serialVersionUID = -4314012675133787319L;
	
	public static final String CAP_TEXT_NAME = "cap_text";
	public static final String EXTENSION_NAME = "extension";
	public static final String FILE_NAME_NAME = "file_name";
	public static final String PATH_NAME = "path";
	public static final String ADDED_DATE_NAME = "added_date";
	public static final String BEER_NAME = "beer";
	public static final String BRAND_ID_NAME = "brand_id";
	public static final String COUNTRY_ID_NAME = "country_id";
	public static final String ID_NAME = "id";
	
	@Column(name=CAP_TEXT_NAME)
	private String cap_text;
	
	@Column(name=EXTENSION_NAME, nullable=false)
	private String extension;
	
	@Column(name=FILE_NAME_NAME, nullable=false)
	private String file_name;
	
	@Column(name=PATH_NAME, nullable=false)
	private String path;
	
	@Column(name=ADDED_DATE_NAME, nullable=false)
	private Date added_date;
	
	@Column(name=BEER_NAME, nullable=true)
	private int beer;
	
	@Column(name=BRAND_ID_NAME, nullable=true)
	private int brand_id;
	
	@Column(name=COUNTRY_ID_NAME, nullable=false)
	private int country_id;
	
	@Id
	@Column(name=ID_NAME, nullable=false, unique=true)
	private int id;

	public String getCap_text() {
		return cap_text;
	}

	public void setCap_text(String cap_text) {
		this.cap_text = cap_text;
	}
	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getAdded_date() {
		return added_date;
	}

	public void setAdded_date(Date added_date) {
		this.added_date = added_date;
	}

	public int getBeer() {
		return beer;
	}

	public void setBeer(int beer) {
		this.beer = beer;
	}

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
