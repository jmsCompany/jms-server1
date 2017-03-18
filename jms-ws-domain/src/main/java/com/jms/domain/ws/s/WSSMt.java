package com.jms.domain.ws.s;

import java.util.Date;

public class WSSMt  implements java.io.Serializable {


    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMtNo() {
		return mtNo;
	}
	public void setMtNo(String mtNo) {
		this.mtNo = mtNo;
	}
	public Long getIdCompany1() {
		return idCompany1;
	}
	public void setIdCompany1(Long idCompany1) {
		this.idCompany1 = idCompany1;
	}
	public Long getIdCompany2() {
		return idCompany2;
	}
	public void setIdCompany2(Long idCompany2) {
		this.idCompany2 = idCompany2;
	}
	public String getCompany2() {
		return company2;
	}
	public void setCompany2(String company2) {
		this.company2 = company2;
	}
	public Long getIdCompany3() {
		return idCompany3;
	}
	public void setIdCompany3(Long idCompany3) {
		this.idCompany3 = idCompany3;
	}
	public String getCompany3() {
		return company3;
	}
	public void setCompany3(String company3) {
		this.company3 = company3;
	}
	public Long getFromStk() {
		return fromStk;
	}
	public void setFromStk(Long fromStk) {
		this.fromStk = fromStk;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public String getPNo() {
		return PNo;
	}
	public void setPNo(String pNo) {
		PNo = pNo;
	}
	public Long getPId() {
		return PId;
	}
	public void setPId(Long pId) {
		PId = pId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	private Long id;
    private String mtNo;
    private Long idCompany1;
    private Long idCompany2;
    private String company2;
    private Long idCompany3;
    private String company3;
    private Long fromStk;
    private Date creationDate;
    private Date auditDate;
    private String PNo;
    private Long PId;
    private Long status;
}