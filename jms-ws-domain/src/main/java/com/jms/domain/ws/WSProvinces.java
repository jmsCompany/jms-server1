package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.List;

public class WSProvinces implements java.io.Serializable{
	
	private List<WSProvince> wsProvinces = new ArrayList<WSProvince>();

	public List<WSProvince> getWsProvinces() {
		return wsProvinces;
	}

	public void setWsProvinces(List<WSProvince> wsProvinces) {
		this.wsProvinces = wsProvinces;
	}

}
