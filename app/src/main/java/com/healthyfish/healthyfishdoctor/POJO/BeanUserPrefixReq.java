package com.healthyfish.healthyfishdoctor.POJO;

public class BeanUserPrefixReq extends BeanBaseReq {
	private String prefix;

	BeanUserPrefixReq(){super(BeanUserPrefixReq.class.getSimpleName());}
	
	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
}
