package com.healthyfish.healthyfishdoctor.POJO;

import java.io.Serializable;

public class BeanUserRegisterReq extends BeanBaseReq implements Serializable{
	private int		type; //0-新用户注册；1-修改密码
	private String  mobileNo;
    private String verifyCode;
	private String  pwdSHA256;
    //...

    public BeanUserRegisterReq(){super(BeanUserRegisterReq.class.getSimpleName());}

    public int getType() {return type;}
	public void setType(int type) {this.type = type;}
    public String getMobileNo() {return mobileNo;}
    public void setMobileNo(String mobileNo) {this.mobileNo = mobileNo;}
    public String getVerifyCode() {	return verifyCode;}
    public void setVerifyCode(String verifyCode) {this.verifyCode = verifyCode;	}
    public String getPwdSHA256() {return pwdSHA256;}
    public void setPwdSHA256(String pwdSHA256) {this.pwdSHA256 = pwdSHA256;}
}
