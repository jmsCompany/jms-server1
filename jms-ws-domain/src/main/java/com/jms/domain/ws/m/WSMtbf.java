package com.jms.domain.ws.m;

import java.util.Date;


public class WSMtbf {
	
    private int x;
    private float mtbfActual;  
    private float mtbfTarget; 
    
    private float mttrActual;  
    private float mttrTarget;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public float getMtbfActual() {
		return mtbfActual;
	}
	public void setMtbfActual(float mtbfActual) {
		this.mtbfActual = mtbfActual;
	}
	public float getMtbfTarget() {
		return mtbfTarget;
	}
	public void setMtbfTarget(float mtbfTarget) {
		this.mtbfTarget = mtbfTarget;
	}
	public float getMttrActual() {
		return mttrActual;
	}
	public void setMttrActual(float mttrActual) {
		this.mttrActual = mttrActual;
	}
	public float getMttrTarget() {
		return mttrTarget;
	}
	public void setMttrTarget(float mttrTarget) {
		this.mttrTarget = mttrTarget;
	}
	
   
}
