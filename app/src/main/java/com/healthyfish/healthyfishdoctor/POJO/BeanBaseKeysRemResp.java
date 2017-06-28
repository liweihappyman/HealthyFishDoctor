package com.healthyfish.healthyfishdoctor.POJO;

import java.util.LinkedList;
import java.util.List;

public class BeanBaseKeysRemResp extends BeanBaseResp {
	//返回删除失败的key
	private List<String> failedList = new LinkedList<String>();

	public List<String> getFailedList() {return failedList;}
	public void setFailedList(List<String> failedList) {this.failedList = failedList;}

}
