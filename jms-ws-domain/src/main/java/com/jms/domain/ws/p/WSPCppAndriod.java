package com.jms.domain.ws.p;

import java.util.ArrayList;
import java.util.List;

import com.jms.domain.ws.WSFileMeta;

//排班计划
public class WSPCppAndriod implements java.io.Serializable{
	
	
	private Long cppId; //日计划ID
	private String woNo; //工单号
	private String pNo; //产品号
	private Long qty;//需要完成的数量 
	private String op;//操作工人
	private String shift;//班次
	private String line;//产线
	private String drawNo;//产品图纸编号
	private String drawVer;//产品图纸版本
	private String route;//工艺
	private String mNo;//机器编码
	
	private Long idMachine; //机器ID
	private Float stdWtLabor;//人力标准工时
	private Float stdWtMachine;//机器标准工时
	private Float stdWtSetup;//装载标准工时

	private String checklistId;//检查清单
	private String wiId;//用户使用手册
	//private String drawId;//产品图纸
	private Long st;//计划开始时间
	private Long ft;//计划结束时间
	
	
	private Long actSt;//开始时间
	private Long actFt;//结束时间
	
	
	private Long checkInterval; //
	private Long checkIntervalType; //1分钟，2小时，3天
	
	
	private String routeDes;
	
	
	private List<String> categories=new ArrayList<String>();//工种
	private List<WSFileMeta> files = new ArrayList<WSFileMeta>();
	
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
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
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
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getDrawNo() {
		return drawNo;
	}
	public void setDrawNo(String drawNo) {
		this.drawNo = drawNo;
	}
	public String getDrawVer() {
		return drawVer;
	}
	public void setDrawVer(String drawVer) {
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
	public Float getStdWtLabor() {
		return stdWtLabor;
	}
	public void setStdWtLabor(Float stdWtLabor) {
		this.stdWtLabor = stdWtLabor;
	}
	public Float getStdWtMachine() {
		return stdWtMachine;
	}
	public void setStdWtMachine(Float stdWtMachine) {
		this.stdWtMachine = stdWtMachine;
	}
	public Float getStdWtSetup() {
		return stdWtSetup;
	}
	public void setStdWtSetup(Float stdWtSetup) {
		this.stdWtSetup = stdWtSetup;
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

	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<WSFileMeta> getFiles() {
		return files;
	}
	public void setFiles(List<WSFileMeta> files) {
		this.files = files;
	}
	public Long getCheckInterval() {
		return checkInterval;
	}
	public void setCheckInterval(Long checkInterval) {
		this.checkInterval = checkInterval;
	}
	public Long getCheckIntervalType() {
		return checkIntervalType;
	}
	public void setCheckIntervalType(Long checkIntervalType) {
		this.checkIntervalType = checkIntervalType;
	}
	public Long getCppId() {
		return cppId;
	}
	public void setCppId(Long cppId) {
		this.cppId = cppId;
	}
	public Long getIdMachine() {
		return idMachine;
	}
	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}
	public Long getActSt() {
		return actSt;
	}
	public void setActSt(Long actSt) {
		this.actSt = actSt;
	}
	public Long getActFt() {
		return actFt;
	}
	public void setActFt(Long actFt) {
		this.actFt = actFt;
	}
	public String getRouteDes() {
		return routeDes;
	}
	public void setRouteDes(String routeDes) {
		this.routeDes = routeDes;
	}
	
   
}
