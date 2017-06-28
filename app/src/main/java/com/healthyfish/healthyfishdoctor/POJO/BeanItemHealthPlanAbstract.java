package com.healthyfish.healthyfishdoctor.POJO;

public class BeanItemHealthPlanAbstract{
	private String url; //获取详细计划的url
	private int idOffset; 
	private String title; //标题
	private String description; //简介

	public BeanItemHealthPlanAbstract(){}
	public BeanItemHealthPlanAbstract(BeanItemHealthPlan bean){
		this.title = bean.getTitle();
		this.description = bean.getDescription();
	}
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public int getIdOffset() {return idOffset;}
	public void setIdOffset(int idOffset) {this.idOffset = idOffset;}
	public String getTitle() {return title;	}
	public void setTitle(String title) {this.title = title;	}
	public String getDescription() {return description;}
	public void setDescription(String desciption) {this.description = desciption;}
}
