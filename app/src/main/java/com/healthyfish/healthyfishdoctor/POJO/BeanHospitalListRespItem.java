package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHospitalListRespItem extends BeanBaseResp {
	private String url; //医院详细介绍的链接
	private String name; //名称
	private String address; //地址
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}
}
