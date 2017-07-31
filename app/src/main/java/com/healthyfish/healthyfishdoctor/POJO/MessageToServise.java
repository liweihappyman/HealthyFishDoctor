package com.healthyfish.healthyfishdoctor.POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：WKJ on 2017/7/27.
 * 邮箱：
 * 编辑：WKJ
 */

public class MessageToServise implements Serializable{
    private int medRecId;
    private int courseOfDiseaseId;
    private List<String> imageList = new ArrayList<>();


    public MessageToServise(int medRecId, int courseOfDiseaseId, List<String> imageList) {
        this.medRecId = medRecId;
        this.courseOfDiseaseId = courseOfDiseaseId;
        this.imageList = imageList;
    }


    public int getMedRecId() {
        return medRecId;
    }

    public void setMedRecId(int medRecId) {
        this.medRecId = medRecId;
    }

    public int getCourseOfDiseaseId() {
        return courseOfDiseaseId;
    }

    public void setCourseOfDiseaseId(int courseOfDiseaseId) {
        this.courseOfDiseaseId = courseOfDiseaseId;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
