package com.jms.domain.ws.s;

import java.util.ArrayList;
import java.util.List;

public class WSMrpNo {

	private List<Long> ids  = new ArrayList<Long>();
	private List<Long> aqtys  = new ArrayList<Long>();
	private Long type;
	private Long poType;
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getPoType() {
		return poType;
	}
	public void setPoType(Long poType) {
		this.poType = poType;
	}
	public List<Long> getAqtys() {
		return aqtys;
	}
	public void setAqtys(List<Long> aqtys) {
		this.aqtys = aqtys;
	}
	
   
}
