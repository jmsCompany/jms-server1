package com.jms.domain;

public enum EnabledEnum {

	//ROBOT only for testing purpose
	DISENABLED(0), ENABLED(1), ROBOT(2);
	 
	private int statusCode;
 
	private EnabledEnum(int s) {
		statusCode = s;
	}
 
	public int getStatusCode() {
		return statusCode;
	}
}
