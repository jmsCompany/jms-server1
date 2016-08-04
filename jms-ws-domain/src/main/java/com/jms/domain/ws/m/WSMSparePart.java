package com.jms.domain.ws.m;

public class WSMSparePart {
    private Long idPart;
    private Long idMachine;
    private String machine;
    private Long idMaterial;
    private String material;
    private Long qty;
    private Long safetyQty;
    private Long incoming;
    private Long outgoing;
    private Long idStatus;
    private String status;
    private String remark;
	public Long getIdPart() {
		return idPart;
	}
	public void setIdPart(Long idPart) {
		this.idPart = idPart;
	}
	public Long getIdMachine() {
		return idMachine;
	}
	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public Long getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public Long getSafetyQty() {
		return safetyQty;
	}
	public void setSafetyQty(Long safetyQty) {
		this.safetyQty = safetyQty;
	}
	public Long getIncoming() {
		return incoming;
	}
	public void setIncoming(Long incoming) {
		this.incoming = incoming;
	}
	public Long getOutgoing() {
		return outgoing;
	}
	public void setOutgoing(Long outgoing) {
		this.outgoing = outgoing;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
