package com.healthyfish.healthyfishdoctor.POJO;

public class BeanUserRegSubmitReq  extends BeanBaseReq {
	private String sickId;
	private String name;
	
	public BeanUserRegSubmitReq(){super(BeanUserRegSubmitReq.class.getSimpleName());}
	
	public String getSickId() {
		return sickId;
	}

	public void setSickId(String sickId) {
		this.sickId = sickId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
