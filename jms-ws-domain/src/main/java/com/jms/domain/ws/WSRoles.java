package com.jms.domain.ws;

public class WSRoles implements java.io.Serializable{
	private Integer idRole;
	private String companyName;
	private String role;
	private String description;
	private Integer level;
	private Integer idSector;
	private String sector;
	private Integer enabled;
	 private Integer isprimary;;
	public Integer getIdRole() {
		return idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIdSector() {
		return idSector;
	}

	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getIsprimary() {
		return isprimary;
	}

	public void setIsprimary(Integer isprimary) {
		this.isprimary = isprimary;
	}

}
