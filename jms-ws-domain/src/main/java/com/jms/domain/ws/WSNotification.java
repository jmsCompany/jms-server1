package com.jms.domain.ws;

import java.util.Date;


public class WSNotification implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idNotification;
	private String username;
	private Date creationTime;
	private String event;
	private String details;
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Long getIdNotification() {
		return idNotification;
	}
	public void setIdNotification(Long idNotification) {
		this.idNotification = idNotification;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


}
