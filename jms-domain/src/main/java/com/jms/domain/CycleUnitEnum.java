package com.jms.domain;

public enum CycleUnitEnum {

	
	月(1l), 周(2l), 天(3l);
	 
	private Long statusCode;
 
	private CycleUnitEnum(Long s) {
		statusCode = s;
	}
 
	public Long getStatusCode() {
		return statusCode;
	}
}
