package com.jms.domain.ws.p;

import java.math.BigDecimal;


public class WSPBomItem {
	
    private Long idBom;
    private String workCenter;
    private Long workCenterId;
    
    private String material;
    private Long materialId;
    private String pno;
    private String rev;
    private String sUnitDicByUnitInf;
    private Long sUnitDicByUnitInfId;
    private Long weight;
    private BigDecimal cost;
   
    private Long idBomLabel;
    private Float qpu;
    private Long orderBy;
    private Long lvl;
    private Float wastage;
    
    private Long idParentBom;
    
    private Long idRoutineD;
    
    private Long qtyCoPo; //合作公司库存
    
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
	public Float getQpu() {
		return qpu;
	}
	public void setQpu(Float qpu) {
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
	public Float getWastage() {
		return wastage;
	}
	public void setWastage(Float wastage) {
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
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public Long getWeight() {
		return weight;
	}
	public void setWeight(Long weight) {
		this.weight = weight;
	}
	public Long getsUnitDicByUnitInfId() {
		return sUnitDicByUnitInfId;
	}
	public void setsUnitDicByUnitInfId(Long sUnitDicByUnitInfId) {
		this.sUnitDicByUnitInfId = sUnitDicByUnitInfId;
	}
	public String getsUnitDicByUnitInf() {
		return sUnitDicByUnitInf;
	}
	public void setsUnitDicByUnitInf(String sUnitDicByUnitInf) {
		this.sUnitDicByUnitInf = sUnitDicByUnitInf;
	}
	public Long getIdRoutineD() {
		return idRoutineD;
	}
	public void setIdRoutineD(Long idRoutineD) {
		this.idRoutineD = idRoutineD;
	}

	public Long getQtyCoPo() {
		return qtyCoPo;
	}
	public void setQtyCoPo(Long qtyCoPo) {
		this.qtyCoPo = qtyCoPo;
	}

}
