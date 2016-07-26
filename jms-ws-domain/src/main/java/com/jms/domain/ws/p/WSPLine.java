package com.jms.domain.ws.production;


public class WSPLine {
	
    private Long idPline;
    private String pline;
    private Long companyId;
    private String companyName;
    private Long statusId;
    private String status;
 
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getIdPline() {
		return idPline;
	}
	public void setIdPline(Long idPline) {
		this.idPline = idPline;
	}
	public String getPline() {
		return pline;
	}
	public void setPline(String pline) {
		this.pline = pline;
	}
	
}
