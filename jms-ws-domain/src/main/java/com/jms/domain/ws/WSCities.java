package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.List;

public class WSCities implements java.io.Serializable{
	
	private List<WSCity> wscities = new ArrayList<WSCity>();

	public List<WSCity> getWscities() {
		return wscities;
	}

	public void setWscities(List<WSCity> wscities) {
		this.wscities = wscities;
	}


}
