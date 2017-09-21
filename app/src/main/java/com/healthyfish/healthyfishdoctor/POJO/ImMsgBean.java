package com.healthyfish.healthyfishdoctor.POJO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class ImMsgBean extends DataSupport implements Serializable{

    public final static int CHAT_SENDER_OTHER= 0;
    public final static int CHAT_SENDER_ME = 1;

    public final static int CHAT_MSGTYPE_TEXT_RECEIVER = 10;
    public final static int CHAT_MSGTYPE_IMG_RECEIVER = 11;
    public final static int CHAT_MSGTYPE_TEXT_SENDER = 12;
    public final static int CHAT_MSGTYPE_IMG_SENDER = 13;
    public final static int CHAT_MSGTYPE_MDR_SENDER = 14;
    public final static int CHAT_MSGTYPE_MDR_RECEIVER = 15;


    public ImMsgBean() {
    }

    public ImMsgBean(long time) {
        this.time = time;
    }

    private int id;
    // 服务类型
    private String serviceType;
    // 界面UI的发送类型：图片，文字
    private int msgType;
    // MQTT判断发送类型：t(text) i(image) v(video) s(system)
    private String type;
    // 发送时间
    @Column(nullable = false)
    private long time;
    // 图片内容
    private String image;
    // 发送姓名
    private String name;
    // 发送内容
    private String content;
    // 发送图片在服务器的地址
    private String imgUrl;
    // 发送主题
    @Column(nullable = false)
    private String topic;
    // 发送者肖像
    private String portrait;
    // 发送电子病历的key
    private String mdrKey;
    // 是发送者还是接收者
    private boolean isSender = false;
    // 发送状态
    private boolean isSuccess = false;
    // 是否在loading
    private boolean isLoading = false;
    // 是否是新消息
    private boolean isNewMsg = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getMdrKey() {
        return mdrKey;
    }

    public void setMdrKey(String dmrKey) {
        this.mdrKey = dmrKey;
    }

    public boolean isSender() {
        return isSender;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isNewMsg() {
        return isNewMsg;
    }

    public void setNewMsg(boolean newMsg) {
        isNewMsg = newMsg;
    }
}
