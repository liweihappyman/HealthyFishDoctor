package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHealthPlanListRespItem{
	private String url; //计划的内容链接
	private String title; //标题
	private String desciption; //简介
	//		private String content; //内容
	private int reads; //阅读数
	private int subscribe; //订单
	private int likes; //点赞数
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public String getTitle() {return title;	}
	public void setTitle(String title) {this.title = title;	}
	public String getDesciption() {return desciption;}
	public void setDesciption(String desciption) {this.desciption = desciption;}
	public int getReads() {return reads;}
	public void setReads(int reads) {this.reads = reads;}
	public int getSubscribe() {return subscribe;}
	public void setSubscribe(int subscribe) {this.subscribe = subscribe;}
	public int getLikes() {return likes;}
	public void setLikes(int likes) {this.likes = likes;}
}
