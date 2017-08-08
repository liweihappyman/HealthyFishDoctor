package com.healthyfish.healthyfishdoctor.eventbus;

/**
 * 描述：EventBus异步提醒接收到消息
 * 作者：Wayne on 2017/8/6 11:17
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class WeChatReceiveMsg {
    private long time;

    public WeChatReceiveMsg(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
