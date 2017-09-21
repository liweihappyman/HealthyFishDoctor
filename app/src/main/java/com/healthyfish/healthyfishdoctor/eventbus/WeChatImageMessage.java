package com.healthyfish.healthyfishdoctor.eventbus;

/**
 * 描述：EventBus异步提醒图片发送状态
 * 作者：Wayne on 2017/8/5 14:49
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class WeChatImageMessage {
    // 发送时间
    private long time;
    // 发送图片在服务器的地址
    private String imgUrl;

    public WeChatImageMessage(long time, String imgUrl) {
        this.time = time;
        this.imgUrl = imgUrl;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
