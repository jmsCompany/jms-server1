package com.jms.domain;

public enum CompanyCategoryEnum {


	NORMAL_COMPANY(0), SYSTEM_COMPANY(1), TEMPLATE_COMPANY(2);
	 
	private int statusCode;
 
	private CompanyCategoryEnum(int s) {
		statusCode = s;
	}
 
	public int getStatusCode() {
		return statusCode;
	}
}
