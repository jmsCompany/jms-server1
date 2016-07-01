package com.jms.acl;

import java.io.Serializable;

public class SidPerm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String sid;
	private String permission;
	private String type; //Company, GROUP, SECTOR,ROLE,USER;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
