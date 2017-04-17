package com.jms.domain.ws.s;

import java.util.ArrayList;
import java.util.List;

import com.jms.domain.ws.WSSelectObj;

public class WSBomComs {

	private Long idBom;
	private List<WSSelectObj> coms = new ArrayList<WSSelectObj>();

	public Long getIdBom() {
		return idBom;
	}

	public void setIdBom(Long idBom) {
		this.idBom = idBom;
	}

	public List<WSSelectObj> getComs() {
		return coms;
	}

	public void setComs(List<WSSelectObj> coms) {
		this.coms = coms;
	}

}
