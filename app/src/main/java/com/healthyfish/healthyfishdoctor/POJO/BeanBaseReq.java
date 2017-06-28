package com.healthyfish.healthyfishdoctor.POJO;

public class BeanBaseReq {
    protected String act;
    protected String ver = "0.1";

    public BeanBaseReq(){}
    BeanBaseReq(String act){this.act = act;}

    public String getAct() { return act; }
    public void setAct(String act) { this.act = act; }

    public String getVer() { return ver; }
    public void setVer(String ver) { this.ver = ver; }
}
