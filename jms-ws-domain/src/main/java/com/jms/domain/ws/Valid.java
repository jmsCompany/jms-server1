package com.jms.domain.ws;

import java.io.Serializable;

public class Valid implements Serializable {
	
	private static final long serialVersionUID = 742134586991767616L;
	private Boolean valid;
	private String msg;

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
