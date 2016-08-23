package com.jms.domain.ws.m;

import java.math.BigDecimal;
import java.util.Date;


public class WSMachine {
	
    private Long idMachine;
    private String code;
    private String name;
    private String spec;
    private Long totalKwa;
    private Date satCompletedDate;
    
    private Long idGroup;
    private String group;
    
    private Long idLocation;
    private String location;
    
    private Long idLine;
    private String line;
    
    private Long idWip;
    private String wip;
    
    private Long idWc;
    private String wc;
    
    private Long idStatus;
    private String status;
    
    private Long idStk;
    private String stk;
    
    private  BigDecimal mttr;
    private  BigDecimal mtbf;
    private  BigDecimal downtime;
 
    
	public Long getIdMachine() {
		return idMachine;
	}
	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Long getTotalKwa() {
		return totalKwa;
	}
	public void setTotalKwa(Long totalKwa) {
		this.totalKwa = totalKwa;
	}
	public Date getSatCompletedDate() {
		return satCompletedDate;
	}
	public void setSatCompletedDate(Date satCompletedDate) {
		this.satCompletedDate = satCompletedDate;
	}
	public Long getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Long getIdLocation() {
		return idLocation;
	}
	public void setIdLocation(Long idLocation) {
		this.idLocation = idLocation;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getIdLine() {
		return idLine;
	}
	public void setIdLine(Long idLine) {
		this.idLine = idLine;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public Long getIdWip() {
		return idWip;
	}
	public void setIdWip(Long idWip) {
		this.idWip = idWip;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public Long getIdWc() {
		return idWc;
	}
	public void setIdWc(Long idWc) {
		this.idWc = idWc;
	}
	public String getWc() {
		return wc;
	}
	public void setWc(String wc) {
		this.wc = wc;
	}
	public Long getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStk() {
		return stk;
	}
	public void setStk(String stk) {
		this.stk = stk;
	}
	public Long getIdStk() {
		return idStk;
	}
	public void setIdStk(Long idStk) {
		this.idStk = idStk;
	}
	public BigDecimal getMttr() {
		return mttr;
	}
	public void setMttr(BigDecimal mttr) {
		this.mttr = mttr;
	}
	public BigDecimal getMtbf() {
		return mtbf;
	}
	public void setMtbf(BigDecimal mtbf) {
		this.mtbf = mtbf;
	}
	public BigDecimal getDowntime() {
		return downtime;
	}
	public void setDowntime(BigDecimal downtime) {
		this.downtime = downtime;
	}

}
