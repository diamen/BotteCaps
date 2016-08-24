package com.stobinski.bottlecaps.ejb.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

@Entity
@Table(name="mini_trade_caps")
public class MiniTradeCaps implements Serializable {

	private static final long serialVersionUID = -8434576889436926419L;
	
	public static final String EXTENSION_NAME = "extension";
	public static final String FILE_NAME_NAME = "file_name";
	public static final String PATH_NAME = "path";
	public static final String ADDED_DATE_NAME = "added_date";
	public static final String ID_NAME = "id";

	@JsonBackReference
	@OneToOne(fetch=FetchType.EAGER, mappedBy="mini_trade_caps", cascade=CascadeType.PERSIST)
	private TradeCaps trade_caps;

	@Column(name=EXTENSION_NAME, nullable=false)
	private String extension;
	
	@Column(name=FILE_NAME_NAME, nullable=false)
	private String file_name;
	
	@Column(name=PATH_NAME, nullable=false)
	private String path;
	
	@Column(name=ADDED_DATE_NAME, nullable=false)
	private Date added_date;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=ID_NAME, nullable=false, unique=true)
	private long id;

	public TradeCaps getTrade_caps() {
		return trade_caps;
	}

	public void setTrade_caps(TradeCaps trade_caps) {
		this.trade_caps = trade_caps;
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

}
