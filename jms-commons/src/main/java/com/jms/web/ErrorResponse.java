package com.jms.web;

import java.io.Serializable;
import java.util.Date;

public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date timestamp;
	private String path;
    private String message;
    private String jmsError;
    private String status;
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJmsError() {
		return jmsError;
	}
	public void setJmsError(String jmsError) {
		this.jmsError = jmsError;
	}
}
