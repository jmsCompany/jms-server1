package com.jms.system;

import java.util.List;

import com.jms.domain.ws.WSSysDicD;


public interface IDicDService {
	public List<WSSysDicD> getDicsByType(String type) throws Exception;
}
