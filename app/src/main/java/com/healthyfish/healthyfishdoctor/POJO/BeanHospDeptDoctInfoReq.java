package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHospDeptDoctInfoReq extends BeanBaseReq {
	private String hosp;
	private String dept;
	private String doct;
	
	public String getDoct() {
		return doct;
	}
	public void setDoct(String doct) {
		this.doct = doct;
	}
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
	BeanHospDeptDoctInfoReq(){super(BeanHospDeptDoctInfoReq.class.getSimpleName());}
	
}
