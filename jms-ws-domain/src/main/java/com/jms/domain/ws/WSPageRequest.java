package com.jms.domain.ws;

public class WSPageRequest implements java.io.Serializable{
	
	private Integer page;
    private Integer size;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
    
   
}
