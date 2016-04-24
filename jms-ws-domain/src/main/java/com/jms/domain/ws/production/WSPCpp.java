package com.jms.domain.ws.production;


//排班计划
public class WSPCpp implements java.io.Serializable{
	
	private String woNo; //工单号
	private String pNo; //产品号
	private int qty;//需要完成的数量 
	private String op;//操作工人
	private String shift;//班次
	private int line;//产线
	private String drawNo;//产品图纸编号
	private int drawVer;//产品图纸版本
	private String route;//工艺
	private String mNo;//机器号
	private String stdWtLabor;//人力标准工时
	private String stdWtMachine;//机器标准工时
	private String stdWtSetup;//装载标准工时
	private String category;//工种
	private String checklistId;//检查清单
	private String wiId;//用户使用手册
	private String drawId;//产品图纸
	private String routeId;//工艺图纸
	private Long st;//计划开始时间
	private Long ft;//计划结束时间
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	public String getpNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getDrawNo() {
		return drawNo;
	}
	public void setDrawNo(String drawNo) {
		this.drawNo = drawNo;
	}
	public int getDrawVer() {
		return drawVer;
	}
	public void setDrawVer(int drawVer) {
		this.drawVer = drawVer;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getmNo() {
		return mNo;
	}
	public void setmNo(String mNo) {
		this.mNo = mNo;
	}
	public String getStdWtLabor() {
		return stdWtLabor;
	}
	public void setStdWtLabor(String stdWtLabor) {
		this.stdWtLabor = stdWtLabor;
	}
	public String getStdWtMachine() {
		return stdWtMachine;
	}
	public void setStdWtMachine(String stdWtMachine) {
		this.stdWtMachine = stdWtMachine;
	}
	public String getStdWtSetup() {
		return stdWtSetup;
	}
	public void setStdWtSetup(String stdWtSetup) {
		this.stdWtSetup = stdWtSetup;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getChecklistId() {
		return checklistId;
	}
	public void setChecklistId(String checklistId) {
		this.checklistId = checklistId;
	}
	public String getWiId() {
		return wiId;
	}
	public void setWiId(String wiId) {
		this.wiId = wiId;
	}
	public Long getSt() {
		return st;
	}
	public void setSt(Long st) {
		this.st = st;
	}
	public Long getFt() {
		return ft;
	}
	public void setFt(Long ft) {
		this.ft = ft;
	}
	public String getDrawId() {
		return drawId;
	}
	public void setDrawId(String drawId) {
		this.drawId = drawId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	
   
}
