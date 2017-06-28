package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanDoctorListResp extends BeanBaseResp {
	
	private List<BeanDoctorListRespItem> doctorList = new ArrayList<BeanDoctorListRespItem>();

	public List<BeanDoctorListRespItem> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(List<BeanDoctorListRespItem> doctorList) {
		this.doctorList = doctorList;
	}
}
