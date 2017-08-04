package com.healthyfish.healthyfishdoctor.utils.mqtt_utils;

/**
 * Created by Administrator on 2017/2/25.
 */

public class BeanMqttTopicItem {
    private String topic;// u:普通用户；d:医生；g:群组；s:系统
    private String icon;
    private String name;
    private String time; //last msg', for show
    private String content; //last msg', for show
    private int unReadNum; //未阅读消息的数量
    //最低一个字节表示MQTT本身的消息，倒数第二个字节表示消息的源是否是自身
    private int state;

    public BeanMqttTopicItem(){}
    public BeanMqttTopicItem(BeanMqttTopicWhole bean){
        this.topic = bean.getTopic();
        this.icon = bean.getIcon();
        this.name = bean.getName();
        this.time = bean.getTime();
        this.content = bean.getContent();
        this.state = bean.getState();
    }
    public BeanMqttTopicItem(BeanMqttTopicReq bean){
        this.icon = bean.getLocalIcon();
        this.name = bean.getLocalName();
        this.content = bean.getContent();
    }

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

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }
}
