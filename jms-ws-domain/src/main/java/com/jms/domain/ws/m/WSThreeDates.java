package com.jms.domain.ws.m;

import java.util.Date;


public class WSThreeDates {
	
	private Date currentDate;
	private Date monday;
	private Date sunday;
	public Date getSunday() {
		return sunday;
	}
	public void setSunday(Date sunday) {
		this.sunday = sunday;
	}
	public Date getMonday() {
		return monday;
	}
	public void setMonday(Date monday) {
		this.monday = monday;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
   
}
