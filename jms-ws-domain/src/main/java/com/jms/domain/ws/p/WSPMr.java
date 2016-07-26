package com.jms.domain.ws.p;


import java.util.Date;


public class WSPMr {

	private Long idMr;
    private Long idMaterial;
    private String pno;
    private String rev;
    private String des;
    private Long bomId;
    private Long cppId;
    private Long qty; //本次发料
    private Long statusId;
    private String status;
    private Date st;
    private Date ft;
    private String remark;
    private Long type;
    private String op;
    private String machine;
    private Long idUnplannedStop;
    private Long binId;
    private Long stkId;
    
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

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSt() {
		return st;
	}

	public void setSt(Date st) {
		this.st = st;
	}

	public Date getFt() {
		return ft;
	}

	public void setFt(Date ft) {
		this.ft = ft;
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

	public Long getIdMr() {
		return idMr;
	}

	public void setIdMr(Long idMr) {
		this.idMr = idMr;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public Long getBinId() {
		return binId;
	}

	public void setBinId(Long binId) {
		this.binId = binId;
	}

	public Long getIdUnplannedStop() {
		return idUnplannedStop;
	}

	public void setIdUnplannedStop(Long idUnplannedStop) {
		this.idUnplannedStop = idUnplannedStop;
	}

	public Long getStkId() {
		return stkId;
	}

	public void setStkId(Long stkId) {
		this.stkId = stkId;
	}

	public Long getQtyDelivered() {
		return qtyDelivered;
	}

	public void setQtyDelivered(Long qtyDelivered) {
		this.qtyDelivered = qtyDelivered;
	}

}
