package com.healthyfish.healthyfishdoctor.utils.mqtt_utils;

/**
 * Created by Administrator on 2017/2/25.
 */

public class BeanMqttMsgItem {
    private long timestamp;
    private String topic;
    private String content;
    private String source; // 消息来源
    private String type; //消息的类型: t(text) i(image) v s(system)
    private boolean flag; //对于发送者：F: 未发送成功；T:已发送；对于接收者：F:消息未阅读；T:已阅

    public BeanMqttMsgItem(){
    }
    public BeanMqttMsgItem(long ts, String tpc, String src, String cnt, String tp, boolean flag){
        this.timestamp = ts;
        this.topic = tpc;
        this.source = src;
        this.content = cnt;
        this.type = tp;
        this.flag = flag;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
