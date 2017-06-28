package com.healthyfish.healthyfishdoctor.POJO;

import java.util.List;

public class BeanListAddReq extends BeanBaseReq {
	private String prefix;
	private List<String> keyList;

	BeanListAddReq(){super(BeanListAddReq.class.getSimpleName());}
	
	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
	public List<String> getKeyList() {return keyList;}
	public void setKeyList(List<String> keyList) {this.keyList = keyList;}

}
