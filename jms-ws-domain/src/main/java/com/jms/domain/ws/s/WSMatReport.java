package com.jms.domain.ws.s;


public class WSMatReport {

//,"物料",	"库存单位",	"需料日期","完成日期","缺料数量","采购数量",	"状态"
	
	private String material; 
	private String invUnit;
	private String reqDate;
	private String finDate;
	private Float reqQty;
    private Float buyQty;
    private String status;
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getInvUnit() {
		return invUnit;
	}
	public void setInvUnit(String invUnit) {
		this.invUnit = invUnit;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getFinDate() {
		return finDate;
	}
	public void setFinDate(String finDate) {
		this.finDate = finDate;
	}
	public Float getReqQty() {
		return reqQty;
	}
	public void setReqQty(Float reqQty) {
		this.reqQty = reqQty;
	}
	public Float getBuyQty() {
		return buyQty;
	}
	public void setBuyQty(Float buyQty) {
		this.buyQty = buyQty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
   
}
