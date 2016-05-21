package com.stobinski.bottlecaps.ejb.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users implements Serializable {

	private static final long serialVersionUID = -6584916065082672361L;

	@Id
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	@Column(name="password", nullable=false, unique=true)
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
