package com.jms.domain.ws;

public class WSModulePriv implements java.io.Serializable{

	private String name;
	private String description;
	private Integer priv;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPriv() {
		return priv;
	}
	public void setPriv(Integer priv) {
		this.priv = priv;
	}
}
