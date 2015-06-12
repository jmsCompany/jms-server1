package com.jms.domain.ws;

import org.hibernate.validator.constraints.Email;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class WSUser implements java.io.Serializable{
	
    private Integer idUser;
	private String name;
	private String username;
	
    private String mobile;
    @Email
    private String email;
    private String password;
    private Date creationTime;
    private String locale;
    
 
    private String address;
    private String idcard;
    private int enabled;
    private String ext;
    private String ENo;
    private String school;
    private Date gradTime;
    private String major;
    private String degree;
    private String emergencyHp;
    
    private String login;
    
    public WSUser(){}
    
    public WSUser(WSUser user){
    	this.idUser = user.getIdUser();
    	this.username =user.getUsername();
    	this.email=user.getEmail();
    	this.mobile=user.getMobile();
    	this.locale=user.getLocale();
    	this.login=user.getLogin();
    	this.password=user.getPassword();
    }
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getENo() {
		return ENo;
	}

	public void setENo(String eNo) {
		ENo = eNo;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Date getGradTime() {
		return gradTime;
	}

	public void setGradTime(Date gradTime) {
		this.gradTime = gradTime;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getEmergencyHp() {
		return emergencyHp;
	}

	public void setEmergencyHp(String emergencyHp) {
		this.emergencyHp = emergencyHp;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}


}
