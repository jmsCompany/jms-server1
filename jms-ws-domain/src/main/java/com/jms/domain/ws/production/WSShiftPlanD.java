package com.jms.domain.ws.production;

import java.util.Date;



public class WSShiftPlanD {

    private Long idShiftD;
    private Long shiftPlanId;
    private String shift;
    private String des;
    private Date st;
    private Date ft;
	
    public Long getIdShiftD() {
		return idShiftD;
	}
	public void setIdShiftD(Long idShiftD) {
		this.idShiftD = idShiftD;
	}
	public Long getShiftPlanId() {
		return shiftPlanId;
	}
	public void setShiftPlanId(Long shiftPlanId) {
		this.shiftPlanId = shiftPlanId;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
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