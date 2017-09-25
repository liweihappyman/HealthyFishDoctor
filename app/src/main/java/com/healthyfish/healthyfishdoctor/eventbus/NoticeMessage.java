package com.healthyfish.healthyfishdoctor.eventbus;

/**
 * 描述：
 * 作者：WKJ on 2017/8/4.
 * 邮箱：
 * 编辑：WKJ
 */

public class NoticeMessage {
    int msg;//11表示刷新病历夹列表

    public NoticeMessage(int msg) {
        this.msg = msg;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }
}
