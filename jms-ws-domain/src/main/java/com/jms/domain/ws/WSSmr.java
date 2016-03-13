package com.jms.domain.ws;

//获得需料信息
public class WSSmr implements java.io.Serializable {

	private String op;// 操作工人姓名
	private String mNo;// 机器号
	private String pNo;// 原料编号
	private int ver;// 原料版本
	private String des;// 原料描述
	private int qty;// 需要的原料数量
	private String lotNo;// 原料的批号
	private String bin;// 原料的库位
	private int binQty;// 原料所在库位的数量
	private int actQty;// 建议发料的数量

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getmNo() {
		return mNo;
	}

	public void setmNo(String mNo) {
		this.mNo = mNo;
	}

	public String getpNo() {
		return pNo;
	}

	public void setpNo(String pNo) {
		this.pNo = pNo;
	}

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public int getBinQty() {
		return binQty;
	}

	public void setBinQty(int binQty) {
		this.binQty = binQty;
	}

	public int getActQty() {
		return actQty;
	}

	public void setActQty(int actQty) {
		this.actQty = actQty;
	}
}
