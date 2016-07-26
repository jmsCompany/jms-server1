package com.jms.domain;

public enum EventTypeEnum {

	create(0l), update(1l), delete(2l),create_ms(3l),update_ms(4l),create_eq(5l),create_qa(6l),create_me(7l),create_sa(8l);
	 
	private Long statusCode;
 
	private EventTypeEnum(Long s) {
		statusCode = s;
	}
 
	public Long getStatusCode() {
		return statusCode;
	}
}
