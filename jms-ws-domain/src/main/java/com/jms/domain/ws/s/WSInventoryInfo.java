package com.jms.domain.ws.s;


public class WSInventoryInfo {
	
	
    private Long inventoryId;
    private Long idMaterial;
    private String pno;
    private String rev;
    private String des;
    private Long qty;
    private Long stkId;
    private String stkName;

    
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
	public Long getStkId() {
		return stkId;
	}
	public void setStkId(Long stkId) {
		this.stkId = stkId;
	}
	public String getStkName() {
		return stkName;
	}
	public void setStkName(String stkName) {
		this.stkName = stkName;
	}
	public Long getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

}
