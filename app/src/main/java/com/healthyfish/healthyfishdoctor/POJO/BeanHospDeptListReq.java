package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHospDeptListReq extends BeanBaseReq {
	private String hosp;
	
	public String getHosp() {
		return hosp;
	}
	public void setHosp(String hosp) {
		this.hosp = hosp;
	}
	BeanHospDeptListReq(){super(BeanHospDeptListReq.class.getSimpleName());}
	
}
