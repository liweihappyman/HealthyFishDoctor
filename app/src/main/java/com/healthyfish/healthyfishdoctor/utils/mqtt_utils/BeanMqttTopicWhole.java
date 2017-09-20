package com.healthyfish.healthyfishdoctor.utils.mqtt_utils;

/**
 * Created by Administrator on 2017/2/25.
 */

public class BeanMqttTopicWhole {
    private String localName; //发送给对方
    private String localIcon; //发送给对方
    private String topic;// u:普通用户；d:医生；g:群组；s:系统
    private String icon;
    private String name;
    private String time; //last msg', for show
    private String content; //last msg', for show
    private int state;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
