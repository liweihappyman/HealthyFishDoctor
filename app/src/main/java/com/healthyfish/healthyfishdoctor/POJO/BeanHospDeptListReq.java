package com.healthyfish.healthyfishdoctor.POJO;
/*
把这三个课时排在前面
"脾胃病科门诊"
"肝病门诊"
"中医预防保健科"
*/
public class BeanHospDeptListReq extends BeanBaseReq {
	private String hosp; // "lzzyy":柳州中医院
	
	public String getHosp() {
		return hosp;
	}
	public void setHosp(String hosp) {
		this.hosp = hosp;
	}
	public BeanHospDeptListReq(){super(BeanHospDeptListReq.class.getSimpleName());}
	
}
