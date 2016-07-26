package com.jms.domain.ws.p;

import java.util.Date;


public class WSPCpp implements java.io.Serializable{


    private Long idCpp;
    private String companyName;
    private Long companyId;
    private String mMachine;
    private Long mMachineId;
    private Long pRoutineDId;
    private String pRoutineD;
    private Long pShiftPlanDId;
    private String pShiftPlanD;

    private Long opId;
    private String op;
    private String pwo;
    private Long pwoId;
    private String cppCode;
    private Long qty;
    private Date planSt;
    private Date planFt;
    
    
	private String line;//产线
	private Long drawNo;//产品图纸编号
	private String drawVer;//产品图纸版本
	
	
	
    private Date actSt;
    private Date actFt;
	
    private Long actQty;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getmMachine() {
		return mMachine;
	}
	public void setmMachine(String mMachine) {
		this.mMachine = mMachine;
	}
	public Long getmMachineId() {
		return mMachineId;
	}
	public void setmMachineId(Long mMachineId) {
		this.mMachineId = mMachineId;
	}
	public Long getpRoutineDId() {
		return pRoutineDId;
	}
	public void setpRoutineDId(Long pRoutineDId) {
		this.pRoutineDId = pRoutineDId;
	}
	public String getpRoutineD() {
		return pRoutineD;
	}
	public void setpRoutineD(String pRoutineD) {
		this.pRoutineD = pRoutineD;
	}
	public String getpShiftPlanD() {
		return pShiftPlanD;
	}
	public void setpShiftPlanD(String pShiftPlanD) {
		this.pShiftPlanD = pShiftPlanD;
	}
	public Long getpShiftPlanDId() {
		return pShiftPlanDId;
	}
	public void setpShiftPlanDId(Long pShiftPlanDId) {
		this.pShiftPlanDId = pShiftPlanDId;
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
	public String getPwo() {
		return pwo;
	}
	public void setPwo(String pwo) {
		this.pwo = pwo;
	}
	public Long getPwoId() {
		return pwoId;
	}
	public void setPwoId(Long pwoId) {
		this.pwoId = pwoId;
	}

	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public Date getPlanSt() {
		return planSt;
	}
	public void setPlanSt(Date planSt) {
		this.planSt = planSt;
	}
	public Date getPlanFt() {
		return planFt;
	}
	public void setPlanFt(Date planFt) {
		this.planFt = planFt;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public Long getDrawNo() {
		return drawNo;
	}
	public void setDrawNo(Long drawNo) {
		this.drawNo = drawNo;
	}
	public String getDrawVer() {
		return drawVer;
	}
	public void setDrawVer(String drawVer) {
		this.drawVer = drawVer;
	}
	public Long getIdCpp() {
		return idCpp;
	}
	public void setIdCpp(Long idCpp) {
		this.idCpp = idCpp;
	}
	public String getCppCode() {
		return cppCode;
	}
	public void setCppCode(String cppCode) {
		this.cppCode = cppCode;
	}
	public Date getActSt() {
		return actSt;
	}
	public void setActSt(Date actSt) {
		this.actSt = actSt;
	}
	public Date getActFt() {
		return actFt;
	}
	public void setActFt(Date actFt) {
		this.actFt = actFt;
	}
	public Long getActQty() {
		return actQty;
	}
	public void setActQty(Long actQty) {
		this.actQty = actQty;
	}
   
}
