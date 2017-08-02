package com.jms.domain.ws.s;

import java.util.ArrayList;
import java.util.List;

public class WSIqcPrint {

	
private String companyName;
private String customerName;
private String doCode;
private String poCode;
private String doDate;
private String address;

private String url;

private List<WSIqcItem> items = new ArrayList<WSIqcItem>();

public String getCompanyName() {
	return companyName;
}

public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

public String getCustomerName() {
	return customerName;
}

public void setCustomerName(String customerName) {
	this.customerName = customerName;
}

public String getDoCode() {
	return doCode;
}

public void setDoCode(String doCode) {
	this.doCode = doCode;
}

public String getDoDate() {
	return doDate;
}

public void setDoDate(String doDate) {
	this.doDate = doDate;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public List<WSIqcItem> getItems() {
	return items;
}

public void setItems(List<WSIqcItem> items) {
	this.items = items;
}

public String getPoCode() {
	return poCode;
}

public void setPoCode(String poCode) {
	this.poCode = poCode;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}
   
}
