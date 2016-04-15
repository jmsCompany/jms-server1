package com.jms.domain.ws.production;


public class WSPBomItem {
	
    private Long idBom;
    private String workCenter;
    private Long workCenterId;
    private String material;
    private Long materialId;
    private String pno;
    private String rev;
    private Long idBomLabel;
    private Long qpu;
    private Long orderBy;
    private Long lvl;
    private Long wastage;
    
    private Long idParentBom;
	public Long getIdBom() {
		return idBom;
	}
	public void setIdBom(Long idBom) {
		this.idBom = idBom;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public Long getWorkCenterId() {
		return workCenterId;
	}
	public void setWorkCenterId(Long workCenterId) {
		this.workCenterId = workCenterId;
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
	public Long getIdBomLabel() {
		return idBomLabel;
	}
	public void setIdBomLabel(Long idBomLabel) {
		this.idBomLabel = idBomLabel;
	}
	public Long getQpu() {
		return qpu;
	}
	public void setQpu(Long qpu) {
		this.qpu = qpu;
	}
	public Long getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}
	public Long getLvl() {
		return lvl;
	}
	public void setLvl(Long lvl) {
		this.lvl = lvl;
	}
	public Long getWastage() {
		return wastage;
	}
	public void setWastage(Long wastage) {
		this.wastage = wastage;
	}
	public Long getIdParentBom() {
		return idParentBom;
	}
	public void setIdParentBom(Long idParentBom) {
		this.idParentBom = idParentBom;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}

}
