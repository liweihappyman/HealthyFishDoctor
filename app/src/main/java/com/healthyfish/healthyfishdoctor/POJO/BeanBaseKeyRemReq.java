package com.healthyfish.healthyfishdoctor.POJO;

public class BeanBaseKeyRemReq extends BeanBaseReq {
	private String key;
	
	BeanBaseKeyRemReq(){super(BeanBaseKeyRemReq.class.getSimpleName());}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
}
