package com.jms.domain.ws.p;

import java.util.ArrayList;
import java.util.List;

public class WSPmrRequest {
	
	private WSPUnplannedStops wsPUnplannedStops;
	private List<WSPMr> wsPMrs = new ArrayList<WSPMr>(0);
	
	public List<WSPMr> getWsPMrs() {
		return wsPMrs;
	}
	public void setWsPMrs(List<WSPMr> wsPMrs) {
		this.wsPMrs = wsPMrs;
	}
	public WSPUnplannedStops getWsPUnplannedStops() {
		return wsPUnplannedStops;
	}
	public void setWsPUnplannedStops(WSPUnplannedStops wsPUnplannedStops) {
		this.wsPUnplannedStops = wsPUnplannedStops;
	}

}
