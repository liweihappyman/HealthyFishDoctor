package com.healthyfish.healthyfishdoctor.POJO;

import java.io.Serializable;

/**
 * 描述：
 * 作者：Wayne on 2017/9/8 16:53
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class BeanUserChatInfo implements Serializable {

    private String name;
    private String phone;
    private String imgUrl;
    // 所选服务类型
    private String serviceType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

}
