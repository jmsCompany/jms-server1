package com.jms.domain.ws;

import java.util.Date;

public class WSCompanyPrice implements java.io.Serializable{
	
	private static final long serialVersionUID = -677955905886087907L;
	private Long id;
	private Long idCocompany;
    private String companyName;
    private Float price;
    private Integer isPrim;
    private Float perc;
    private Integer days;
    private String meth;
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
	public Integer getIsPrim() {
		return isPrim;
	}
	public void setIsPrim(Integer isPrim) {
		this.isPrim = isPrim;
	}
	public Float getPerc() {
		return perc;
	}
	public void setPerc(Float perc) {
		this.perc = perc;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getMeth() {
		return meth;
	}
	public void setMeth(String meth) {
		this.meth = meth;
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
}
