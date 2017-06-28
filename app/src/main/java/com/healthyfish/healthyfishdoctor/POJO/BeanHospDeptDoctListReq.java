package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHospDeptDoctListReq extends BeanBaseReq {
	private String hosp;
	private String dept;
	
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getHosp() {
		return hosp;
	}
	public void setHosp(String hosp) {
		this.hosp = hosp;
	}
	BeanHospDeptDoctListReq(){super(BeanHospDeptDoctListReq.class.getSimpleName());}
	
}
