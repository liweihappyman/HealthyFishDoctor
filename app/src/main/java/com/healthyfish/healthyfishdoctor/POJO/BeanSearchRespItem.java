package com.healthyfish.healthyfishdoctor.POJO;

public class BeanSearchRespItem{
	private String type;
	private String key;
	private String title;
	private String value;

	public BeanSearchRespItem(){}
	public BeanSearchRespItem(String t, String k, String v){type = t; key = k; value = v;}
	
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
}
