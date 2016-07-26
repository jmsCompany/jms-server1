package com.jms.domain.ws.production;

import java.util.Date;


public class WSPStopsPlan {

    private Long idStopsPlan;
    private String pSubCode;
    private Long pSubCodeId;
    private String companyName;
    private Long companyId;
    private Long machineId;
    private String machineCode;
    private Long stopsPlanNo;
    private Date planSt;
    private Date planFt;
    private Date actSt;
    private Date actFt;
    
    private Long statusId;
    private String status;
    
	public Long getIdStopsPlan() {
		return idStopsPlan;
	}
	public void setIdStopsPlan(Long idStopsPlan) {
		this.idStopsPlan = idStopsPlan;
	}
	public String getpSubCode() {
		return pSubCode;
	}
	public void setpSubCode(String pSubCode) {
		this.pSubCode = pSubCode;
	}
	public Long getpSubCodeId() {
		return pSubCodeId;
	}
	public void setpSubCodeId(Long pSubCodeId) {
		this.pSubCodeId = pSubCodeId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public Long getStopsPlanNo() {
		return stopsPlanNo;
	}
	public void setStopsPlanNo(Long stopsPlanNo) {
		this.stopsPlanNo = stopsPlanNo;
	}
	public Date getPlanSt() {
		return planSt;
	}
	public void setPlanSt(Date planSt) {
		this.planSt = planSt;
	}
	public Date getPlanFt() {
		return planFt;
	}
	public void setPlanFt(Date planFt) {
		this.planFt = planFt;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

}
