package com.healthyfish.healthyfishdoctor.eventbus;

import java.util.List;

/**
 * 描述：通知刷新审核状态
 * 作者：LYQ on 2017/8/6.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class DoctorInfo {

    private boolean isUpload;
    private boolean isPast;
    private boolean isLogin;
    private String name;
    private String gender;
    private List<String> imgList;

    public DoctorInfo(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public DoctorInfo() {
    }

    public boolean isPast() {
        return isPast;
    }

    public void setPast(boolean past) {
        isPast = past;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
