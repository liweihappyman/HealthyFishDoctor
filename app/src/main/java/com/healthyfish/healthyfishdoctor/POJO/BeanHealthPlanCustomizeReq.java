package com.healthyfish.healthyfishdoctor.POJO;

//定制整体计划
//客户端提交此请求，肯定已经知晓该计划的内容
//在resp中，返回用户定制的key
public class BeanHealthPlanCustomizeReq extends BeanBaseReq {
	private String key; //定制的HealthPlanId
	private String date; //起始日期
	
	BeanHealthPlanCustomizeReq(){super(BeanHealthPlanCustomizeReq.class.getSimpleName());}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	public String getDate() {return date;}
	public void setDate(String date) {this.date = date;}
}
