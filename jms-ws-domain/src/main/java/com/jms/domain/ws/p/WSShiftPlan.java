package com.jms.domain.ws.production;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class WSShiftPlan {

	private Long idShiftPlan;
	private Long companyId;
	private String companyName;
	private Long statusId;
	private String status;
	private Long shiftPlanNo;
	private String name;
	private Date validityTime;
	private Date st;
	private Date ft;

   private Map<String,WSShiftPlanD> shifPlanItems = new LinkedHashMap<String,WSShiftPlanD>(0);

	public Long getIdShiftPlan() {
		return idShiftPlan;
	}

	public void setIdShiftPlan(Long idShiftPlan) {
		this.idShiftPlan = idShiftPlan;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public Long getShiftPlanNo() {
		return shiftPlanNo;
	}

	public void setShiftPlanNo(Long shiftPlanNo) {
		this.shiftPlanNo = shiftPlanNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(Date validityTime) {
		this.validityTime = validityTime;
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

	public Map<String,WSShiftPlanD> getShifPlanItems() {
		return shifPlanItems;
	}

	public void setShifPlanItems(Map<String,WSShiftPlanD> shifPlanItems) {
		this.shifPlanItems = shifPlanItems;
	}

}
