package com.healthyfish.healthyfishdoctor.POJO;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：WKJ on 2017/7/14.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanMedRecUser extends DataSupport implements Serializable{
    private int id;
    private String imgUrl;
    private String name;
    private String date;
    private List<BeanMedRec> medRecList = new ArrayList<BeanMedRec>();//1对多

    public BeanMedRecUser() {
    }

    public BeanMedRecUser(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BeanMedRec> getMedRecList() {
        return medRecList;
    }

    public void setMedRecList(List<BeanMedRec> medRecList) {
        this.medRecList = medRecList;
    }
}
