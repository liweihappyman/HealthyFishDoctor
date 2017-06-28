package com.healthyfish.healthyfishdoctor.POJO;

import java.util.List;

public class BeanBaseKeysRemReq extends BeanBaseReq {
	private String prefix; //删除list中的元素时，需指定list的名称
	private List<String> keyList;
	
	BeanBaseKeysRemReq(){super(BeanBaseKeysRemReq.class.getSimpleName());}
	
	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
	public List<String> getKeyList() {return keyList;}
	public void setKeyList(List<String> keyList) {this.keyList = keyList;}
}
