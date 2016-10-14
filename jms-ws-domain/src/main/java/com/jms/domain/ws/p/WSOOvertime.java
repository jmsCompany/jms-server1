package com.jms.domain.ws.p;

import java.util.Date;


public class WSOOvertime {
    private Long idOvertime;
    private Long idMachine;
    private Date overtimeStart;
    private Date overtimeEnd;
    private Date creationTime;
    private Long idCompany;
	public Long getIdOvertime() {
		return idOvertime;
	}
	public void setIdOvertime(Long idOvertime) {
		this.idOvertime = idOvertime;
	}
	public Long getIdMachine() {
		return idMachine;
	}
	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}
	public Date getOvertimeStart() {
		return overtimeStart;
	}
	public void setOvertimeStart(Date overtimeStart) {
		this.overtimeStart = overtimeStart;
	}
	public Date getOvertimeEnd() {
		return overtimeEnd;
	}
	public void setOvertimeEnd(Date overtimeEnd) {
		this.overtimeEnd = overtimeEnd;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
   
}
