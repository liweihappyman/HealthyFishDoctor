package com.healthyfish.healthyfishdoctor.POJO;


public class BeanUserSmsAuthReq extends BeanBaseReq {
	private int		type; //短信验证类型：0-新用户注册；1-找回密码
	private String mobileNo;

    public BeanUserSmsAuthReq(){super(BeanUserSmsAuthReq.class.getSimpleName());}

    public int getType() {return type;}
	public void setType(int type) {this.type = type;}
    public String getMobileNo() {return mobileNo;}
    public void setMobileNo(String mobileNo) {this.mobileNo = mobileNo;}

}
