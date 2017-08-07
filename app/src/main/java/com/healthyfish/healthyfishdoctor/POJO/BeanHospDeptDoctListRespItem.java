package com.healthyfish.healthyfishdoctor.POJO;


import java.io.Serializable;
import java.util.List;

//获取到的数据：
//[{"PRE_LIMIT":5,
// "PRICE":10,
// "DOCTOR_NAME":"江铭",
// "WEB_INTRODUCE":"擅长双眼皮、祛眼袋、隆鼻、面部除皱，面部肿物、疤痕治疗，胸部整形，吸脂减肥、微创腋臭治疗等整形美容手术。",
// "schdList":["2017-07-21_2","2017-07-24_1","2017-07-24_2","2017-07-25_1","2017-07-25_2","2017-07-26_1","2017-07-26_2","2017-07-27_1","2017-07-27_2"],
// "PRE_ALLOW":0,
// "CLINIQUE_CODE":403,
// "title":"柳州市中医院-中医美容科-江铭",
// "WORK_DATE":"2017-07-21T00:00:00",
// "REISTER_NAME":"副主任医师号",
// "WORK_TYPE":2,
// "STAFF_NO":"054",
// "ZHAOPIAN":"/demo/downloadFile/img_lzzyy_0204_054.jpg",
// "DOCTOR":"江铭/054"}]

public class BeanHospDeptDoctListRespItem extends BeanBaseReq implements Serializable{

	private int STAFF_NO; //员工编号
	private String REISTER_NAME; //职称
	private int CLINIQUE_CODE; //诊室
	private int PRICE; //挂号价格
	private String DOCTOR_NAME; //姓名。挂号时，提供DOCTOR而非DOCTOR_NAME
	private String WEB_INTRODUCE; //简介
	private String ZHAOPIAN; //照片
	private int WORK_TYPE; //

	private String DOCTOR; //挂号用的医生标识
	private int PRE_ALLOW;
	/*"schdList":[\"2017-07-21_1\",\"2017-07-24_1\",\"2017-07-25_1\",\"2017-07-26_1\",\"2017-07-27_1\"*/
	private List<String> schdList; //排班表, 1:上午，2:下午

	public BeanHospDeptDoctListRespItem(){super(BeanHospDeptDoctListRespItem.class.getSimpleName());}

	public int getSTAFF_NO() {
		return STAFF_NO;
	}

	public void setSTAFF_NO(int sTAFF_NO) {
		STAFF_NO = sTAFF_NO;
	}

	public String getREISTER_NAME() {
		return REISTER_NAME;
	}

	public void setREISTER_NAME(String rEISTER_NAME) {
		REISTER_NAME = rEISTER_NAME;
	}

	public int getCLINIQUE_CODE() {
		return CLINIQUE_CODE;
	}

	public void setCLINIQUE_CODE(int cLINIQUE_CODE) {
		CLINIQUE_CODE = cLINIQUE_CODE;
	}

	public int getPRICE() {
		return PRICE;
	}

	public void setPRICE(int pRICE) {
		PRICE = pRICE;
	}

	public String getDOCTOR_NAME() {
		return DOCTOR_NAME;
	}

	public void setDOCTOR_NAME(String dOCTOR_NAME) {
		DOCTOR_NAME = dOCTOR_NAME;
	}

	public String getWEB_INTRODUCE() {
		return WEB_INTRODUCE;
	}

	public void setWEB_INTRODUCE(String wEB_INTRODUCE) {
		WEB_INTRODUCE = wEB_INTRODUCE;
	}

	public String getZHAOPIAN() {
		return ZHAOPIAN;
	}

	public void setZHAOPIAN(String zHAOPIAN) {
		ZHAOPIAN = zHAOPIAN;
	}

	public int getWORK_TYPE() {
		return WORK_TYPE;
	}

	public void setWORK_TYPE(int wORK_TYPE) {
		WORK_TYPE = wORK_TYPE;
	}

	public String getDOCTOR() {
		return DOCTOR;
	}

	public void setDOCTOR(String dOCTOR) {
		DOCTOR = dOCTOR;
	}

	public int getPRE_ALLOW() {
		return PRE_ALLOW;
	}

	public void setPRE_ALLOW(int pRE_ALLOW) {
		PRE_ALLOW = pRE_ALLOW;
	}

	public List<String> getSchdList() {
		return schdList;
	}

	public void setSchdList(List<String> schdList) {
		this.schdList = schdList;
	}

}
