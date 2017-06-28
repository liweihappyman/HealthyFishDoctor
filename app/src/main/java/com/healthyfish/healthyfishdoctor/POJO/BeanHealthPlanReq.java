package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHealthPlanReq extends BeanBaseReq {
	private String key;
	
	BeanHealthPlanReq(){super(BeanHealthPlanReq.class.getSimpleName());}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
}
