package com.healthyfish.healthyfishdoctor.POJO;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：医生个人信息类，包括关联挂号的信息，用来与服务器交互
 * 作者：LYQ on 2017/8/5.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanDoctor implements Serializable{

    private int type;//1表示关联挂号信息，2表示个人信息
    private String doctId;//uid,手机号
    private String hosp;//医院编号
    private String hospTxt;//医院名称
    private String dept;//部门编号
    private String deptTxt;//部门名称
    private String name;//医生姓名(doctList[x].DOCTOR_NAME)
    private String icon;//医生头像(doctList[x].ZHAOPIAN)
    private String phone;//医生手机号
    private String desc;//医生专长简介(doctList[x].WEB_INTRODUCE)
    private String title;//用于搜索的医生标题，格式：hospTxt+"-"+deptTxt+"-"+name
    private String reister;//医生职称(doctList[x].REISTER_NAME)
    private int doct;//医生编号(doctList[x].STAFF_NO)
    private String doctReg;//挂号标识，格式：name/staff_No(doctList[x].DOCTOR)
    private String gender;//性别
    private List<String> imgList;//证件照

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDoctId() {
        return doctId;
    }

    public void setDoctId(String doctId) {
        this.doctId = doctId;
    }

    public String getHosp() {
        return hosp;
    }

    public void setHosp(String hosp) {
        this.hosp = hosp;
    }

    public String getHospTxt() {
        return hospTxt;
    }

    public void setHospTxt(String hospTxt) {
        this.hospTxt = hospTxt;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptTxt() {
        return deptTxt;
    }

    public void setDeptTxt(String deptTxt) {
        this.deptTxt = deptTxt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReister() {
        return reister;
    }

    public void setReister(String reister) {
        this.reister = reister;
    }

    public int getDoct() {
        return doct;
    }

    public void setDoct(int doct) {
        this.doct = doct;
    }

    public String getDoctReg() {
        return doctReg;
    }

    public void setDoctReg(String doctReg) {
        this.doctReg = doctReg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

}
