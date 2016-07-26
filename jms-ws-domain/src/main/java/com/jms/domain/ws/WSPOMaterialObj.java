package com.jms.domain.ws;

public class WSPOMaterialObj {
	

	private String id;
	private String name;
	
	public WSPOMaterialObj() {
	}
	
	public WSPOMaterialObj(String id, String name) {
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
