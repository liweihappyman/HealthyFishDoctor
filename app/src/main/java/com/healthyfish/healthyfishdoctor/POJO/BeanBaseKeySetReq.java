package com.healthyfish.healthyfishdoctor.POJO;

public class BeanBaseKeySetReq extends BeanBaseReq {
	private String key;
	private String value;
	

	public BeanBaseKeySetReq(){super(BeanBaseKeySetReq.class.getSimpleName());}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
}
