package com.jms.domain.ws;

import java.util.List;


public class WSTest implements java.io.Serializable{
	
	private static final long serialVersionUID = 7787128799747339720L;
	private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List<String[]> data;
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
   
}
