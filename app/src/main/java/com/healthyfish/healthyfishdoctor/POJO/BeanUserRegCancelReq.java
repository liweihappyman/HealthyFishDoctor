package com.healthyfish.healthyfishdoctor.POJO;

public class BeanUserRegCancelReq extends BeanBaseReq {
	private String key;
	
	public BeanUserRegCancelReq(){super(BeanUserRegCancelReq.class.getSimpleName());}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
