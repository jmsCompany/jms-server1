package com.jms.domain.ws;

public class WSBomMaterialObj {
	

	private String id;
	private String name;
	
	public WSBomMaterialObj() {
	}
	
	public WSBomMaterialObj(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
