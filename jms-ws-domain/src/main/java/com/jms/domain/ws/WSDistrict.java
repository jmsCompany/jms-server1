package com.jms.domain.ws;


public class WSDistrict {

    private int idDistrict;
    private WSCity wsCity;
    private String district;
	public int getIdDistrict() {
		return idDistrict;
	}
	public void setIdDistrict(int idDistrict) {
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
