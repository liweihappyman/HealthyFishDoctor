package com.healthyfish.healthyfishdoctor.POJO;

import java.util.LinkedList;
import java.util.List;

//not used
public class BeanPageResp extends BeanBaseResp {
	private int total; //总数，用于客户端的分页
	private List<String> pageList = new LinkedList<String>();
//	private List<BeanPageRespItem> pageList = new LinkedList<BeanPageRespItem>();

	public int getTotal() {	return total;}
	public void setTotal(int total) {this.total = total;}
	public List<String> getPageList() {	return pageList;}
	public void setPageList(List<String> pageList) {this.pageList = pageList;}
//	public List<BeanPageRespItem> getPageList() {return pageList;}
//	public void setPageList(List<BeanPageRespItem> pageList) {this.pageList = pageList;}
}
