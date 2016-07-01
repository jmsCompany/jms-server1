package com.jms.domain;

public enum SystemRoleEnum {

	admin(0l), user(1l),group_admin(2l),sector_supervisor(3l),manager(4l),OP(101l),quality(102l),supervisor(103l),warehouse(104l),equipment(105l);
	 
	private Long statusCode;
 
	private SystemRoleEnum(Long s) {
		statusCode = s;
	}
 
	public Long getStatusCode() {
		return statusCode;
	}
}
