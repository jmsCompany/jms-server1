package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class WSUser implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer idUser;
	private String name;
	private String username;
    private String mobile;
    private String email;
    private String password;
    private Date creationTime;
    private WSSysDicD localeD;
    private WSSysDicD status;
    private WSSysDicD scheme;
    private WSSysDicD gender;
    private String address;
    private String idcard;
    private Integer enabled;
    private String ext;
    private String ENo;
    private String school;
    private Date gradTime;
    private String major;
    private String degree;
    private String emergencyHp;
    private String login;
    private String description;
    private Date birthday;
    private Date lastLogin;
    private String token;
    private String locale;
    
    private List<WSRoles> roleList = new ArrayList<WSRoles>(0);
    public WSUser(){}
    
    public WSUser(WSUser user){
    	this.idUser = user.getIdUser();
    	this.username =user.getUsername();
    	this.email=user.getEmail();
    	this.mobile=user.getMobile();
    	this.setLocale(user.getLocale());
    	this.login=user.getLogin();
    	this.password=user.getPassword();
    	this.token =user.getToken();
 
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

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
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


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public WSSysDicD getStatus() {
		return status;
	}

	public void setStatus(WSSysDicD status) {
		this.status = status;
	}

	public WSSysDicD getScheme() {
		return scheme;
	}

	public void setScheme(WSSysDicD scheme) {
		this.scheme = scheme;
	}

	public WSSysDicD getGender() {
		return gender;
	}

	public void setGender(WSSysDicD gender) {
		this.gender = gender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public WSSysDicD getLocaleD() {
		return localeD;
	}

	public void setLocaleD(WSSysDicD localeD) {
		this.localeD = localeD;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public List<WSRoles> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<WSRoles> roleList) {
		this.roleList = roleList;
	}


}
