package com.jms.domain.ws;

import org.hibernate.validator.constraints.Email;
import java.util.Date;

public class WSUser {
	
	private String name;
	private String username;
    private String mobile;
    @Email
    private String email;
    private String password;
    private Date creationTime;
    private String locale;
    
    public String getEmail() {
    	return email;
    }

    public void setEmail(String email) {
    	this.email = email;
    }


    public String getPassword() {
    	return password;
    }


    public void setPassword(String password) {
    	this.password = password;
    }


    public String getMobile() {
    	return mobile;
    }


    public void setMobile(String mobile) {
    	this.mobile = mobile;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}


}
