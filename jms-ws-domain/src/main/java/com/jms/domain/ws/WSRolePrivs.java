package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WSRolePrivs implements java.io.Serializable{
	private String companyName;
    private String role;
    private List<WSModulePriv> modulePrivList =new ArrayList<WSModulePriv>(0);
    
	
    public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public List<WSModulePriv> getModulePrivList() {
		return modulePrivList;
	}
	public void setModulePrivList(List<WSModulePriv> modulePrivList) {
		this.modulePrivList = modulePrivList;
	}
}
