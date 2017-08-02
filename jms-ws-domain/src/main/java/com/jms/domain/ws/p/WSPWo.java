package com.jms.domain.ws.p;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WSPWo {
	
    private Long idWo;
    private Long soId;
    private String so;
    private Long statusId;
    private String status;
    private String woNo;
    private Date creationTime;
    private String creator;
    private Long qty;
    
    private String pno;
    private String rev;
    private String des;
    private Long materialId;
    
    
    private Long qtyFinished;
    
    private Date actSt;
    private Date actFt;
    private Long actQty;
    
    private Long typeId;
    private String orgWo;
    
    private Date st;
    private Date ft;
    
    List<WSPRoutineD> routines = new ArrayList<WSPRoutineD>();

	public Long getIdWo() {
		return idWo;
	}
	public void setIdWo(Long idWo) {
		this.idWo = idWo;
	}
	public Long getSoId() {
		return soId;
	}
	public void setSoId(Long soId) {
		this.soId = soId;
	}
	public String getSo() {
		return so;
	}
	public void setSo(String so) {
		this.so = so;
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
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
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
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public List<WSPRoutineD> getRoutines() {
		return routines;
	}
	public void setRoutines(List<WSPRoutineD> routines) {
		this.routines = routines;
	}
	public Long getQtyFinished() {
		return qtyFinished;
	}
	public void setQtyFinished(Long qtyFinished) {
		this.qtyFinished = qtyFinished;
	}
	public Date getActSt() {
		return actSt;
	}
	public void setActSt(Date actSt) {
		this.actSt = actSt;
	}
	public Date getActFt() {
		return actFt;
	}
	public void setActFt(Date actFt) {
		this.actFt = actFt;
	}
	public Long getActQty() {
		return actQty;
	}
	public void setActQty(Long actQty) {
		this.actQty = actQty;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getOrgWo() {
		return orgWo;
	}
	public void setOrgWo(String orgWo) {
		this.orgWo = orgWo;
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


}
