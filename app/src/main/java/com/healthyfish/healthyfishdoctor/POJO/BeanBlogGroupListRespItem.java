package com.healthyfish.healthyfishdoctor.POJO;

public class BeanBlogGroupListRespItem{
	private String name; //名称
	private String desciption; //简介
	//		private String content; //内容
	private int reads; //阅读数
	private int likes; //点赞数
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getDesciption() {return desciption;}
	public void setDesciption(String desciption) {this.desciption = desciption;}
	public int getReads() {return reads;}
	public void setReads(int reads) {this.reads = reads;}
	public int getLikes() {return likes;}
	public void setLikes(int likes) {this.likes = likes;}
}
