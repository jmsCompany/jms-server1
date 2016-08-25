package com.jms.domain.ws.q;

public class WSQFileManagent  implements java.io.Serializable{

    private Long idFile;
    private Long idFileType;
    private String fileNo;
    private Long idRoutineD;
    private Long creator;
    private String creationTime;
    private String orgFilename;
    private String fileName;
    private Long uploadByUser;
    private Long idCompany;
    private Long idWo;
    
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
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
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
}
