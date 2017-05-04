package com.jms.domain.ws.q;

import java.util.Date;


public class WSQFileType  implements java.io.Serializable{
  

    private Long idFileType;
    private String type;
    private String des;
    private Long idCompany;
	public Long getIdFileType() {
		return idFileType;
	}
	public void setIdFileType(Long idFileType) {
		this.idFileType = idFileType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
}
