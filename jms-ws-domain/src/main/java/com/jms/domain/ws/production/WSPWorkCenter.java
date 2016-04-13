package com.jms.domain.ws.production;

import java.util.Date;



public class WSPWorkCenter {
	
    private Long idWc;
    private Long companyId;
    private String workCenter;
    private Date creationTime;
    private String creator;
	public Long getIdWc() {
		return idWc;
	}
	public void setIdWc(Long idWc) {
		this.idWc = idWc;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}

}
