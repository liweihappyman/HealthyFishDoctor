package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHospDeptDoctListReq extends BeanBaseReq {
	private String hosp; // "lzzyy":柳州中医院
	private String dept; // BeanHospDeptListRespItem中的DEPT_CODE
	
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
	public BeanHospDeptDoctListReq(){super(BeanHospDeptDoctListReq.class.getSimpleName());}
	
}
