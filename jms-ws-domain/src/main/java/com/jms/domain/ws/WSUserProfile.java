package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.List;

import com.jms.domain.ws.m.WSMachine;
import com.jms.domain.ws.production.WSPCppAndriod;
import com.jms.domain.ws.production.WSPWo;

public class WSUserProfile implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idUser;
	private String login;
	private String token;
	private String logoURL;
	private Long idCompany;
	private String name;
	private List<WSMenu> WSMenuList;
	private Boolean isOP;
	
	private String department;
	private List<WSPCppAndriod> pcppList =new ArrayList<WSPCppAndriod>();
	
	
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsOP() {
		return isOP;
	}
	public void setIsOP(Boolean isOP) {
		this.isOP = isOP;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public List<WSPCppAndriod> getPcppList() {
		return pcppList;
	}
	public void setPcppList(List<WSPCppAndriod> pcppList) {
		this.pcppList = pcppList;
	}


}
