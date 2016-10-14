package com.jms.domain.ws.p;

import java.util.Date;


public class WSOCalendar {
    
	private Long idCalendar;
    private Long idOCalendarType;
    private String type;
    private String name;
    private Long year;
    private Date date;
    private Long idCompany;
    private Long idShiftD;
    
	public Long getIdCalendar() {
		return idCalendar;
	}
	
	public void setIdCalendar(Long idCalendar) {
		this.idCalendar = idCalendar;
	}
	
	public Long getIdOCalendarType() {
		return idOCalendarType;
	}
	
	public void setIdOCalendarType(Long idOCalendarType) {
		this.idOCalendarType = idOCalendarType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	public Long getIdShiftD() {
		return idShiftD;
	}
	public void setIdShiftD(Long idShiftD) {
		this.idShiftD = idShiftD;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
   
}
