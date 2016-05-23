package com.jms.domain.ws.production;

import java.util.Date;

public class WSPUnplannedStops {
	
	private Long idUnplannedStops;
	private Long pSubCodeId;
	private String pSubCode;
	private Long statusId;
	private String status;
	private Long unplannedStopsNo;
	private Long idMachine;
	private String opDes;
	private Date unplannedSt;
	private String reason;
	private String reaction;
	private Date opSt;
	private Date eqSt;
	private Date eqFt;
	private Date opFt;

	private Long idCpp;
	
	public Long getIdUnplannedStops() {
		return idUnplannedStops;
	}

	public void setIdUnplannedStops(Long idUnplannedStops) {
		this.idUnplannedStops = idUnplannedStops;
	}

	public Long getpSubCodeId() {
		return pSubCodeId;
	}

	public void setpSubCodeId(Long pSubCodeId) {
		this.pSubCodeId = pSubCodeId;
	}

	public String getpSubCode() {
		return pSubCode;
	}

	public void setpSubCode(String pSubCode) {
		this.pSubCode = pSubCode;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	public Long getUnplannedStopsNo() {
		return unplannedStopsNo;
	}

	public void setUnplannedStopsNo(Long unplannedStopsNo) {
		this.unplannedStopsNo = unplannedStopsNo;
	}

	public Long getIdMachine() {
		return idMachine;
	}

	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}

	public String getOpDes() {
		return opDes;
	}

	public void setOpDes(String opDes) {
		this.opDes = opDes;
	}

	public Date getUnplannedSt() {
		return unplannedSt;
	}

	public void setUnplannedSt(Date unplannedSt) {
		this.unplannedSt = unplannedSt;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReaction() {
		return reaction;
	}

	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	public Date getOpSt() {
		return opSt;
	}

	public void setOpSt(Date opSt) {
		this.opSt = opSt;
	}

	public Date getEqSt() {
		return eqSt;
	}

	public void setEqSt(Date eqSt) {
		this.eqSt = eqSt;
	}

	public Date getEqFt() {
		return eqFt;
	}

	public void setEqFt(Date eqFt) {
		this.eqFt = eqFt;
	}

	public Date getOpFt() {
		return opFt;
	}

	public void setOpFt(Date opFt) {
		this.opFt = opFt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getIdCpp() {
		return idCpp;
	}

	public void setIdCpp(Long idCpp) {
		this.idCpp = idCpp;
	}

}
