package com.jms.domain.ws;


public class WSCity {
	
    private int idCity;
    private WSProvince wsProvince;
    private String city;
    private String postcode;
	public int getIdCity() {
		return idCity;
	}
	public void setIdCity(int idCity) {
		this.idCity = idCity;
	}
	public WSProvince getWsProvince() {
		return wsProvince;
	}
	public void setWsProvince(WSProvince wsProvince) {
		this.wsProvince = wsProvince;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

}
