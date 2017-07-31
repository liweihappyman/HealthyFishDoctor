package com.healthyfish.healthyfishdoctor.POJO;

public class BeanUserLogoutReq extends BeanBaseReq {
    private String  mobileNo;
    private String  pwdSHA256;
    //...

    public BeanUserLogoutReq(){super(BeanUserLogoutReq.class.getSimpleName());}

    public String getMobileNo() {return mobileNo;}
    public void setMobileNo(String mobileNo) {this.mobileNo = mobileNo;}
    public String getPwdSHA256() {return pwdSHA256;}
    public void setPwdSHA256(String pwdSHA256) {this.pwdSHA256 = pwdSHA256;}
}
