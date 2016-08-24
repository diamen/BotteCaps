package com.stobinski.bottlecaps.ejb.wrappers;

import java.util.Date;

import com.stobinski.bottlecaps.ejb.entities.MiniTradeCaps;
import com.stobinski.bottlecaps.ejb.entities.TradeCaps;

public class Base64TradeCap {

	private long id;
	private String extension;
	private String file_name;
	private String path;
	private Date added_date;
	private MiniTradeCaps miniTradeCaps;
	private String base64;
	
	public Base64TradeCap(long id, String extension, String file_name, String path, Date added_date, MiniTradeCaps miniTradeCaps, String base64) {
		this.id = id;
		this.extension = extension;
		this.file_name = file_name;
		this.path = path;
		this.added_date = added_date;
		this.miniTradeCaps = miniTradeCaps;
		this.base64 = base64;
	}

	public Base64TradeCap(TradeCaps cap, String base64) {
		this.id = cap.getId();
		this.extension = cap.getExtension();
		this.file_name = cap.getFile_name();
		this.path = cap.getPath();
		this.added_date = cap.getAdded_date();
		this.miniTradeCaps = cap.getMini_trade_caps();
		this.base64 = base64;
	}
	
	public MiniTradeCaps getMiniTradeCaps() {
		return miniTradeCaps;
	}

	public void setMiniTradeCaps(MiniTradeCaps miniTradeCaps) {
		this.miniTradeCaps = miniTradeCaps;
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
