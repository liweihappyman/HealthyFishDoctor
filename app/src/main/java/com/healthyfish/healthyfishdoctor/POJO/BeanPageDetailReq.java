package com.healthyfish.healthyfishdoctor.POJO;

//分页显示prefix指定的相关信息
//prefix的含义参考BeanBaseKeyAddReq.java
public class BeanPageDetailReq extends BeanBaseReq {
	//由prefix决定返回的response格式
	//prefix为news_，返回List<BeanItemNewsAbstract>对应的JSON String
	//prefix为pharm_，返回List<BeanItemPharmAbstract>对应的JSON String
	private String prefix; 
	private long from; // 0 stand for "from the start"
	private long to; // -1 stand for "to the end"
	private int refresh; //for test

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}

	BeanPageDetailReq(){super(BeanPageDetailReq.class.getSimpleName());}
	
	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
	public long getFrom() {return from;}
	public void setFrom(long from) {this.from = from;}
	public long getTo() {return to;}
	public void setTo(long to) {this.to = to;}	
}
