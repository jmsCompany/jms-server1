package com.jms.domain.ws.production;

import java.util.Date;



public class WSPCheckPlan {
 
	private Long pcppId; //日计划ID
    private Date planCheckTime; //计划检查时间
    private Date checkTime; //实际检查时间
    private Long toBeQty; //当前按计划需完成总数
    private Long finQty;  //实际完成总数
    private Long totalQty; //日计划需要完成数量
    private String status; //满意，不满意


	public Long getPcppId() {
		return pcppId;
	}

	public void setPcppId(Long pcppId) {
		this.pcppId = pcppId;
	}

	public Date getPlanCheckTime() {
		return planCheckTime;
	}

	public void setPlanCheckTime(Date planCheckTime) {
		this.planCheckTime = planCheckTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Long getToBeQty() {
		return toBeQty;
	}

	public void setToBeQty(Long toBeQty) {
		this.toBeQty = toBeQty;
	}

	public Long getFinQty() {
		return finQty;
	}

	public void setFinQty(Long finQty) {
		this.finQty = finQty;
	}

	public Long getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
