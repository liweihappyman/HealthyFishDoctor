package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：我的关注的医生信息封装类
 * 作者：LYQ on 2017/7/8.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanMyConcernDoctorItem {

    private String name;//名字
    private String department;//科室
    private String duties;//职务
    private String imgUrl;//头像路径
    private String hospital;//医院
    private String describe;//简介描述


    public BeanMyConcernDoctorItem(String name, String department, String duties, String imgUrl,String hospital, String describe) {
        this.name = name;
        this.department = department;
        this.duties = duties;
        this.imgUrl = imgUrl;
        this.hospital = hospital;
        this.describe = describe;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
