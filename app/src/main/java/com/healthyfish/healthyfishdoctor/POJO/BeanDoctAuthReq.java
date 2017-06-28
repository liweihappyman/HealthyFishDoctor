package com.healthyfish.healthyfishdoctor.POJO;

public class BeanDoctAuthReq extends BeanBaseReq {
	private String uid;
	private String hosp;
	public String getHosp() {
		return hosp;
	}

	public void setHosp(String hosp) {
		this.hosp = hosp;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDoct() {
		return doct;
	}

	public void setDoct(String doct) {
		this.doct = doct;
	}
	private String dept;
	private String doct;

	BeanDoctAuthReq(){super(BeanDoctAuthReq.class.getSimpleName());}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
}
