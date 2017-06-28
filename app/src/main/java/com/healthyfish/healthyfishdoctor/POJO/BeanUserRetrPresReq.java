package com.healthyfish.healthyfishdoctor.POJO;

public class BeanUserRetrPresReq extends BeanBaseReq {
	private String user;
	private String hosp;
	private String sickId;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSickId() {
		return sickId;
	}

	public void setSickId(String sickId) {
		this.sickId = sickId;
	}

	BeanUserRetrPresReq(){super(BeanUserRetrPresReq.class.getSimpleName());}
	
	public String getHosp() {
		return hosp;
	}

	public void setHosp(String hosp) {
		this.hosp = hosp;
	}

}
