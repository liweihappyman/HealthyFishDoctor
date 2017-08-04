package com.healthyfish.healthyfishdoctor.utils.mqtt_utils;

/**
 * Created by Administrator on 2017/2/25.
 */

public class BeanMqttTopicReq {
    private String localName; //发送给对方
    private String localIcon; //发送给对方
    private String content; //last msg', for show
    public BeanMqttTopicReq(){}
    public BeanMqttTopicReq(BeanMqttTopicWhole bean){
        this.localIcon = new String(bean.getLocalIcon());
        this.localName = new String(bean.getLocalName());
        this.content = new String(bean.getContent());
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocalIcon() {
        return localIcon;
    }

    public void setLocalIcon(String localIcon) {
        this.localIcon = localIcon;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }
}
