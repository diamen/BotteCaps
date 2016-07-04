package com.stobinski.bottlecaps.ejb.wrappers;

import java.util.Date;

import com.stobinski.bottlecaps.ejb.entities.Caps;

public class Base64Cap {

	private long id;
	private String cap_text;
	private String extension;
	private String file_name;
	private String path;
	private Date added_date;
	private int beer;
	private long brand_id;
	private long country_id;
	private String base64;
	
	public Base64Cap(long id, String cap_text, String extension, String file_name, String path, Date added_date, int beer, long brand_id, long country_id, String base64) {
		this.id = id;
		this.cap_text = cap_text;
		this.extension = extension;
		this.file_name = file_name;
		this.path = path;
		this.added_date = added_date;
		this.beer = beer;
		this.brand_id = brand_id;
		this.country_id = country_id;
		this.base64 = base64;
	}

	public Base64Cap(Caps cap, String base64) {
		this.id = cap.getId();
		this.cap_text = cap.getCap_text();
		this.extension = cap.getExtension();
		this.file_name = cap.getFile_name();
		this.path = cap.getPath();
		this.added_date = cap.getAdded_date();
		this.beer = cap.getBeer();
		this.brand_id = cap.getBrand_id();
		this.country_id = cap.getCountry_id();
		this.base64 = base64;
	}
	
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

	public long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(long brand_id) {
		this.brand_id = brand_id;
	}

	public long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(long country_id) {
		this.country_id = country_id;
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
