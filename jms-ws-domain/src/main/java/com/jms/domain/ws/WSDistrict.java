package com.jms.domain.ws;


public class WSDistrict implements java.io.Serializable{

    private Long idDistrict;
    private WSCity wsCity;
    private String district;
	public Long getIdDistrict() {
		return idDistrict;
	}
	public void setIdDistrict(Long idDistrict) {
		this.idDistrict = idDistrict;
	}
	public WSCity getWsCity() {
		return wsCity;
	}
	public void setWsCity(WSCity wsCity) {
		this.wsCity = wsCity;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
}
