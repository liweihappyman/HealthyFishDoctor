package com.healthyfish.healthyfishdoctor.POJO;

import java.util.List;

public class BeanHospitalListResp extends BeanBaseResp {
	private List<BeanHospitalListRespItem> hospitalList;

	public List<BeanHospitalListRespItem> getHospitalList() {return hospitalList;}
	public void setHospitalList(List<BeanHospitalListRespItem> hospitalList) {this.hospitalList = hospitalList;}

}
