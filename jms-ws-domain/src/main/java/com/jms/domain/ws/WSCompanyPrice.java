package com.jms.domain.ws;

import java.util.Date;

public class WSCompanyPrice implements java.io.Serializable{
	
	private static final long serialVersionUID = -677955905886087907L;
	private Long id;
	private Long idCocompany;
    private String companyName;
    private Float price;
    private Long isPrim;
    private Float perc;
    private Long days;
    private Float carriage;
    private Long materialId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdCocompany() {
		return idCocompany;
	}
	public void setIdCocompany(Long idCocompany) {
		this.idCocompany = idCocompany;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Long getIsPrim() {
		return isPrim;
	}
	public void setIsPrim(Long isPrim) {
		this.isPrim = isPrim;
	}
	public Float getPerc() {
		return perc;
	}
	public void setPerc(Float perc) {
		this.perc = perc;
	}
	public Long getDays() {
		return days;
	}
	public void setDays(Long days) {
		this.days = days;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Float getCarriage() {
		return carriage;
	}
	public void setCarriage(Float carriage) {
		this.carriage = carriage;
	}  
}
