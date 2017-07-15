package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：医生详情实体类
 * 作者：LYQ on 2017/7/2.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanDoctorInfo {

    private String imgUrl;  //医生头像地址或者路径
    private String name;  //医生姓名
    private String department;  //医生所在科室
    private String duties;  //医生职务
    private String hospital;  //医生所在医院
    private String introduce;  //医生简介
    private String price;  //医生问诊价格


    public BeanDoctorInfo() {
    }

    public BeanDoctorInfo(String imgUrl, String name, String department, String duties, String hospital, String introduce, String price) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.department = department;
        this.duties = duties;
        this.hospital = hospital;
        this.introduce = introduce;
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
