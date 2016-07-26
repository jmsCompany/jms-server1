package com.jms.domain.ws.store;


public class WSInventory {
	
	
    private Long inventoryId;
    private Long idMaterial;
    private String pno;
    private String rev;
    private String des;
    private Long qty;
    private Long stkId;
    private String stkName;
    private Long binId;
    private String binName;
    private String lotNo;
    private Long UQty;
    private Long box;

    
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
	public Long getBinId() {
		return binId;
	}
	public void setBinId(Long binId) {
		this.binId = binId;
	}
	public String getBinName() {
		return binName;
	}
	public void setBinName(String binName) {
		this.binName = binName;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public Long getUQty() {
		return UQty;
	}
	public void setUQty(Long uQty) {
		UQty = uQty;
	}
	public Long getBox() {
		return box;
	}
	public void setBox(Long box) {
		this.box = box;
	}
	public Long getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

}
