package com.jms.domain.ws;



public class WSSector implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -748466226685624795L;
	private Long idSector;
    private String companyName;
    private String sector;
    private String description;
    private Long enabled;
    private Long seq;
	public Long getIdSector() {
		return idSector;
	}
	public void setIdSector(Long idSector) {
		this.idSector = idSector;
	}

	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getEnabled() {
		return enabled;
	}
	public void setEnabled(Long enabled) {
		this.enabled = enabled;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
}
