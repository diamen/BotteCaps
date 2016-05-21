package com.stobinski.bottlecaps.ejb.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class News implements Serializable {
	
	private static final long serialVersionUID = 4598704830814493764L;
	
	public static final String DATE_NAME = "date";
	public static final String TITLE_NAME = "title";
	public static final String CONTENT_NAME = "content";
	public static final String ID_NAME = "id";
	
	@Column(name=DATE_NAME, nullable=false)
	private Date date;
	
	@Column(name=TITLE_NAME, nullable=false)
	private String title;
	
	@Column(name=CONTENT_NAME, nullable=false)
	private String content;
	
	@Id
	@Column(name=ID_NAME, nullable=false, unique=true)
	private int id;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
