package com.jms.domain.ws.p;



public class WSPCppOP implements java.io.Serializable{


    private Long idCpp;
    private String cpp;
    private boolean checked;
    
	public Long getIdCpp() {
		return idCpp;
	}
	public void setIdCpp(Long idCpp) {
		this.idCpp = idCpp;
	}
	public String getCpp() {
		return cpp;
	}
	public void setCpp(String cpp) {
		this.cpp = cpp;
	};
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
