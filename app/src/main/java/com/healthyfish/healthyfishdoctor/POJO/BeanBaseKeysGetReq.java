package com.healthyfish.healthyfishdoctor.POJO;

import java.util.LinkedList;
import java.util.List;

public class BeanBaseKeysGetReq extends BeanBaseReq {
	private List<String> keyList = new LinkedList<String>();
	
	BeanBaseKeysGetReq(){super(BeanBaseKeysGetReq.class.getSimpleName());}
	
	public List<String> getKeyList() {return keyList;}
	public void setKeyList(List<String> keyList) {this.keyList = keyList;}
}
