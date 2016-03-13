package com.jms.domain.ws.store;

import java.util.ArrayList;
import java.util.List;

public class WSMaterialCategory {

	private Long id;
    private Long status;
    private Long parent;
    private String name;
    private String des;
    private Long orderBy;
    private List<Long> idPics =new ArrayList<Long>();
    
	
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
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
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
	public List<Long> getIdPics() {
		return idPics;
	}
	public void setIdPics(List<Long> idPics) {
		this.idPics = idPics;
	}

}
