package com.jms.domain.ws.s;


public class WSLotNo {

	private String id;
    private String name;
	

	public WSLotNo(String id, String name) {
		this.id=id;
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
   
}
