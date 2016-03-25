package com.jms.domain;

public enum SandVikRoleEnum {

	OP(1000l), equipment(1001l),quality(1002l),warehouse(1003l),supervisor(1004l);
	 
	private Long statusCode;
 
	private SandVikRoleEnum(Long s) {
		statusCode = s;
	}
 
	public Long getStatusCode() {
		return statusCode;
	}
}
