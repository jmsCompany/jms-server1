package com.jms.domain.ws.ehs;

public class WSEHSItem{


    private Long idEhs;
    private String des;
    private Long type;
    private String stype; //0重要，1不重要
    private Long idCompany;
    
	public Long getIdEhs() {
		return idEhs;
	}
	public void setIdEhs(Long idEhs) {
		this.idEhs = idEhs;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}

	   
}
