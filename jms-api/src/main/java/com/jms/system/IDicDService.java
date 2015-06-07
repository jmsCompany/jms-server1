package com.jms.system;

import com.jms.domain.ws.WSDics;

public interface IDicDService {
	public WSDics getDicsByType(String type) throws Exception;
}
