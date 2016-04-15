package com.jms.domain.ws.production;

import java.util.Date;


public class WSPWo {
	
    private Long idWo;
    private Long soId;
    private String so;
    private Long statusId;
    private String status;
    private String woNo;
    private Date creationTime;
    private String creator;
    private Long qty;

	public Long getIdWo() {
		return idWo;
	}
	public void setIdWo(Long idWo) {
		this.idWo = idWo;
	}
	public Long getSoId() {
		return soId;
	}
	public void setSoId(Long soId) {
		this.soId = soId;
	}
	public String getSo() {
		return so;
	}
	public void setSo(String so) {
		this.so = so;
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
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
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
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}


}
