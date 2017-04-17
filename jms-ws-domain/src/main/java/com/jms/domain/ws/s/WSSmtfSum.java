package com.jms.domain.ws.s;

public class WSSmtfSum {
	
	
	private String companyName;
	private String material;
    private Long received;
    private Long sent;
    private Long invQty;
    

    public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getReceived() {
		return received;
	}
	public void setReceived(Long received) {
		this.received = received;
	}
	public Long getSent() {
		return sent;
	}
	public void setSent(Long sent) {
		this.sent = sent;
	}
	public Long getInvQty() {
		return invQty;
	}
	public void setInvQty(Long invQty) {
		this.invQty = invQty;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}

   
}
