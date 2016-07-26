package com.jms.domain.ws.s;


public class WSMaterialCategory {

	private Long id;
    private Long status;
    private String name;
    private String des;
    private Long orderBy;
    private String statusName;
   
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Long getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


}
