package com.jms.domain;

public enum GroupTypeEnum {

	
	Company(1l), User(2l), Sector(3l),Group(4l),Role(5l);
	private Long statusCode;
	 
	private GroupTypeEnum(Long s) {
		statusCode = s;
	}
 
	public Long getStatusCode() {
		return statusCode;
	}
	 
	
}
