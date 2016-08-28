package com.stobinski.bottlecaps.ejb.wrappers;

import java.util.Date;

import com.stobinski.bottlecaps.ejb.entities.MiniTradeCaps;
import com.stobinski.bottlecaps.ejb.entities.TradeCaps;

public class Base64MiniTradeCap {

	private long id;
	private String extension;
	private String file_name;
	private String path;
	private Date added_date;
	private TradeCaps tradeCaps;
	private String base64;
	
	public Base64MiniTradeCap(long id, String extension, String file_name, String path, Date added_date, TradeCaps tradeCaps, String base64) {
		this.id = id;
		this.extension = extension;
		this.file_name = file_name;
		this.path = path;
		this.added_date = added_date;
		this.tradeCaps = tradeCaps;
		this.base64 = base64;
	}

	public Base64MiniTradeCap(MiniTradeCaps cap, String base64) {
		this.id = cap.getId();
		this.extension = cap.getExtension();
		this.file_name = cap.getFile_name();
		this.path = cap.getPath();
		this.added_date = cap.getAdded_date();
		this.tradeCaps = cap.getTrade_caps();
		this.base64 = base64;
	}
	
	public TradeCaps getTradeCaps() {
		return tradeCaps;
	}

	public void setTradeCaps(TradeCaps tradeCaps) {
		this.tradeCaps = tradeCaps;
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
