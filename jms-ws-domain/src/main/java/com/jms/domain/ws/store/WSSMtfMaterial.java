package com.jms.domain.ws.store;

import java.util.Date;

public class WSSMtfMaterial {

    private Long idMtfMaterial;
    private String toBin;
    private Long toBinId;
    
    private String fromBin;
    private Long fromBinId;
    
    private String soCode;
    private Long soId;
    
    private Long poMaterialId;
    private Long materialId;
    private String materialPno;
    private String materialRev;
    private String materialDes;
    private String marterialUnit;
    private String codePo;
    private Long qtyPo;
    private Date deliveryDate;
    private Long qtyReceived;
   
    
    private String status;
    private Long statusId;
    
    
    private String pwoBom;
    private Long pwoBomId;
    private String woNo;
   


    private String lotNo;
    private Long uqty;
    private Long box;
    private String remark;
    private Long qty;
	
    
    
    private Long idMt;
    private String type;
    private Long typeId;
    private String company;
    private Long companyId;
    private Long toStkId;
    private String ToStk;
    private Long fromStkId;
    private String fromStk;
    private Long mtfStatusId;
    private String mtfStatus;
    private String empMtUser;
    private Long empMtUserId;
    private String recMtUser;
    private Long recMtUserId;
    private String mtNo;
    private Date dateMt;
    private Date creationTime;
    
    
    private String codeCo;
    
    
    
    
	public Long getIdMtfMaterial() {
		return idMtfMaterial;
	}
	public void setIdMtfMaterial(Long idMtfMaterial) {
		this.idMtfMaterial = idMtfMaterial;
	}
	public String getToBin() {
		return toBin;
	}
	public void setToBin(String toBin) {
		this.toBin = toBin;
	}
	public Long getToBinId() {
		return toBinId;
	}
	public void setToBinId(Long toBinId) {
		this.toBinId = toBinId;
	}
	public String getFromBin() {
		return fromBin;
	}
	public void setFromBin(String fromBin) {
		this.fromBin = fromBin;
	}
	public Long getFromBinId() {
		return fromBinId;
	}
	public void setFromBinId(Long fromBinId) {
		this.fromBinId = fromBinId;
	}
	public String getSoCode() {
		return soCode;
	}
	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}
	public Long getSoId() {
		return soId;
	}
	public void setSoId(Long soId) {
		this.soId = soId;
	}
	public Long getPoMaterialId() {
		return poMaterialId;
	}
	public void setPoMaterialId(Long poMaterialId) {
		this.poMaterialId = poMaterialId;
	}
	public String getMaterialPno() {
		return materialPno;
	}
	public void setMaterialPno(String materialPno) {
		this.materialPno = materialPno;
	}
	public String getMaterialRev() {
		return materialRev;
	}
	public void setMaterialRev(String materialRev) {
		this.materialRev = materialRev;
	}
	public String getMaterialDes() {
		return materialDes;
	}
	public void setMaterialDes(String materialDes) {
		this.materialDes = materialDes;
	}
	public String getMarterialUnit() {
		return marterialUnit;
	}
	public void setMarterialUnit(String marterialUnit) {
		this.marterialUnit = marterialUnit;
	}
	public Long getQtyPo() {
		return qtyPo;
	}
	public void setQtyPo(Long qtyPo) {
		this.qtyPo = qtyPo;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Long getQtyReceived() {
		return qtyReceived;
	}
	public void setQtyReceived(Long qtyReceived) {
		this.qtyReceived = qtyReceived;
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
	public String getPwoBom() {
		return pwoBom;
	}
	public void setPwoBom(String pwoBom) {
		this.pwoBom = pwoBom;
	}
	public Long getPwoBomId() {
		return pwoBomId;
	}
	public void setPwoBomId(Long pwoBomId) {
		this.pwoBomId = pwoBomId;
	}
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public Long getUqty() {
		return uqty;
	}
	public void setUqty(Long uqty) {
		this.uqty = uqty;
	}
	public Long getBox() {
		return box;
	}
	public void setBox(Long box) {
		this.box = box;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public Long getIdMt() {
		return idMt;
	}
	public void setIdMt(Long idMt) {
		this.idMt = idMt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getToStk() {
		return ToStk;
	}
	public void setToStk(String toStk) {
		ToStk = toStk;
	}
	public Long getFromStkId() {
		return fromStkId;
	}
	public void setFromStkId(Long fromStkId) {
		this.fromStkId = fromStkId;
	}
	public String getFromStk() {
		return fromStk;
	}
	public void setFromStk(String fromStk) {
		this.fromStk = fromStk;
	}
	public Long getMtfStatusId() {
		return mtfStatusId;
	}
	public void setMtfStatusId(Long mtfStatusId) {
		this.mtfStatusId = mtfStatusId;
	}
	public String getMtfStatus() {
		return mtfStatus;
	}
	public void setMtfStatus(String mtfStatus) {
		this.mtfStatus = mtfStatus;
	}
	public String getEmpMtUser() {
		return empMtUser;
	}
	public void setEmpMtUser(String empMtUser) {
		this.empMtUser = empMtUser;
	}
	public Long getEmpMtUserId() {
		return empMtUserId;
	}
	public void setEmpMtUserId(Long empMtUserId) {
		this.empMtUserId = empMtUserId;
	}
	public String getRecMtUser() {
		return recMtUser;
	}
	public void setRecMtUser(String recMtUser) {
		this.recMtUser = recMtUser;
	}
	public Long getRecMtUserId() {
		return recMtUserId;
	}
	public void setRecMtUserId(Long recMtUserId) {
		this.recMtUserId = recMtUserId;
	}
	public String getMtNo() {
		return mtNo;
	}
	public void setMtNo(String mtNo) {
		this.mtNo = mtNo;
	}
	public Date getDateMt() {
		return dateMt;
	}
	public void setDateMt(Date dateMt) {
		this.dateMt = dateMt;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getCodeCo() {
		return codeCo;
	}
	public void setCodeCo(String codeCo) {
		this.codeCo = codeCo;
	}
	public String getCodePo() {
		return codePo;
	}
	public void setCodePo(String codePo) {
		this.codePo = codePo;
	}
	public Long getToStkId() {
		return toStkId;
	}
	public void setToStkId(Long toStkId) {
		this.toStkId = toStkId;
	}
    
  
   
}
