package com.jms.domain.ws.p;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class WSPBom {
	
    private Long idBomLabel;
    private Long companyId;
    private String companyName;
    private String creator;
    private Long statusId;
    private String status;
    private Date creationTime;
    private Long materialId;
    private String material;
    private String pno;
    private String rev;
    private boolean saved;
    private boolean edit;
    
    private Map<String,WSPBomItem> bomItems = new LinkedHashMap<String,WSPBomItem>(0);

    public Long getIdBomLabel() {
		return idBomLabel;
	}
	public void setIdBomLabel(Long idBomLabel) {
		this.idBomLabel = idBomLabel;
	}
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
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
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Map<String, WSPBomItem> getBomItems() {
		return bomItems;
	}
	public void setBomItems(Map<String, WSPBomItem> bomItems) {
		this.bomItems = bomItems;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}

}
