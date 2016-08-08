package com.jms.domain.ws.p;

import java.util.Date;

public class WSOEE {
	
	private Date cppSt; //日期
	private String shiftD; //班次
	private Long woId; //工单
	private String wo;
	private Long routineId; //工艺
	private String routine;
	
	private Long materialId;
	private String material;
	
	private Long routineDId; //工序
	private String routineD;
	private Long machineId; //机台
	private String machine;
	
	private Long planTime; //计划加工时间； cpp中的计划完成时间－计划开始时间， 毫秒级别
	private Long planStopTime; //该Cpp内计划停机时长
	private Long unPlannedStopTime; //该Cpp内非计划停机时长
	private Long loadingTime;//装载时间
	private Long actTime; //实际加工时间
	private Long actQty;//实际生产数量
	private Long machineTime; //理论加工周期
	private Long planQty; //计划生产数量
	private Long passedQty; //合格品数量
	
	private float timeEff; //时间开动率
	private float machineEff;//设备开动率
	private float passedEff; //合格品率
	
	private float oee; //timeEff*machineEff*passedEff

	public Date getCppSt() {
		return cppSt;
	}

	public void setCppSt(Date cppSt) {
		this.cppSt = cppSt;
	}

	public String getShiftD() {
		return shiftD;
	}

	public void setShiftD(String shiftD) {
		this.shiftD = shiftD;
	}

	public Long getWoId() {
		return woId;
	}

	public void setWoId(Long woId) {
		this.woId = woId;
	}

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
	}

	public Long getRoutineId() {
		return routineId;
	}

	public void setRoutineId(Long routineId) {
		this.routineId = routineId;
	}

	public String getRoutine() {
		return routine;
	}

	public void setRoutine(String routine) {
		this.routine = routine;
	}

	public Long getRoutineDId() {
		return routineDId;
	}

	public void setRoutineDId(Long routineDId) {
		this.routineDId = routineDId;
	}

	public String getRoutineD() {
		return routineD;
	}

	public void setRoutineD(String routineD) {
		this.routineD = routineD;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public Long getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Long planTime) {
		this.planTime = planTime;
	}

	public Long getPlanStopTime() {
		return planStopTime;
	}

	public void setPlanStopTime(Long planStopTime) {
		this.planStopTime = planStopTime;
	}

	public Long getUnPlannedStopTime() {
		return unPlannedStopTime;
	}

	public void setUnPlannedStopTime(Long unPlannedStopTime) {
		this.unPlannedStopTime = unPlannedStopTime;
	}

	public Long getLoadingTime() {
		return loadingTime;
	}

	public void setLoadingTime(Long loadingTime) {
		this.loadingTime = loadingTime;
	}

	public Long getActTime() {
		return actTime;
	}

	public void setActTime(Long actTime) {
		this.actTime = actTime;
	}

	public Long getActQty() {
		return actQty;
	}

	public void setActQty(Long actQty) {
		this.actQty = actQty;
	}

	public Long getMachineTime() {
		return machineTime;
	}

	public void setMachineTime(Long machineTime) {
		this.machineTime = machineTime;
	}

	public Long getPlanQty() {
		return planQty;
	}

	public void setPlanQty(Long planQty) {
		this.planQty = planQty;
	}

	public Long getPassedQty() {
		return passedQty;
	}

	public void setPassedQty(Long passedQty) {
		this.passedQty = passedQty;
	}

	public float getTimeEff() {
		return timeEff;
	}

	public void setTimeEff(float timeEff) {
		this.timeEff = timeEff;
	}

	public float getMachineEff() {
		return machineEff;
	}

	public void setMachineEff(float machineEff) {
		this.machineEff = machineEff;
	}

	public float getPassedEff() {
		return passedEff;
	}

	public void setPassedEff(float passedEff) {
		this.passedEff = passedEff;
	}

	public float getOee() {
		return oee;
	}

	public void setOee(float oee) {
		this.oee = oee;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
   
}
