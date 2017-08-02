package com.jms.domain.ws.s;

import java.util.ArrayList;
import java.util.List;

public class WSPoPrint {

	
private String companyName;
private String address;
private String telephone;
private String fax;
private String url;
private String coName;
private String coAddress;

private String linkMan1;
private String linkTel1;
private String linkMan;
private String linkTel;
private String poCode;
private String poDate;

private String delDate; //交货日

private String price;  //总价
private String priceCap; //总价 大写



private List<WSPoItem> items = new ArrayList<WSPoItem>();

public String getCompanyName() {
	return companyName;
}

public void setCompanyName(String companyName) {
	this.companyName = companyName;
}



public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public List<WSPoItem> getItems() {
	return items;
}

public void setItems(List<WSPoItem> items) {
	this.items = items;
}



public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public String getTelephone() {
	return telephone;
}

public void setTelephone(String telephone) {
	this.telephone = telephone;
}

public String getFax() {
	return fax;
}

public void setFax(String fax) {
	this.fax = fax;
}

public String getCoName() {
	return coName;
}

public void setCoName(String coName) {
	this.coName = coName;
}

public String getCoAddress() {
	return coAddress;
}

public void setCoAddress(String coAddress) {
	this.coAddress = coAddress;
}

public String getLinkMan() {
	return linkMan;
}

public void setLinkMan(String linkMan) {
	this.linkMan = linkMan;
}

public String getLinkTel() {
	return linkTel;
}

public void setLinkTel(String linkTel) {
	this.linkTel = linkTel;
}

public String getPoDate() {
	return poDate;
}

public void setPoDate(String poDate) {
	this.poDate = poDate;
}

public String getPoCode() {
	return poCode;
}

public void setPoCode(String poCode) {
	this.poCode = poCode;
}

public String getDelDate() {
	return delDate;
}

public void setDelDate(String delDate) {
	this.delDate = delDate;
}

public String getPrice() {
	return price;
}

public void setPrice(String price) {
	this.price = price;
}

public String getPriceCap() {
	return priceCap;
}

public void setPriceCap(String priceCap) {
	this.priceCap = priceCap;
}

public String getLinkMan1() {
	return linkMan1;
}

public void setLinkMan1(String linkMan1) {
	this.linkMan1 = linkMan1;
}

public String getLinkTel1() {
	return linkTel1;
}

public void setLinkTel1(String linkTel1) {
	this.linkTel1 = linkTel1;
}
   
}
