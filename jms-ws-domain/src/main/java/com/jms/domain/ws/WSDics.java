package com.jms.domain.ws;

import java.util.ArrayList;
import java.util.List;

public class WSDics implements java.io.Serializable{
	
	private List<WSSysDicD> wsSysDicDList = new ArrayList<WSSysDicD>();

	public List<WSSysDicD> getWsSysDicDList() {
		return wsSysDicDList;
	}

	public void setWsSysDicDList(List<WSSysDicD> wsSysDicDList) {
		this.wsSysDicDList = wsSysDicDList;
	}



}
