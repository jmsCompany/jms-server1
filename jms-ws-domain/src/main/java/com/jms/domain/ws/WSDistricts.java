package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.List;

public class WSDistricts implements java.io.Serializable{
	
	private List<WSDistrict> wsDistricts = new ArrayList<WSDistrict>();

	public List<WSDistrict> getWsDistricts() {
		return wsDistricts;
	}

	public void setWsDistricts(List<WSDistrict> wsDistricts) {
		this.wsDistricts = wsDistricts;
	}


}
