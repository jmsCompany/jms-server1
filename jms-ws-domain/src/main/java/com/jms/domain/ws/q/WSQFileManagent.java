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
}
