package com.healthyfish.healthyfishdoctor.POJO;


public class BeanUserSmsVerifyReq extends BeanBaseReq {
	private int		type; //0-新用户注册；1-修改密码
    private String mobileNo;
    private String verifyCode;

    BeanUserSmsVerifyReq(){super(BeanUserSmsAuthReq.class.getSimpleName());}

    public int getType() {return type;}
	public void setType(int type) {this.type = type;}
    public String getMobileNo() {return mobileNo;}
    public void setMobileNo(String mobileNo) {this.mobileNo = mobileNo;}
    public String getVerifyCode() {	return verifyCode;}
    public void setVerifyCode(String verifyCode) {this.verifyCode = verifyCode;	}

}
