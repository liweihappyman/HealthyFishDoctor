package com.healthyfish.healthyfishdoctor.POJO;

import java.util.LinkedList;
import java.util.List;

public class BeanBaseKeysGetResp extends BeanBaseResp {
	private List<String> valueList = new LinkedList<String>();

	public List<String> getValueList() {return valueList;}
	public void setValueList(List<String> valueList) {this.valueList = valueList;}
}
