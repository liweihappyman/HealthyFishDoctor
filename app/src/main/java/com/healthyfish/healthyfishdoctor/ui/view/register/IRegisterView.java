package com.healthyfish.healthyfishdoctor.ui.view.register;

/**
 * 描述：注册交互接口
 * 作者：Wayne on 2017/6/28 11:22
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public interface IRegisterView {

    void showRegisterProgressBar();

    void hideRegisterProgressBar();

    String getRegisterUserName();

    String getRegisterVerificationCode();

    void toRegisterActivity();

    void showRegisterFailedError();
}
