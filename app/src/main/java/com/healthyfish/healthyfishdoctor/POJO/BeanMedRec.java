package com.healthyfish.healthyfishdoctor.POJO;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者： TMXK on 2017/7/1.
 */

public class BeanMedRec extends DataSupport implements Serializable{
    private int id;//数据库自动分配的id，用来查询关联表的数据
    private boolean select = false;//在选择病历页面记录选中状态，
    private List<String> lables = new ArrayList<String>();//标签
    private String name;//姓名
    private String gender;//性别
    private String birthday;//出生日期
    private String IDno;//身份证号码
    private String occupation;//职业
    private String marital_status;//婚姻情况
    private String diagnosis;//诊断
    private String diseaseInfo;//病情信息
    private String clinicalDepartement;//就诊科室
    private String clinicalTime;//就诊时间
    private List<BeanCourseOfDisease> listCourseOfDisease = new ArrayList<BeanCourseOfDisease>() ;//病程列表
    private boolean state;//记录是自己的还是医生改过的状态  false表示是自己的，true表示改过的

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

//    public List<BeanCourseOfDisease> getListCourseOfDisease() {
//        return DataSupport.where("beanMedRec_id = ?", String.valueOf(id)).find(BeanCourseOfDisease.class);
//    }

    public List<BeanCourseOfDisease> getListCourseOfDisease() {
        return listCourseOfDisease;
    }

    public void setListCourseOfDisease(List<BeanCourseOfDisease> listCourseOfDisease) {
        this.listCourseOfDisease = listCourseOfDisease;
    }

    public boolean isSelect() {
        return select;
    }
    public void setSelect(boolean select) {
        this.select = select;
    }


    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return birthday;
    }

    public void setIDno(String IDno) {
        this.IDno = IDno;
    }
    public String getIDno() {
        return IDno;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public String getOccupation() {
        return occupation;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }
    public String getMarital_status() {
        return marital_status;
    }

    public List<String> getLables() {
        return lables;
    }

    public void setLables(List<String> lables) {
        this.lables = lables;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiseaseInfo() {
        return diseaseInfo;
    }
    public void setDiseaseInfo(String diseaseInfo) {
        this.diseaseInfo = diseaseInfo;
    }

    public String getClinicalDepartement() {
        return clinicalDepartement;
    }
    public void setClinicalDepartement(String clinicalDepartement) {
        this.clinicalDepartement = clinicalDepartement;
    }

    public String getClinicalTime() {
        return clinicalTime;
    }
    public void setClinicalTime(String clinicalTime) {
        this.clinicalTime = clinicalTime;
    }
}
