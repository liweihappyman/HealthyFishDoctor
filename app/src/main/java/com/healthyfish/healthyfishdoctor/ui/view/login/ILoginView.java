package com.healthyfish.healthyfishdoctor.ui.view.login;

/**
 * 描述：登录交互接口
 * 作者：Wayne on 2017/6/28 11:11
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

// TODO: 2017/6/28 实现登录接口
public interface ILoginView {

    void showLoginProgressBar();

    void hideLoginProgressBar();

    String getLoginUserName();

    String getLoginPassword();

    void toLoginActivity();

    void showLoginFailedError();
}
