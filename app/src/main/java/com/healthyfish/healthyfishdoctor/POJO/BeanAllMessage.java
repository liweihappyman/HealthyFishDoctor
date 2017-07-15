package com.healthyfish.healthyfishdoctor.POJO;

import android.widget.TextView;

/**
 * 描述：首页消息列表bean
 * 作者：WKJ on 2017/7/14.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanAllMessage {
    private String type;
    private String date;
    private String description;

    public BeanAllMessage(String type, String date, String description) {
        this.type = type;
        this.date = date;
        this.description = description;
    }

    public BeanAllMessage(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
