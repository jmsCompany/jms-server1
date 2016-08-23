package com.jms.domain.ws.m;

import java.util.Date;


public class WSBreakdownRate {
	
    private int x;
    private float y;  //故障率
    private float target; //目标
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getTarget() {
		return target;
	}
	public void setTarget(float target) {
		this.target = target;
	}
   
}
