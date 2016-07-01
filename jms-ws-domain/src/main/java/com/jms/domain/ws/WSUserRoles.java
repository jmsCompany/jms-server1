package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class WSUserRoles implements java.io.Serializable{
	
	
	private Long idUser;
	private String name;
	private String username;
 
    private List<WSRoles> roleList = new ArrayList<WSRoles>(0);
  
    public WSUserRoles(){}

	public List<WSRoles> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<WSRoles> roleList) {
		this.roleList = roleList;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
