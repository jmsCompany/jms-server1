package com.jms.domain.ws.q;

import java.util.Date;

public class WSQFileManagent  implements java.io.Serializable{

	private Long idFile;
    private Long idFileType;
    private String fileNo;
    private Long idRoutineD;
    private Long creator;
    private Date creationTime;
    private String orgFilename;
    private String fileName;
    private Long uploadByUser;
    private Long idCompany;
    private Long idWo;
    private Long idMaterial;
    
    private String routineD;
    private String material;
    private String fileType;
    private String creatorName;
    
    private String woNo;
    
	public Long getIdFile() {
		return idFile;
	}
	public void setIdFile(Long idFile) {
		this.idFile = idFile;
	}
	public Long getIdFileType() {
		return idFileType;
	}
	public void setIdFileType(Long idFileType) {
		this.idFileType = idFileType;
	}
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public Long getIdRoutineD() {
		return idRoutineD;
	}
	public void setIdRoutineD(Long idRoutineD) {
		this.idRoutineD = idRoutineD;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public String getOrgFilename() {
		return orgFilename;
	}
	public void setOrgFilename(String orgFilename) {
		this.orgFilename = orgFilename;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getUploadByUser() {
		return uploadByUser;
	}
	public void setUploadByUser(Long uploadByUser) {
		this.uploadByUser = uploadByUser;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	public Long getIdWo() {
		return idWo;
	}
	public void setIdWo(Long idWo) {
		this.idWo = idWo;
	}
	public Long getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}
	public String getRoutineD() {
		return routineD;
	}
	public void setRoutineD(String routineD) {
		this.routineD = routineD;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
}
