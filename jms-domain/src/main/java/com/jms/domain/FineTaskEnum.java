package com.jms.domain;

public enum FineTaskEnum {

	NORMALTASK(0), FINETASK(1);
	 
	private int statusCode;
 
	private FineTaskEnum(int s) {
		statusCode = s;
	}
 
	public int getStatusCode() {
		return statusCode;
	}
}
