package com.jms.domain.ws;


public class WSCity implements java.io.Serializable{
	
    private Long idCity;
    private WSProvince wsProvince;
    private String city;
    private String postcode;
	public Long getIdCity() {
		return idCity;
	}
	public void setIdCity(Long idCity) {
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
