package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：问诊首页付费服务封装类
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanPayService {

    private int img;//服务图标
    private String title;//服务名称
    private boolean isOpen;//是否开通该服务

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
