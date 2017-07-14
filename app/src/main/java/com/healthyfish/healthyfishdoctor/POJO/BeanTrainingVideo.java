package com.healthyfish.healthyfishdoctor.POJO;

import java.util.List;

/**
 * 描述：培训视频缩略图及介绍（在培训ViewPage下）
 * 作者：Wayne on 2017/7/13 10:39
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class BeanTrainingVideo {
    // 门类ID
    private int categoryId;
    // 培训缩略图
    private int thumbNailTraining;
    // 培训名称
    private String nameTraining;
    // 观看人数
    private int numViewer;
    // 总的培训门类列表
    private String[] listCategoryTraining;

    public String[] getListCategoryTraining() {
        return listCategoryTraining;
    }

    public void setListCategoryTraining(String[] listCategoryTraining) {
        this.listCategoryTraining = listCategoryTraining;
    }

    public int getThumbNailTraining() {
        return thumbNailTraining;
    }

    public void setThumbNailTraining(int thumbNailTraining) {
        this.thumbNailTraining = thumbNailTraining;
    }

    public String getNameTraining() {
        return nameTraining;
    }

    public void setNameTraining(String nameTraining) {
        this.nameTraining = nameTraining;
    }

    public int getNumViewer() {
        return numViewer;
    }

    public void setNumViewer(int numViewer) {
        this.numViewer = numViewer;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
