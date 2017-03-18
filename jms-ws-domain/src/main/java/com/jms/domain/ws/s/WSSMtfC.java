package com.jms.domain.ws.s;

import java.util.Date;

public class WSSMtfC {

	private Long id;
	private String mtNo;
	private Long idCompany1;
	private Long idCompany2;
	private String pno;
	private String lotNo;
	private String materialName;
	private Date creationDate;
	private Date auditDate;
	private Long qty;
	private Long type;
	private Long status;
	private Long idSmtf1;
	private Long idSmtf2;
	private String remark;
	
	private Long idPoMaterial;

	
	 private Long fromBin;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	private Long materialId;
	
	
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
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getIdSmtf1() {
		return idSmtf1;
	}
	public void setIdSmtf1(Long idSmtf1) {
		this.idSmtf1 = idSmtf1;
	}
	public Long getIdSmtf2() {
		return idSmtf2;
	}
	public void setIdSmtf2(Long idSmtf2) {
		this.idSmtf2 = idSmtf2;
	}
	public Long getIdPoMaterial() {
		return idPoMaterial;
	}
	public void setIdPoMaterial(Long idPoMaterial) {
		this.idPoMaterial = idPoMaterial;
	}
	public Long getFromBin() {
		return fromBin;
	}
	public void setFromBin(Long fromBin) {
		this.fromBin = fromBin;
	}

}
