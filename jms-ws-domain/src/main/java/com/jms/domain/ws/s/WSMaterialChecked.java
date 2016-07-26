package com.jms.domain.ws.s;

import java.util.Date;

public class WSMaterialChecked {
	
	private Long idMtfMaterial;

    private Long idMaterial;
    private String pno;
    private String rev;
    private String des;

    private String lotNo;
    private Long qty;
    private Long qtyChecked;
    
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
	public Long getQtyChecked() {
		return qtyChecked;
	}
	public void setQtyChecked(Long qtyChecked) {
		this.qtyChecked = qtyChecked;
	}
	public Long getIdMtfMaterial() {
		return idMtfMaterial;
	}
	public void setIdMtfMaterial(Long idMtfMaterial) {
		this.idMtfMaterial = idMtfMaterial;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	

 

}
