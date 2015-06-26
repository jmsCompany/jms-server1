package com.jms.domain;

public enum EnabledEnum {

	//ROBOT only for testing purpose
	DISENABLED(0l), ENABLED(1l), ROBOT(2l);
	 
	private Long statusCode;
 
	private EnabledEnum(Long s) {
		statusCode = s;
	}
 
	public Long getStatusCode() {
		return statusCode;
	}
}
