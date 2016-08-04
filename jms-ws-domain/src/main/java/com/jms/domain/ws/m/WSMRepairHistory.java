package com.jms.domain.ws.m;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class WSMRepairHistory {
    
	private Long idRepairHistory;
    private String machine;
    private Long machineId;
    private Long confirmorId;
    private String confirmor;
    private Long opId;
    private String op;
    private Long repairId;
    private String repair;
    private String status;
    private Long statusId;
    private String maintainer;
    private Long maintainerId;
    private Long repairHistoryNo;
    private Date repairTime;
    private String issueDes;
    private String solution;
    private String suggestion;
    private Date responseTime;
    private Date repairingTime;
    private Date recoverTime;
    private Long idUnplannedStop;
    
    private Map<String,WSMHistoryPart> historyPartItems = new LinkedHashMap<String,WSMHistoryPart>(0);

    public Long getIdRepairHistory() {
		return idRepairHistory;
	}
	public void setIdRepairHistory(Long idRepairHistory) {
		this.idRepairHistory = idRepairHistory;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	public Long getConfirmorId() {
		return confirmorId;
	}
	public void setConfirmorId(Long confirmorId) {
		this.confirmorId = confirmorId;
	}
	public String getConfirmor() {
		return confirmor;
	}
	public void setConfirmor(String confirmor) {
		this.confirmor = confirmor;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public Long getRepairId() {
		return repairId;
	}
	public void setRepairId(Long repairId) {
		this.repairId = repairId;
	}
	public String getRepair() {
		return repair;
	}
	public void setRepair(String repair) {
		this.repair = repair;
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
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public Long getMaintainerId() {
		return maintainerId;
	}
	public void setMaintainerId(Long maintainerId) {
		this.maintainerId = maintainerId;
	}
	public Long getRepairHistoryNo() {
		return repairHistoryNo;
	}
	public void setRepairHistoryNo(Long repairHistoryNo) {
		this.repairHistoryNo = repairHistoryNo;
	}
	public Date getRepairTime() {
		return repairTime;
	}
	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}
	public String getIssueDes() {
		return issueDes;
	}
	public void setIssueDes(String issueDes) {
		this.issueDes = issueDes;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	public Date getRepairingTime() {
		return repairingTime;
	}
	public void setRepairingTime(Date repairingTime) {
		this.repairingTime = repairingTime;
	}
	public Date getRecoverTime() {
		return recoverTime;
	}
	public void setRecoverTime(Date recoverTime) {
		this.recoverTime = recoverTime;
	}
	public Map<String,WSMHistoryPart> getHistoryPartItems() {
		return historyPartItems;
	}
	public void setHistoryPartItems(Map<String,WSMHistoryPart> historyPartItems) {
		this.historyPartItems = historyPartItems;
	}
	public Long getIdUnplannedStop() {
		return idUnplannedStop;
	}
	public void setIdUnplannedStop(Long idUnplannedStop) {
		this.idUnplannedStop = idUnplannedStop;
	}
	
}
