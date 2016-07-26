package com.jms.domain.ws.p;


import java.util.Date;


public class WSPlanMaterialSend {

    private Long idMaterial;
    private String pno;
    private String rev;
    private String des;
    private Long bomId;
    private Long cppId;


    private String remark;
    private Long type;
    private String machine;
    private Long fromBinId;
    private Long fromStkId;
    private Long toBinId;
    private Long toStkId;
    private Long idInventory;
    
    private Long qty; //本次发料
    private Long qtyDelivered; //已发
    private Long qtyStored; //库存

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

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}


	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}


	public Long getQtyDelivered() {
		return qtyDelivered;
	}

	public void setQtyDelivered(Long qtyDelivered) {
		this.qtyDelivered = qtyDelivered;
	}

}
