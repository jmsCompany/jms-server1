package com.jms.domain.ws.q;

import java.util.Date;


public class WSQFileTemplate  implements java.io.Serializable{
  
	private Long idFileTemplate;
    private Long idFileType;
    private String orgFilename;
    private String filename;
    private Long uploadBy;
    private Date creationTime;
    private Long idCompany;
    
    
    private String uploader;
    private String fileType;
    
	public Long getIdFileTemplate() {
		return idFileTemplate;
	}
	public void setIdFileTemplate(Long idFileTemplate) {
		this.idFileTemplate = idFileTemplate;
	}
	public Long getIdFileType() {
		return idFileType;
	}
	public void setIdFileType(Long idFileType) {
		this.idFileType = idFileType;
	}
	public String getOrgFilename() {
		return orgFilename;
	}
	public void setOrgFilename(String orgFilename) {
		this.orgFilename = orgFilename;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Long getUploadBy() {
		return uploadBy;
	}
	public void setUploadBy(Long uploadBy) {
		this.uploadBy = uploadBy;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
