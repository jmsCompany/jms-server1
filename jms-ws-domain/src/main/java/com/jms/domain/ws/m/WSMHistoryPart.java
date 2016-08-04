package com.jms.domain.ws.m;


public class WSMHistoryPart {
    
	private Long idHistoryPart;
    private String material;
    private Long materialId;
    private Long repairHistoryId;
    private Long qty;
	public Long getIdHistoryPart() {
		return idHistoryPart;
	}
	public void setIdHistoryPart(Long idHistoryPart) {
		this.idHistoryPart = idHistoryPart;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Long getRepairHistoryId() {
		return repairHistoryId;
	}
	public void setRepairHistoryId(Long repairHistoryId) {
		this.repairHistoryId = repairHistoryId;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
}
