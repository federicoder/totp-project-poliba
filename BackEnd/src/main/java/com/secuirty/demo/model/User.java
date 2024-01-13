package com.secuirty.demo.model;

import com.secuirty.demo.service.TotpManager;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
//	@Autowired
//	private TotpManager otp;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	private int id;
	private String username ;
	private String password ;
	private String mail;
	public boolean firstime= true;
	public int getId() {
		return id;
	}
	public String secret;
	
	public User() {
//	this.secret = otp.generateSecret();
	}

	public User(int id, String username, String password, String mail) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.mail = mail;
	}




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
	public String getmail() {
		return mail;
	}
	public void setmail(String email) {
		this.mail = email;
	}
	public void setSecret(String secret) { this.secret = secret;}
	
	
}
