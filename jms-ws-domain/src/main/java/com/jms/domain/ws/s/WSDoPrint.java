package com.jms.domain.ws.s;

import java.util.ArrayList;
import java.util.List;

public class WSDoPrint {

	
private String companyName;
private String customerName;
private String doCode;
private String doDate;
private String address;

private String url;

private List<WSDoItem> items = new ArrayList<WSDoItem>();

public List<WSDoItem> getItems() {
	return items;
}

public void setItems(List<WSDoItem> items) {
	this.items = items;
}

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

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}
   
}
