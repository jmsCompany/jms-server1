package com.jms.domain.ws.p;


import java.util.Date;


public class WSPlannedMaterialSendingItem {


    private Long woId;
    private String woNo;   //工单
	private String production;//成品
    private Long plannedQty;//计划发料数
    private Date plannedSt;//计划开始时间
    private String shiftD; //班次
    private Long idMachine;
    private String machine; //机台
    private String wip; //车间
    
	private Long idMaterial;
    private String material; //原料
    private String lotNo; //批号
 

    private Long shouldQty; //应发
    private Long qtyDelivered; //已发
    private Long qtyStored; //库存
    private Long qty; //本次发料
    private String bin; //库存货架
  
    private Long bomId;
    private Long cppId;
    private Long invId;
    private Long fromBinId;
    private Long toBinId;
    
    
	public Long getWoId() {
		return woId;
	}
	public void setWoId(Long woId) {
		this.woId = woId;
	}
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	public String getProduction() {
		return production;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public Long getPlannedQty() {
		return plannedQty;
	}
	public void setPlannedQty(Long plannedQty) {
		this.plannedQty = plannedQty;
	}
	public Date getPlannedSt() {
		return plannedSt;
	}
	public void setPlannedSt(Date plannedSt) {
		this.plannedSt = plannedSt;
	}
	public String getShiftD() {
		return shiftD;
	}
	public void setShiftD(String shiftD) {
		this.shiftD = shiftD;
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
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
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
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public Long getShouldQty() {
		return shouldQty;
	}
	public void setShouldQty(Long shouldQty) {
		this.shouldQty = shouldQty;
	}
	public Long getQtyDelivered() {
		return qtyDelivered;
	}
	public void setQtyDelivered(Long qtyDelivered) {
		this.qtyDelivered = qtyDelivered;
	}
	public Long getQtyStored() {
		return qtyStored;
	}
	public void setQtyStored(Long qtyStored) {
		this.qtyStored = qtyStored;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public Long getBomId() {
		return bomId;
	}
	public void setBomId(Long bomId) {
		this.bomId = bomId;
	}
	public Long getCppId() {
		return cppId;
	}
	public void setCppId(Long cppId) {
		this.cppId = cppId;
	}
	public Long getInvId() {
		return invId;
	}
	public void setInvId(Long invId) {
		this.invId = invId;
	}
	public Long getFromBinId() {
		return fromBinId;
	}
	public void setFromBinId(Long fromBinId) {
		this.fromBinId = fromBinId;
	}
	public Long getToBinId() {
		return toBinId;
	}
	public void setToBinId(Long toBinId) {
		this.toBinId = toBinId;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
    



}
