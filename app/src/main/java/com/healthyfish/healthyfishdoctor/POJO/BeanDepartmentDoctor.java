package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：科室生详情实体类
 * 作者：LYQ on 2017/7/2.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanDepartmentDoctor {

    private String imgUrl;  //医生头像地址或者路径
    private String name;  //医生姓名
    private String department;  //医生所在科室
    private String duties;  //医生职务
    private String hospital;  //医生所在医院
    private String introduce;  //医生简介
    private boolean isAvailable;  //医生问诊价格


    public BeanDepartmentDoctor() {
    }

    public BeanDepartmentDoctor(String imgUrl, String name, String department, String duties, String hospital, String introduce, boolean isAvailable) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.department = department;
        this.duties = duties;
        this.hospital = hospital;
        this.introduce = introduce;
        this.isAvailable = isAvailable;
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

    public boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}
