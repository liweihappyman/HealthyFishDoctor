package com.healthyfish.healthyfishdoctor.POJO;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 描述：添加过图文资讯的医生列表
 * 作者：Wayne on 2017/8/7 22:02
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class BeanInterrogationServiceUserList extends DataSupport{
    private int id;
    @Column(unique = true)
    private String PeerNumber;

    private String PeerName;

    private String PeerPortrait;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeerNumber() {
        return PeerNumber;
    }

    public void setPeerNumber(String peerNumber) {
        PeerNumber = peerNumber;
    }

    public String getPeerName() {
        return PeerName;
    }

    public void setPeerName(String peerName) {
        PeerName = peerName;
    }

    public String getPeerPortrait() {
        return PeerPortrait;
    }

    public void setPeerPortrait(String peerPortrait) {
        PeerPortrait = peerPortrait;
    }
}
