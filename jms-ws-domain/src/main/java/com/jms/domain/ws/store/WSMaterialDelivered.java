package com.jms.domain.ws.store;

import java.util.Date;

public class WSMaterialDelivered {

    private Long idMaterial;
    private String pno;
    private String rev;
    private String des;
    private String unitInv;
    private Long qtySo;
    private Long qtyDelivered;
    private Date deliveredDate;
    
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
	public String getUnitInv() {
		return unitInv;
	}
	public void setUnitInv(String unitInv) {
		this.unitInv = unitInv;
	}
	public Long getQtySo() {
		return qtySo;
	}
	public void setQtySo(Long qtySo) {
		this.qtySo = qtySo;
	}

	public Date getDeliveredDate() {
		return deliveredDate;
	}
	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}
	public Long getQtyDelivered() {
		return qtyDelivered;
	}
	public void setQtyDelivered(Long qtyDelivered) {
		this.qtyDelivered = qtyDelivered;
	}
 

}
