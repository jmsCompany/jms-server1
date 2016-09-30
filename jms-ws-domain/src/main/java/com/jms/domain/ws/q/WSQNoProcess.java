package com.jms.domain.ws.q;

import java.util.Date;

public class WSQNoProcess  implements java.io.Serializable{
 
	
	private Long idNoProcess;
    private String ncpNo;
    private String lotNo;
    private Long idMaterial;
    private String material;
    private Float qty;
    private String default_;
    private Long idLocation;
    private String des;
    private Long release1;
    private Long audit01;
    private String comment;
    private String isSorting;
    private Float sortingQty;
    private String isReworking;
    private Float reworkingQty;
    private String isScrap;
    private Float scrapQty;
    private String idReject;
    private Float manHour;
    private String isDeviation;
    private Float deviationQty;
    private Long owner;
    private Long audit02;
    private Long countersign;
    private Long approval02;
    private String inprovementAction;
    private String concernOrNot;
    private Long response;
    private Long audit03;
    private Long approval03;
    private Long idQstatus;
    private Long idCompany;
    
    private Long idDeviation;
    private String deviationNo;
    
    private Long idNcr;
    private String ncrNo;
    
    
    
    private String realease1Name;
    private String ownerName;
    private String audit01Name;
    private String audit02Name;
    private String countersignName;
    private String approval02Name;
    private String responseName;
    private String audit03Name;
    private String approval03Name;
    
    
    private Long idCpp;
    private Long idUnplannedStop;
    
    
    
    private Date time;
    
    
	public Long getIdNoProcess() {
		return idNoProcess;
	}
	public void setIdNoProcess(Long idNoProcess) {
		this.idNoProcess = idNoProcess;
	}
	public String getNcpNo() {
		return ncpNo;
	}
	public void setNcpNo(String ncpNo) {
		this.ncpNo = ncpNo;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public Long getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}
	public Float getQty() {
		return qty;
	}
	public void setQty(Float qty) {
		this.qty = qty;
	}
	public String getDefault_() {
		return default_;
	}
	public void setDefault_(String default_) {
		this.default_ = default_;
	}
	public Long getIdLocation() {
		return idLocation;
	}
	public void setIdLocation(Long idLocation) {
		this.idLocation = idLocation;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getRelease1() {
		return release1;
	}
	public void setRelease1(Long release1) {
		this.release1 = release1;
	}
	public Long getAudit01() {
		return audit01;
	}
	public void setAudit01(Long audit01) {
		this.audit01 = audit01;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getIsSorting() {
		return isSorting;
	}
	public void setIsSorting(String isSorting) {
		this.isSorting = isSorting;
	}
	public Float getSortingQty() {
		return sortingQty;
	}
	public void setSortingQty(Float sortingQty) {
		this.sortingQty = sortingQty;
	}
	public String getIsReworking() {
		return isReworking;
	}
	public void setIsReworking(String isReworking) {
		this.isReworking = isReworking;
	}
	public Float getReworkingQty() {
		return reworkingQty;
	}
	public void setReworkingQty(Float reworkingQty) {
		this.reworkingQty = reworkingQty;
	}
	public String getIsScrap() {
		return isScrap;
	}
	public void setIsScrap(String isScrap) {
		this.isScrap = isScrap;
	}
	public Float getScrapQty() {
		return scrapQty;
	}
	public void setScrapQty(Float scrapQty) {
		this.scrapQty = scrapQty;
	}
	public String getIdReject() {
		return idReject;
	}
	public void setIdReject(String idReject) {
		this.idReject = idReject;
	}
	public Float getManHour() {
		return manHour;
	}
	public void setManHour(Float manHour) {
		this.manHour = manHour;
	}
	public String getIsDeviation() {
		return isDeviation;
	}
	public void setIsDeviation(String isDeviation) {
		this.isDeviation = isDeviation;
	}
	public Float getDeviationQty() {
		return deviationQty;
	}
	public void setDeviationQty(Float deviationQty) {
		this.deviationQty = deviationQty;
	}
	public Long getOwner() {
		return owner;
	}
	public void setOwner(Long owner) {
		this.owner = owner;
	}
	public Long getAudit02() {
		return audit02;
	}
	public void setAudit02(Long audit02) {
		this.audit02 = audit02;
	}
	public Long getCountersign() {
		return countersign;
	}
	public void setCountersign(Long countersign) {
		this.countersign = countersign;
	}
	public Long getApproval02() {
		return approval02;
	}
	public void setApproval02(Long approval02) {
		this.approval02 = approval02;
	}
	public String getInprovementAction() {
		return inprovementAction;
	}
	public void setInprovementAction(String inprovementAction) {
		this.inprovementAction = inprovementAction;
	}
	public String getConcernOrNot() {
		return concernOrNot;
	}
	public void setConcernOrNot(String concernOrNot) {
		this.concernOrNot = concernOrNot;
	}
	public Long getResponse() {
		return response;
	}
	public void setResponse(Long response) {
		this.response = response;
	}
	public Long getAudit03() {
		return audit03;
	}
	public void setAudit03(Long audit03) {
		this.audit03 = audit03;
	}
	public Long getApproval03() {
		return approval03;
	}
	public void setApproval03(Long approval03) {
		this.approval03 = approval03;
	}
	public Long getIdQstatus() {
		return idQstatus;
	}
	public void setIdQstatus(Long idQstatus) {
		this.idQstatus = idQstatus;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Long getIdDeviation() {
		return idDeviation;
	}
	public void setIdDeviation(Long idDeviation) {
		this.idDeviation = idDeviation;
	}
	public String getDeviationNo() {
		return deviationNo;
	}
	public void setDeviationNo(String deviationNo) {
		this.deviationNo = deviationNo;
	}
	public Long getIdNcr() {
		return idNcr;
	}
	public void setIdNcr(Long idNcr) {
		this.idNcr = idNcr;
	}
	public String getNcrNo() {
		return ncrNo;
	}
	public void setNcrNo(String ncrNo) {
		this.ncrNo = ncrNo;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getAudit02Name() {
		return audit02Name;
	}
	public void setAudit02Name(String audit02Name) {
		this.audit02Name = audit02Name;
	}
	public String getCountersignName() {
		return countersignName;
	}
	public void setCountersignName(String countersignName) {
		this.countersignName = countersignName;
	}
	public String getApproval02Name() {
		return approval02Name;
	}
	public void setApproval02Name(String approval02Name) {
		this.approval02Name = approval02Name;
	}
	public String getResponseName() {
		return responseName;
	}
	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}
	public String getAudit03Name() {
		return audit03Name;
	}
	public void setAudit03Name(String audit03Name) {
		this.audit03Name = audit03Name;
	}
	public String getApproval03Name() {
		return approval03Name;
	}
	public void setApproval03Name(String approval03Name) {
		this.approval03Name = approval03Name;
	}
	public String getRealease1Name() {
		return realease1Name;
	}
	public void setRealease1Name(String realease1Name) {
		this.realease1Name = realease1Name;
	}
	public String getAudit01Name() {
		return audit01Name;
	}
	public void setAudit01Name(String audit01Name) {
		this.audit01Name = audit01Name;
	}
	public Long getIdCpp() {
		return idCpp;
	}
	public void setIdCpp(Long idCpp) {
		this.idCpp = idCpp;
	}
	public Long getIdUnplannedStop() {
		return idUnplannedStop;
	}
	public void setIdUnplannedStop(Long idUnplannedStop) {
		this.idUnplannedStop = idUnplannedStop;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
