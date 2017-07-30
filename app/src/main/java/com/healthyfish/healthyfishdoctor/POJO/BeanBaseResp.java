package com.healthyfish.healthyfishdoctor.POJO;

public class BeanBaseResp {
    private int code = 0; //0 SUCCESS, <0 FAILED
    private String ver = "0.1";
    private String timestamp;

    public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getCode() {return code;}
    public void setCode(int code) {this.code = code;}
    public String getVer() {return ver;}
    public void setVer(String ver) {this.ver = ver;}
}
