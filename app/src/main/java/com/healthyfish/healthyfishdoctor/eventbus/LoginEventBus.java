package com.healthyfish.healthyfishdoctor.eventbus;

/**
 * 描述：
 * 作者：LYQ on 2017/8/6.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class LoginEventBus {

    private String name;
    private String imgUrl;
    private boolean isLogin;


    public LoginEventBus() {
    }

    public LoginEventBus(String name,boolean isLogin) {
        this.isLogin = isLogin;
        this.name = name;
    }

    public LoginEventBus(String name, String imgUrl, boolean isLogin) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.isLogin = isLogin;
    }

    public LoginEventBus(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
