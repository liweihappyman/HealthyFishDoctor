package com.healthyfish.healthyfishdoctor.POJO;

public class BeanSessionIdReq extends BeanBaseReq {
    private String  mobileNo;
    private String  pwdSHA256;
    private int 	expired; //min
    //...

    public BeanSessionIdReq(){super(BeanSessionIdReq.class.getSimpleName());}

    public String getMobileNo() {return mobileNo;}
    public void setMobileNo(String mobileNo) {this.mobileNo = mobileNo;}
    public String getPwdSHA256() {return pwdSHA256;}
    public void setPwdSHA256(String pwdSHA256) {this.pwdSHA256 = pwdSHA256;}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}
}
