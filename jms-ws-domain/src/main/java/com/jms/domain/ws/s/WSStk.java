package com.jms.domain.ws.store;


public class WSStk {

	private Long idStkType;
    private String stkName;
    private String des;
    private String address;
    private Long status;
    private Long idStk;
    
    
	
    public Long getIdStkType() {
		return idStkType;
	}
	public void setIdStkType(Long idStkType) {
		this.idStkType = idStkType;
	}
	public String getStkName() {
		return stkName;
	}
	public void setStkName(String stkName) {
		this.stkName = stkName;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getIdStk() {
		return idStk;
	}
	public void setIdStk(Long idStk) {
		this.idStk = idStk;
	}

   
}
