package com.jms.domain.ws.p;

import java.util.Date;



public class WSShiftPlanD {

    private Long idShiftD;
    private Long shiftPlanId;
    private String shift;
    private String des;
    private String st;
    private String ft;
	
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
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public String getFt() {
		return ft;
	}
	public void setFt(String ft) {
		this.ft = ft;
	}
}
