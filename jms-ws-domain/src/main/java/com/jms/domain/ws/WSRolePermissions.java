package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.List;

import com.jms.domain.ws.m.WSMachine;
import com.jms.domain.ws.production.WSPCppAndriod;
import com.jms.domain.ws.production.WSPWo;

public class WSRolePermissions implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idRole;
	private String role;
	private String descrption;
	private List<WSMenu> menuList=new ArrayList<WSMenu>();
	public Long getIdRole() {
		return idRole;
	}
	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	public List<WSMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<WSMenu> menuList) {
		this.menuList = menuList;
	}



}
