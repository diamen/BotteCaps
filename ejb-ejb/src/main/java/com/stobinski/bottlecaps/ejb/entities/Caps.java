package com.stobinski.bottlecaps.ejb.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name="Caps.findCaps",
			    query="SELECT e FROM Caps e"),
	@NamedQuery(name="Caps.findMaxId",
				query="SELECT MAX(e.id) FROM Caps e"),
	@NamedQuery(name="Caps.findCapsByCountryId",
				query="SELECT e FROM Caps e " +
				  	  "WHERE e.country_id = :country_id"),
	@NamedQuery(name="Caps.countCapsByCountryId",
				query="SELECT COUNT(e) FROM Caps e " +
					  "WHERE e.country_id = :country_id"),
	@NamedQuery(name="Caps.findCapByIdAndCountryId",
				query="SELECT e FROM Caps e " +
					  "WHERE e.country_id = :country_id " +
					  "AND e.id = :id"),
	@NamedQuery(name="Caps.findCapsByCountryIdAndCapText",
				query="SELECT e FROM Caps e " +
					  "WHERE e.country_id = :country_id " +
					  "AND e.cap_text LIKE :cap_text"),
	@NamedQuery(name="Caps.findCapsByCountryIdAndBeer",
				query="SELECT e FROM Caps e " +
					  "WHERE e.country_id = :country_id " +
					  "AND e.beer = :beer"),
	@NamedQuery(name="Caps.findCapsByCountryIdBeerAndSearchText",
				query="SELECT e FROM Caps e " +
					  "WHERE e.country_id = :country_id " +
					  "AND e.cap_text LIKE :cap_text " +
					  "AND e.beer = :beer"),
	@NamedQuery(name="Caps.countCapsGroupByCountryId",
				query="SELECT e.country_id, f.name, f.flag, COUNT(e.country_id), " +
					  "COALESCE((SELECT COUNT(g) FROM Caps g WHERE g.country_id = f.id AND g.beer = 1 GROUP BY g.country_id), 0), " +
					  "COALESCE((SELECT COUNT(h) FROM Caps h WHERE h.country_id = f.id AND h.beer = 0 GROUP BY h.country_id), 0) " +
					  "FROM Caps e, Countries f " +
					  "WHERE e.country_id = f.id " +
					  "GROUP BY e.country_id"),
	@NamedQuery(name="Caps.findNewestCaps",
				query="SELECT e FROM Caps e " +
					  "ORDER BY e.added_date DESC"),
	@NamedQuery(name="Caps.countCapsGroupByAddedDate",
				query="SELECT COUNT(e.added_date), e.added_date, FUNCTION('MONTH', e.added_date) AS month, FUNCTION('YEAR', e.added_date) AS year FROM Caps e " +
					  "GROUP BY month, year ORDER BY e.added_date DESC")
})

@Entity
public class Caps implements Serializable, IBase64 {

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
	
	@Temporal(TemporalType.DATE)
	@Column(name=ADDED_DATE_NAME, nullable=false)
	private Date added_date;
	
	@Column(name=BEER_NAME, nullable=true)
	private int beer;
	
	@Column(name=BRAND_ID_NAME, nullable=true)
	private long brand_id;
	
	@Column(name=COUNTRY_ID_NAME, nullable=false)
	private long country_id;
	
	@Id
	@Column(name=ID_NAME, nullable=false, unique=true)
	private long id;

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
	
	@Override
	public String toString() {
		return String.format("[id: %d, cap_text: %s, file_name: %s, beer: %d, country_id: %d, brand_id: %d, added_date: %s]",
				id, cap_text, file_name, beer, country_id, brand_id, added_date.toString())
				.replace('\r', '\0').replace('\n', '\0');
	}
	
}
