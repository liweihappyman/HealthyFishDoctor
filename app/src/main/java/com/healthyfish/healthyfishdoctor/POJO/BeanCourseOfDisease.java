package com.healthyfish.healthyfishdoctor.POJO;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：病程  数据项
 * 作者：WKJ on 2017/7/5.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanCourseOfDisease extends DataSupport implements Serializable{

    private int id;
    private BeanMedRec beanMedRec;
    private String type;//记录首诊、出院、复诊等类型
    private String recPatientInfo;//记录患者信息
    private String date;//时间
    private List<String> imgPaths = new ArrayList<>();//图片路径
    private List<String> imgUrls = new ArrayList<>();//图片网络路径

    public BeanMedRec getBeanMedRec() {
        return beanMedRec;
    }
    public void setBeanMedRec(BeanMedRec beanMedRec) {
        this.beanMedRec = beanMedRec;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecPatientInfo() {
        return recPatientInfo;
    }

    public void setRecPatientInfo(String recPatientInfo) {
        this.recPatientInfo = recPatientInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(List<String> imgPaths) {
        this.imgPaths = imgPaths;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}
