package com.jms.domain.ws.p;


import java.util.ArrayList;
import java.util.List;


public class WSPlannedMaterialSending {


    private Long fromStkId;
    private Long toStkId;

    private List<WSPlannedMaterialSendingItem> items = new ArrayList<WSPlannedMaterialSendingItem>();

	public List<WSPlannedMaterialSendingItem> getItems() {
		return items;
	}

	public void setItems(List<WSPlannedMaterialSendingItem> items) {
		this.items = items;
	}

	public Long getToStkId() {
		return toStkId;
	}

	public void setToStkId(Long toStkId) {
		this.toStkId = toStkId;
	}

	public Long getFromStkId() {
		return fromStkId;
	}

	public void setFromStkId(Long fromStkId) {
		this.fromStkId = fromStkId;
	}

}
