package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：个人中心我的关注列表item封装类
 * 作者：LYQ on 2017/7/9.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanMyConcernItem {

    private String type;//关注的类型，比如私人医生或者健康新闻
    private BeanDoctorInfo beanDoctorInfo;
    private BeanItemNewsAbstract beanItemNewsAbstract;

    public BeanMyConcernItem() {

    }

    public BeanMyConcernItem(String type, BeanDoctorInfo beanDoctorInfo, BeanItemNewsAbstract beanItemNewsAbstract) {
        this.type = type;
        this.beanDoctorInfo = beanDoctorInfo;
        this.beanItemNewsAbstract = beanItemNewsAbstract;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BeanDoctorInfo getBeanDoctorInfo() {
        return beanDoctorInfo;
    }

    public void setBeanDoctorInfo(BeanDoctorInfo beanDoctorInfo) {
        this.beanDoctorInfo = beanDoctorInfo;
    }

    public BeanItemNewsAbstract getBeanItemNewsAbstract() {
        return beanItemNewsAbstract;
    }

    public void setBeanItemNewsAbstract(BeanItemNewsAbstract beanItemNewsAbstract) {
        this.beanItemNewsAbstract = beanItemNewsAbstract;
    }
}
