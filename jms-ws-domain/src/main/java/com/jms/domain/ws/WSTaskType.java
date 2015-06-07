package com.jms.domain.ws;

public class WSTaskType {
	private int idTaskType;
	private String name;
	public WSTaskType()
	{
		
	}
	public WSTaskType(int idTaskType,String name)
	{
		this.idTaskType =idTaskType;
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdTaskType() {
		return idTaskType;
	}
	public void setIdTaskType(int idTaskType) {
		this.idTaskType = idTaskType;
	}

}
