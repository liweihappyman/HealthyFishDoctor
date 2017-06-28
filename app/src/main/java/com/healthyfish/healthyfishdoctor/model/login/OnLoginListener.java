package com.healthyfish.healthyfishdoctor.model.login;

/**
 * 描述：登录状态监听事件
 * 作者：Wayne on 2017/6/28 11:36
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

// TODO: 2017/6/28 实现网络接口
public interface OnLoginListener {
    void LoginSucess();

    void LoginFailed();
}
