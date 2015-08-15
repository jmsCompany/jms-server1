package com.jms.domain.ws;

import java.util.List;

public class WSUserProfile implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idUser;
	private String login;
	private String token;
	private String logoURL;
	private Long idCompany;
	private String username;
	private List<WSMenu> WSMenuList;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public List<WSMenu> getWSMenuList() {
		return WSMenuList;
	}
	public void setWSMenuList(List<WSMenu> wSMenuList) {
		WSMenuList = wSMenuList;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	public String getLogoURL() {
		return logoURL;
	}
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

}
