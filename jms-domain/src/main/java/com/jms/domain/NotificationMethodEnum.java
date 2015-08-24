package com.jms.domain;

public enum NotificationMethodEnum {

	email(0l), sms(1l), sys(2l);
	 
	private Long statusCode;
 
	private NotificationMethodEnum(Long s) {
		statusCode = s;
	}
 
	public Long getStatusCode() {
		return statusCode;
	}
}
