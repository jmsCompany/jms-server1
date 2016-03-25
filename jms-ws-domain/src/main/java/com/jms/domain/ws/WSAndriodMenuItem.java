package com.jms.domain.ws;

public class WSAndriodMenuItem implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	public WSAndriodMenuItem(String role, String roleDes, String viewId, String viewDesEn, String viewDesCn) {
		super();
		this.role = role;
		this.roleDes = roleDes;
		this.viewId = viewId;
		this.viewDesEn = viewDesEn;
		this.viewDesCn = viewDesCn;
	}
	private String role;
	private String roleDes;
	private String viewId;
	private String viewDesEn;
	private String viewDesCn;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRoleDes() {
		return roleDes;
	}
	public void setRoleDes(String roleDes) {
		this.roleDes = roleDes;
	}
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	public String getViewDesEn() {
		return viewDesEn;
	}
	public void setViewDesEn(String viewDesEn) {
		this.viewDesEn = viewDesEn;
	}
	public String getViewDesCn() {
		return viewDesCn;
	}
	public void setViewDesCn(String viewDesCn) {
		this.viewDesCn = viewDesCn;
	}
	
	

}
