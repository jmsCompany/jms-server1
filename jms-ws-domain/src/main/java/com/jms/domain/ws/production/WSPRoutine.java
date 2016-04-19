package com.jms.domain.ws.production;


import java.util.LinkedHashMap;
import java.util.Map;

public class WSPRoutine {

	private Long idRoutine;
	private String companyName;
	private Long companyId;
	private String pNo;
	private String materialDes;
	private String materialRev;
	private Long drawId;
	private String drawNo;
	private String drawVer;
	private Long lineId;
	private String line;
	private String status;
	private Long statusId;
	private Map<String, WSPRoutineD> wsRoutineDs = new LinkedHashMap<String, WSPRoutineD>(0);

	public Long getIdRoutine() {
		return idRoutine;
	}

	public void setIdRoutine(Long idRoutine) {
		this.idRoutine = idRoutine;
	}

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

	public String getpNo() {
		return pNo;
	}

	public void setpNo(String pNo) {
		this.pNo = pNo;
	}

	public String getMaterialDes() {
		return materialDes;
	}

	public void setMaterialDes(String materialDes) {
		this.materialDes = materialDes;
	}

	public String getMaterialRev() {
		return materialRev;
	}

	public void setMaterialRev(String materialRev) {
		this.materialRev = materialRev;
	}

	public Long getDrawId() {
		return drawId;
	}

	public void setDrawId(Long drawId) {
		this.drawId = drawId;
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

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
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

	public Map<String, WSPRoutineD> getWsRoutineDs() {
		return wsRoutineDs;
	}

	public void setWsRoutineDs(Map<String, WSPRoutineD> wsRoutineDs) {
		this.wsRoutineDs = wsRoutineDs;
	}

}
