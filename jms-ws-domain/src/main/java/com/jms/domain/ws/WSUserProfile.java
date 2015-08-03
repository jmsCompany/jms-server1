package com.jms.domain.ws;

public class WSUserProfile implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idUser;
	private String login;
	private String token;
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

}
