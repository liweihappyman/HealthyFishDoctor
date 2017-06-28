package com.healthyfish.healthyfishdoctor.POJO;

public class BeanSearchReq extends BeanBaseReq {
	//type: 
	//"blog" 博文
	//"hpc" health plan customize, use BeanHealthPlanCustomizeReq instead
	//"news" 新闻
	//"doct" 医生
	//null 所有类别
	private String type;
	private String keyword;
	
	BeanSearchReq(){super(BeanSearchReq.class.getSimpleName());}
	
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	public String getKeyword() {return keyword;}
	public void setKeyword(String keyword) {this.keyword = keyword;}
}
