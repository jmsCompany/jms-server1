package com.jms.domain.ws.s;

public class WSMaterialQty {


    private Long idMaterial;
    private String pno;
    private String rev;
    private String des;
    private Long bomId;
    private Long cppId;
    private Long qty;

    public Long getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public Long getBomId() {
		return bomId;
	}
	public void setBomId(Long bomId) {
		this.bomId = bomId;
	}
	public Long getCppId() {
		return cppId;
	}
	public void setCppId(Long cppId) {
		this.cppId = cppId;
	}

}
