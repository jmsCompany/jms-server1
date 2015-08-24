package com.jms.domain;

public enum EventTypeEnum {

	create(0l), update(1l), delete(2l);
	 
	private Long statusCode;
 
	private EventTypeEnum(Long s) {
		statusCode = s;
	}
 
	public Long getStatusCode() {
		return statusCode;
	}
}
