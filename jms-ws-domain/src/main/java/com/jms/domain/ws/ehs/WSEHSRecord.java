package com.jms.domain.ws.ehs;

import java.util.Date;


public class WSEHSRecord{

    private Long idEhsRecord;
    private String ehsItem;
    private Long idEhsItem;
    private Date date;
    private Long idOp;
    private Long idShiftD;
    private Date creationTime;
    private Long idSup;
    private String remark;
    private Date supTime;
    private Long status;
    private String sstatus;//0未处理，1已处理
    private Long idCompany;
    
    
	public Long getIdEhsRecord() {
		return idEhsRecord;
	}
	public void setIdEhsRecord(Long idEhsRecord) {
		this.idEhsRecord = idEhsRecord;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getIdOp() {
		return idOp;
	}
	public void setIdOp(Long idOp) {
		this.idOp = idOp;
	}
	public Long getIdShiftD() {
		return idShiftD;
	}
	public void setIdShiftD(Long idShiftD) {
		this.idShiftD = idShiftD;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Long getIdSup() {
		return idSup;
	}
	public void setIdSup(Long idSup) {
		this.idSup = idSup;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getSupTime() {
		return supTime;
	}
	public void setSupTime(Date supTime) {
		this.supTime = supTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getSstatus() {
		return sstatus;
	}
	public void setSstatus(String sstatus) {
		this.sstatus = sstatus;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	public String getEhsItem() {
		return ehsItem;
	}
	public void setEhsItem(String ehsItem) {
		this.ehsItem = ehsItem;
	}
	public Long getIdEhsItem() {
		return idEhsItem;
	}
	public void setIdEhsItem(Long idEhsItem) {
		this.idEhsItem = idEhsItem;
	}

	   
}
