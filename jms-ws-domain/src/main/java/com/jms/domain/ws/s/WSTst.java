package com.jms.domain.ws.store;

import java.util.Map;

import com.jms.domain.ws.WSTableData;

public class WSTst {

	private Long id;
    private String name;
    
    private Map<String,WSSType> tests;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Map<String,WSSType> getTests() {
		return tests;
	}
	public void setTests(Map<String,WSSType> tests) {
		this.tests = tests;
	}

   
}
