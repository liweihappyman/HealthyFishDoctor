package com.healthyfish.healthyfishdoctor.POJO;

public class BeanPageRespItem{
	private String key;
	private String value;
	private long 	reads;
	private long	likes;

	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
	public long getReads() {return reads;}
	public void setReads(long reads) {this.reads = reads;}
	public long getLikes() {return likes;}
	public void setLikes(long likes) {this.likes = likes;}
}
